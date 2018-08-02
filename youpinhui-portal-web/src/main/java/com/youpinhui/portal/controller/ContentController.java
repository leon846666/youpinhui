package com.youpinhui.portal.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.youpinhui.content.service.ContentService;
import com.youpinhui.pojo.TbContent;

@RequestMapping("/content")
@RestController
public class ContentController {

	@Reference
	private ContentService contentService;
	
	@RequestMapping("/findByCategoryId")
	public List<TbContent> findByCategoryId(Long categoryId){
		return contentService.findByCategoryId(categoryId);
	}

	
}
