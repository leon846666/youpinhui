package com.youpinhui.search.service.impl;

import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.core.Is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.GroupOptions;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.HighlightQuery;
import org.springframework.data.solr.core.query.Query;
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
import com.youpinhui.pojo.TbItem;
import com.youpinhui.search.service.ItemSearchService;

@Service(timeout=5000)
public class ItemSearchServiceImpl implements ItemSearchService{

	@Autowired
	private SolrTemplate solrTemplate;
	
	@Override
	public Map<String, Object> search(Map searchMap) {
		Map map=new HashMap();
		
		// 1. search items
		map.putAll(searchList(searchMap));
	
		// 2. search item category List
		map.put("categoryList",searchCategoryList(searchMap));
		
		
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
		
		//query by key word
		Criteria criteria=new Criteria("item_keywords").is(searchMap.get("keywords"));
		query.addCriteria(criteria);
		
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
}
