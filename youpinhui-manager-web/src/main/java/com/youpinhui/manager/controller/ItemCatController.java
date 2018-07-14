package com.youpinhui.manager.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.youpinhui.entity.PageResult;
import com.youpinhui.entity.Result;
import com.youpinhui.pojo.TbItemCat;
import com.youpinhui.sellergoods.service.ItemCatService;


/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

	@Reference
	private ItemCatService itemCatService;
	
	/**
	 * return all 
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbItemCat> findAll(){			
		return itemCatService.findAll();
	}
	
	
	/**
	 * findall with pagination
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return itemCatService.findPage(page, rows);
	}
	
	/**
	 * Add
	 * @param itemCat
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbItemCat itemCat){
		try {
			System.out.println("inside item save!");
			itemCatService.add(itemCat);
			return new Result(true, " add success");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "add failed");
		}
	}
	
	/**
	 * Update
	 * @param itemCat
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbItemCat itemCat){
		try {
			itemCatService.update(itemCat);
			return new Result(true, "update success");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "update failed");
		}
	}	
	
	/**
	 * get one by id
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbItemCat findOne(Long id){
		return itemCatService.findOne(id);		
	}
	
	/**
	 * batch delete
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			itemCatService.delete(ids);
			return new Result(true, "delete success"); 
		} catch (Exception e) {
			e.printStackTrace(); 
			return new Result(false, "delete failed");
		}
	}
	
		/**
	 *  fuzzy search
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbItemCat itemCat, int page, int rows  ){
		return itemCatService.findPage(itemCat, page, rows);		
	}
	
	/**
	 * find parent by id
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/findByParentId")
	public List<TbItemCat> findByParentId(Long parentId  ){
		return itemCatService.findByParentId(parentId);		
	}
	
}
