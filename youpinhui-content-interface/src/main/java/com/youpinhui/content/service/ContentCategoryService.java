package com.youpinhui.content.service;

import java.util.List;

import com.youpinhui.entity.PageResult;
import com.youpinhui.pojo.TbContentCategory;


/**
 * provider interface
 * @author Administrator
 *
 */
public interface ContentCategoryService {

	/**
	 * find all
	 * @return
	 */
	public List<TbContentCategory> findAll();
	
	
	/**
	 * query according pagination 
	 * @return
	 */
	public PageResult findPage(int pageNum,int pageSize);
	
	
	/**
	 * Add
	*/
	public void add(TbContentCategory contentCategory);
	
	
	/**
	 * update
	 */
	public void update(TbContentCategory contentCategory);
	

	/**
	 * get one by id
	 * @param id
	 * @return
	 */
	public TbContentCategory findOne(Long id);
	
	
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
	public PageResult findPage(TbContentCategory contentCategory, int pageNum,int pageSize);
	
}
