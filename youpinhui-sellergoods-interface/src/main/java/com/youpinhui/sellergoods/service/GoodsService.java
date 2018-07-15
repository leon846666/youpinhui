package com.youpinhui.sellergoods.service;
import java.util.List;

import com.youpinhui.entity.PageResult;
import com.youpinhui.pojo.TbGoods;
import com.youpinhui.pojogroup.Goods;


/**
 * provider interface
 * @author Administrator
 *
 */
public interface GoodsService {

	/**
	 * find all
	 * @return
	 */
	public List<TbGoods> findAll();
	
	
	/**
	 * query according pagination 
	 * @return
	 */
	public PageResult findPage(int pageNum,int pageSize);
	
	
	/**
	 * Add
	*/
	public void add(Goods goods);
	
	
	/**
	 * update
	 */
	public void update(TbGoods goods);
	

	/**
	 * get one by id
	 * @param id
	 * @return
	 */
	public TbGoods findOne(Long id);
	
	
	/**
	 * batch delete
	 * @param ids
	 */
	public void delete(Long [] ids);

	/**
	 * fuzzy search 
	 * if the object  is not null , get the parameter from it as the search condition.
	 * @param pageNum current page number
	 * @param pageSize size of the page to display
	 * @return
	 */
	public PageResult findPage(TbGoods goods, int pageNum,int pageSize);
	
}
