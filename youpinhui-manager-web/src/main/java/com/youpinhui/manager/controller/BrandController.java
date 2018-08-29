package com.youpinhui.manager.controller;

import java.util.List;
import java.util.Map;

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

	@Reference(timeout=10000)
	private BrandService brandService;

	@RequestMapping("/findAll")
	public List<TbBrand> findAll() {
		
		return brandService.findAll();
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
		return brandService.findOne(id);
	}
	
	@RequestMapping("/update")
	public Result update(@RequestBody TbBrand brand) {
		
		try {
			brandService.update(brand);
			return new Result(true,"update success");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new Result(false,"update failed");
		}
	}
	
	
	@RequestMapping("/delete")
	public Result delete(Long [] ids) {
		
		try {
			brandService.delete(ids);
			return new Result(true,"delete success");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new Result(false,"delete failed");
		}
	}
	
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbBrand brand, int page,int size) {
		
		return brandService.findPage(brand,page, size);
	}
	
	
	@RequestMapping("/selectOptionList")
	public List<Map> selectOptionList() {
		
		return brandService.selectOptionList();
	}
	
	
	
}
