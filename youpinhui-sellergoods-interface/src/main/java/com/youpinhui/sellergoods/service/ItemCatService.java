package com.youpinhui.sellergoods.service;
import java.util.List;
import com.youpinhui.pojo.TbItemCat;

import  com.youpinhui.entity.PageResult;
/**
 * provider interface
 * @author Administrator
 *
 */
public interface ItemCatService {

	/**
	 * find all
	 * @return
	 */
	public List<TbItemCat> findAll();
	
	
	/**
	 * query according pagination 
	 * @return
	 */
	public PageResult findPage(int pageNum,int pageSize);
	
	
	/**
	 * Add
	*/
	public void add(TbItemCat itemCat);
	
	
	/**
	 * update
	 */
	public void update(TbItemCat itemCat);
	

	/**
	 * get one by id
	 * @param id
	 * @return
	 */
	public TbItemCat findOne(Long id);
	
	
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
	public PageResult findPage(TbItemCat itemCat, int pageNum,int pageSize);


	public List<TbItemCat> findByParentId(Long parentId);
	
}
