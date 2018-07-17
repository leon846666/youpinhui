package com.youpinhui.shop.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.youpinhui.entity.PageResult;
import com.youpinhui.entity.Result;
import com.youpinhui.pojo.TbTypeTemplate;
import com.youpinhui.sellergoods.service.TypeTemplateService;


/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {

	@Reference
	private TypeTemplateService typeTemplateService;
	
	/**
	 *  return all data
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbTypeTemplate> findAll(){			
		return typeTemplateService.findAll();
	}
	
	
	/**
	 * return all data with pagination
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return typeTemplateService.findPage(page, rows);
	}
	
	/**
	 * add
	 * @param typeTemplate
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbTypeTemplate typeTemplate){
		try {
			typeTemplateService.add(typeTemplate);
			return new Result(true, "add success");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "add failed");
		}
	}
	
	/**
	 * update
	 * @param typeTemplate
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbTypeTemplate typeTemplate){
		try {
			typeTemplateService.update(typeTemplate);
			return new Result(true, " update success");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, " update failed");
		}
	}	
	
	/**
	 * get one by id
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbTypeTemplate findOne(Long id){
		return typeTemplateService.findOne(id);		
	}
	
	/**
	 * batch delete 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		
		List aList = new ArrayList<>();
		aList.iterator();
		
		try {
			typeTemplateService.delete(ids);
			return new Result(true, "delete success"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "delete failed");
		}
	}
	
		/**
	 * fuzzy search 
	 *  if the object is not null, get the parameter from it as the search condition.
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbTypeTemplate typeTemplate, int page, int rows  ){
		return typeTemplateService.findPage(typeTemplate, page, rows);		
	}
	
	
	@RequestMapping("/selectOptionList")
	public List<Map> selectOptionList() {
		
		return typeTemplateService.selectOptionList();
	}
	
	@RequestMapping("/findSpecList")
	public List<Map> findSpecList(Long id) {
		
		
		return typeTemplateService.findSpecList(id);
		
	}
	
	
}
