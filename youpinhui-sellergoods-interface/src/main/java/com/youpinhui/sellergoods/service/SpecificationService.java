package com.youpinhui.sellergoods.service;
import java.util.List;

import com.youpinhui.entity.PageResult;
import com.youpinhui.pojo.TbSpecification;
import com.youpinhui.pojogroup.Specification;

/**
 * provider  Specification interface
 * @author Leon
 *
 */
public interface SpecificationService {

	/**
	 * find all 
	 * @return
	 */
	public List<TbSpecification> findAll();
	
	
	/**
	 * query according pagination 
	 * @return
	 */
	public PageResult findPage(int pageNum,int pageSize);
	
	
	/**
	 * Add
	*/
	public void add(Specification specification);
	
	
	/**
	 * update
	 */
	public void update(Specification specification);
	

	/**
	 * get one by id
	 * @param id
	 * @return
	 */
	public Specification findOne(Long id);
	
	
	/**
	 * batch delete
	 * @param ids
	 */
	public void delete(Long [] ids);

	/**
	 * 
	 * fuzzy search 
	 *  if the object  is not null , get the parameter from it as the search condition.
	 *
	 * @param pageNum current page number
	 * @param pageSize size of the page to display
	 * @return
	 */
	public PageResult findPage(TbSpecification specification, int pageNum,int pageSize);
	
}
