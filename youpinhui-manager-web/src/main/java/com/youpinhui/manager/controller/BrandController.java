package com.youpinhui.manager.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.youpinhui.pojo.TbBrand;
import com.youpinhui.sellergoods.service.BrandService;

@RestController
@RequestMapping("/brand")
public class BrandController {

	@Reference
	private BrandService brandService;
	
	@RequestMapping("/findAll")
	public List<TbBrand> findAll() {
		System.out.println("findALL");
		 List<TbBrand> findAll = brandService.findAll();
			System.out.println(" after findALL"+findAll.size());

		 return findAll;
	}
}
