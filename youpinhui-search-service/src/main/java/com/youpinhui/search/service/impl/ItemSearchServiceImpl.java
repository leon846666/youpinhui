package com.youpinhui.search.service.impl;

import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.core.Is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.FilterQuery;
import org.springframework.data.solr.core.query.GroupOptions;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.HighlightQuery;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleFilterQuery;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.GroupEntry;
import org.springframework.data.solr.core.query.result.GroupPage;
import org.springframework.data.solr.core.query.result.GroupResult;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightEntry.Highlight;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;

import com.alibaba.dubbo.config.annotation.Service;
import com.youpinhui.pojo.TbBrand;
import com.youpinhui.pojo.TbItem;
import com.youpinhui.search.service.ItemSearchService;

@Service(timeout=5000)
public class ItemSearchServiceImpl implements ItemSearchService{

	@Autowired
	private SolrTemplate solrTemplate;
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Override
	public Map<String, Object> search(Map searchMap) {
		Map map=new HashMap();
		
		// white space
		String string = (String)searchMap.get("keywords");
		searchMap.put("keywords", string.replace(" ", ""));
		
		// 1. search items
		map.putAll(searchList(searchMap));
	
		// 2. search item category List
		List<String> categoryList = searchCategoryList(searchMap);
		map.put("categoryList",categoryList);
		String category =(String)searchMap.get("category");
		
		// 3. search brand & specfication 
		// if user hasn't choose category ,use the first category in category List to search
		if(category.equals("")){
			
			if(categoryList.size()>0){
				map.putAll(searchBrandAndSpecList(categoryList.get(0)));
			}
		}else{
			//if user already chosen a category , use the chosen one to search
			map.putAll(searchBrandAndSpecList(category));
		}
		
		return map;
	}

	
	private Map searchList(Map searchMap){
		Map map=new HashMap();
		//search keyword highLight
		HighlightQuery query = new SimpleHighlightQuery();
		
		HighlightOptions highlightOptions = new HighlightOptions().addField("item_title");
		highlightOptions.setSimplePrefix("<em style='color:red'>");
		highlightOptions.setSimplePostfix("</em>");
		query.setHighlightOptions(highlightOptions);
		
		// 1.1 query by key word 
		Criteria criteria=new Criteria("item_keywords").is(searchMap.get("keywords"));
		query.addCriteria(criteria);
	
		// 1.2 query by category
		if(!"".equals(searchMap.get("category"))){
			FilterQuery filterQuery = new SimpleFilterQuery();
			Criteria filterCriteria= new Criteria("item_category").is(searchMap.get("category"));
			filterQuery.addCriteria(filterCriteria);
			query.addFilterQuery(filterQuery);
		}

		// 1.3 query by brand
		if(!"".equals(searchMap.get("brand"))){
			FilterQuery filterQuery = new SimpleFilterQuery();
			Criteria filterCriteria= new Criteria("item_brand").is(searchMap.get("brand"));
			filterQuery.addCriteria(filterCriteria);
			query.addFilterQuery(filterQuery);
		}

		// 1.4 query by specification
		if(searchMap.get("spec")!=null){
			Map<String,String> specMap =	(Map) searchMap.get("spec");
			for(String key :specMap.keySet()){
				FilterQuery filterQuery = new SimpleFilterQuery();
				Criteria filterCriteria= new Criteria("item_spec_"+key).is(specMap.get(key));
				filterQuery.addCriteria(filterCriteria);
				query.addFilterQuery(filterQuery);
			}
		}
		// 1.5 query by price
		if(!"".equals(searchMap.get("price"))){
			String price = (String)searchMap.get("price");
			String[] priceArr= price.split("-");
			if(!priceArr[0].equals("0")){
				
				FilterQuery filterQuery= new SimpleFilterQuery();
				Criteria filterCriteria= new Criteria("item_price").greaterThanEqual(priceArr[0]);
				filterQuery.addCriteria(filterCriteria);
				query.addFilterQuery(filterQuery);
			}
			if(!priceArr[1].equals("*")){
				
				FilterQuery filterQuery= new SimpleFilterQuery();
				Criteria filterCriteria= new Criteria("item_price").lessThanEqual(priceArr[1]);
				filterQuery.addCriteria(filterCriteria);
				query.addFilterQuery(filterQuery);
			}		
		}
		
		// 1.6 pagination
		Integer pageNo= (Integer)searchMap.get("pageNo");
		if(pageNo==null){
			pageNo=1;
		}
		
		Integer pageSize= (Integer)searchMap.get("pageSize");
		if(pageSize==null){
			pageSize=20;
		}
		//set off set 
		query.setOffset((pageNo-1)*pageSize);
		// set size 
		query.setRows(pageSize);
		
		
		// 1.7 sorting
		String sortWay =(String) searchMap.get("sortWay");
		String sortField=(String) searchMap.get("sortField");
		
		if(sortWay!=null&&!sortWay.equals("")){
			if(sortWay.equals("ASC")){
				 Sort sort = new Sort(Sort.Direction.ASC, "item_"+sortField);
				 query.addSort(sort);
			}
			if(sortWay.equals("DESC")) {
				Sort  sort = new Sort(Sort.Direction.DESC, "item_"+sortField);
				 query.addSort(sort);
			}
			
			
		}
		
		
		
		
		//high light page 
		HighlightPage<TbItem> page = solrTemplate.queryForHighlightPage(query,TbItem.class);
		List<HighlightEntry<TbItem>> listHighlight = page.getHighlighted();
	
		for (HighlightEntry<TbItem> entry : listHighlight) {
			
			List<Highlight> highlights = entry.getHighlights();
		
			TbItem item = entry.getEntity();
			if(highlights.size()>0&& highlights.get(0).getSnipplets().size()>0){
				item.setTitle(highlights.get(0).getSnipplets().get(0));
			}
		
		}
		map.put("rows", page.getContent());
		map.put("totalPage", page.getTotalPages());
		map.put("total", page.getTotalElements());
		return map;
		
	}
	/**
	 * search category list using  group by 
	 */
	private List<String> searchCategoryList(Map searchMap){
		List<String> list = new ArrayList<>();
		Query query= new SimpleQuery("*:*");
		
		//query by key word
		Criteria criteria=new Criteria("item_keywords").is(searchMap.get("keywords"));
		query.addCriteria(criteria);
		
		// set group Option
		GroupOptions groupOptions = new GroupOptions().addGroupByField("item_category");
		query.setGroupOptions(groupOptions);
		
		// get the group page
		GroupPage<TbItem> page = solrTemplate.queryForGroupPage(query, TbItem.class);
		// get the groupResult by the keywordname added in group option
		GroupResult<TbItem> groupResult = page.getGroupResult("item_category");
		// get groupEntry 
		Page<GroupEntry<TbItem>> groupEntries = groupResult.getGroupEntries();
		// loop the entry to get data
		for (GroupEntry<TbItem> groupEntry : groupEntries) {
			list.add(groupEntry.getGroupValue());   // put the entryValue into list
		}
		
		return list;
	}
	
	
	/**
	 * 
	 *  according category name find brand & speclist
	 * @param categoryName
	 * @return
	 */
	private Map searchBrandAndSpecList(String categoryName){
	
		Map map = new HashMap();
		Long templateId = (Long) redisTemplate.boundHashOps("itemCate").get(categoryName);
		if(templateId!=null){
			List brandList = (List) redisTemplate.boundHashOps("brandList").get(templateId);
			List specList = (List) redisTemplate.boundHashOps("specList").get(templateId);
			System.out.println("size of brandList in redis: "+brandList.size());
			map.put("brandList", brandList);
			System.out.println("size of specList in redis: "+specList.size());
			map.put("specList", specList);
		}
		return map;
	}
	
}
