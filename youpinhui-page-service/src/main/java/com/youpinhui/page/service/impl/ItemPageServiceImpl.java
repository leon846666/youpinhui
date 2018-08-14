package com.youpinhui.page.service.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.alibaba.dubbo.config.annotation.Service;
import com.youpinhui.mapper.TbGoodsDescMapper;
import com.youpinhui.mapper.TbGoodsMapper;
import com.youpinhui.page.service.ItemPageService;
import com.youpinhui.pojo.TbGoods;
import com.youpinhui.pojo.TbGoodsDesc;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;

@Service
public class ItemPageServiceImpl implements ItemPageService{

	@Autowired
	private TbGoodsMapper tbGoodsMapper;
	
	@Autowired 
	private TbGoodsDescMapper tbGoodsDescMapper;
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfiger;
	
	@Value("${pagedir}")
	private String pagedir;
	
	@Override
	public boolean genItemHtml(Long goodsId) {
		// TODO Auto-generated method stub
		
		Configuration configuration = freeMarkerConfiger.getConfiguration();
		try {
			
			//  read template 
			Template template = configuration.getTemplate("item.ftl");
			
			// create data model
			Map dataModel= new HashMap();
			
			// search data in db
			TbGoods goods = tbGoodsMapper.selectByPrimaryKey(goodsId);
			TbGoodsDesc goodsDesc = tbGoodsDescMapper.selectByPrimaryKey(goodsId);
			
			// put data into dataModel
			dataModel.put("goods", goods);
			dataModel.put("goodsDesc", goodsDesc);
			
			// create file
			Writer out = new FileWriter(pagedir+goodsId+".html");
			
			
			
			
			//out put 
			template.process(dataModel, out);
			
			out.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
