package com.youpinhui.solrutil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import com.youpinhui.mapper.TbItemMapper;
import com.youpinhui.pojo.TbItem;
import com.youpinhui.pojo.TbItemExample;
import com.youpinhui.pojo.TbItemExample.Criteria;

@Component
public class SolrUtil {

	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private SolrTemplate solrTemplate;
	
	
	public void importData(){
		
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		
		//item must be approved
		criteria.andStatusEqualTo("1");
		List<TbItem> itemList = itemMapper.selectByExample(example);
		
		System.out.println("Starts import");
		for (TbItem tbItem : itemList) {
			System.out.println(tbItem.getId()+","+tbItem.getTitle()+","+tbItem.getPrice());
		}
		
		solrTemplate.saveBeans(itemList);
		solrTemplate.commit();
		System.out.println("End import");
	}
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");
		SolrUtil solrUtil = (SolrUtil) context.getBean("solrUtil");
		solrUtil.importData();
		
		
	}
	
}
