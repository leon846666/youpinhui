package com.youpinhui.content.service;

import java.util.List;
import com.youpinhui.pojo.TbContent;
import com.youpinhui.entity.PageResult;
import com.youpinhui.pojo.TbContentCategory;

/**
 * provider interface
 * @author Administrator
 *
 */
public interface ContentService {

	/**
	 * find all
	 * @return
	 */
	public List<TbContent> findAll();
	
	
	/**
	 * query according pagination 
	 * @return
	 */
	public PageResult findPage(int pageNum,int pageSize);
	
	
	/**
	 * Add
	*/
	public void add(TbContent content);
	
	
	/**
	 * update
	 */
	public void update(TbContent content);
	

	/**
	 * get one by id
	 * @param id
	 * @return
	 */
	public TbContent findOne(Long id);
	
	
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
	public PageResult findPage(TbContent content, int pageNum,int pageSize);
	
	
	public List<TbContent> findByCategoryId(Long categoryId);
}
