package com.youpinhui.manager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.youpinhui.entity.PageResult;
import com.youpinhui.entity.Result;
import com.youpinhui.pojo.TbSpecification;
import com.youpinhui.pojogroup.Specification;
import com.youpinhui.sellergoods.service.SpecificationService;

/**
 * controller
 * @author Leon
 *
 *	
 */
@RestController
@RequestMapping("/specification")
public class SpecificationController {

	@Reference
	private SpecificationService specificationService;
	
	/**
	 * return all data
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSpecification> findAll(){			
		return specificationService.findAll();
	}
	
	
	/**
	 *  return all data with pagination
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return specificationService.findPage(page, rows);
	}
	
	/**
	 * add 
	 * @param specification
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody Specification specification){
		try {
			specificationService.add(specification);
			return new Result(true, "add success");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "add failed");
		}
	}
	
	/**
	 * update
	 * @param specification
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody Specification specification){
		try {
			specificationService.update(specification);
			return new Result(true, "update success");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "update failed");
		}
	}	
	
	/**
	 * find one object
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public Specification findOne(Long id){
		return specificationService.findOne(id);		
	}
	
	/**
	 * batch delete
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			specificationService.delete(ids);
			return new Result(true, "delete success"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "delete failed");
		}
	}
	
	/**
	 * fuzzy search 
	 *  if the object is not null, get the parameter from it as the search condition.
	 * @param TbSpecification
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbSpecification specification, int page, int rows  ){
		return specificationService.findPage(specification, page, rows);		
	}
	
	@RequestMapping("/selectOptionList")
	public List<Map> selectOptionList() {
		
		return specificationService.selectOptionList();
	}
	
}
