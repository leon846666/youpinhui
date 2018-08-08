package com.youpinhui.search.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.youpinhui.search.service.ItemSearchService;

@RestController
@RequestMapping("/itemSearch")
public class ItemSearchController {
	
	
	@Reference
	private ItemSearchService itemSearchService;
	
	@RequestMapping("/search")
	public Map search(@RequestBody Map searchMap) {
		
		System.out.println("111111111111111111");
		return itemSearchService.search(searchMap);
	}
	
	
}
