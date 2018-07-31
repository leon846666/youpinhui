package com.youpinhui.manager.controller;


import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.youpinhui.entity.PageResult;
import com.youpinhui.entity.Result;
import com.youpinhui.pojo.TbGoods;
import com.youpinhui.pojogroup.Goods;
import com.youpinhui.sellergoods.service.GoodsService;


/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

	@Reference
	private GoodsService goodsService;
	
	/**
	 * test case find all
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoods> findAll(){			
		return goodsService.findAll();
	}
	
	
	/**
	 * pagination
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return goodsService.findPage(page, rows);
	}
	
	
	
	/**
	 *  update
	 * @param goods
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody Goods goods){
		try {
			goodsService.update(goods);
			return new Result(true, "update success");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "update®æfailed");
		}
	}	
	
	/**
	 * get one by id
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public Goods findOne(Long id){
		return goodsService.findOne(id);		
	}
	
	/**
	 * batch delete
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			goodsService.delete(ids);
			return new Result(true, "delete success"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "delete failed");
		}
	}
	
		/**
		 * fuzzy search
	 * if the object is not null, get the parameter from it as the search condition.
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbGoods goods, int page, int rows  ){
		return goodsService.findPage(goods, page, rows);		
	}
	
	/**
	 * 
	 * update status
	 */
	@RequestMapping("/updateStatus")
	private Result updateStatus(Long [] ids,String status) {
		try {
			goodsService.updateStatus(ids, status);
			return  new Result(true,"suceess");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return  new Result(true,"failed");
		}
		
	}
}
