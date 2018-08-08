package com.youpinhui.search.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.core.Is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.HighlightQuery;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.SimpleQuery;
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
		/*
		Query query=new SimpleQuery("*:*");
		Criteria criteria=new Criteria("item_keywords").is(searchMap.get("keywords"));
		query.addCriteria(criteria);
		
		ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);
		
		map.put("rows", page.getContent());
		*/
		
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
			
			/*for (Highlight highlight : highlights) {
				List<String> snpts = highlight.getSnipplets();
				System.out.println(snpts);
			}*/
			TbItem item = entry.getEntity();
			if(highlights.size()>0&& highlights.get(0).getSnipplets().size()>0){
				item.setTitle(highlights.get(0).getSnipplets().get(0));
			}
		
		}
		map.put("rows", page.getContent());
		
		
		return map;
	}

}
