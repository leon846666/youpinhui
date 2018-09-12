package com.youpinhui.user.service;


import java.util.List;

import com.youpinhui.entity.PageResult;
import com.youpinhui.pojo.TbUser;


/**
 * provider interface
 * @author Administrator
 *
 */
public interface UserService {

	/**
	 * find all
	 * @return
	 */
	public List<TbUser> findAll();
	
	
	/**
	 * query according pagination 
	 * @return
	 */
	public PageResult findPage(int pageNum,int pageSize);
	
	
	/**
	 * Add
	*/
	public void add(TbUser user);
	
	
	/**
	 * update
	 */
	public void update(TbUser user);
	

	/**
	 * get one by id
	 * @param id
	 * @return
	 */
	public TbUser findOne(Long id);
	
	
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
	public PageResult findPage(TbUser user, int pageNum,int pageSize);
	
}
