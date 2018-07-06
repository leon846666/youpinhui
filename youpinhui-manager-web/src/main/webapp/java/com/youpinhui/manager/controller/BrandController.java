package com.youpinhui.manager.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.youpinhui.entity.PageResult;
import com.youpinhui.entity.Result;
import com.youpinhui.pojo.TbBrand;
import com.youpinhui.sellergoods.service.BrandService;

@RestController
@RequestMapping("/brand")
public class BrandController {

	@Reference
	private BrandService brandService;
	
	@RequestMapping("/findAll")
	public List<TbBrand> findAll() {
		 List<TbBrand> findAll = brandService.findAll();
		 return findAll;
	}
	
	@RequestMapping("/findPage")
	public PageResult findPage(int page,int size) {
		return brandService.findPage(page, size);
	}
	
	
	
	@RequestMapping("/add")
	public Result add(@RequestBody TbBrand brand) {
		try {
			brandService.add(brand);
			return new Result(true,"insert success");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new Result(false,"insert failed");
		}
	}
	
	@RequestMapping("/findOne")
	public TbBrand findOne(long id) {
		TbBrand brand =brandService.findOne(id);
		return brand ;
	}
	
	
}
