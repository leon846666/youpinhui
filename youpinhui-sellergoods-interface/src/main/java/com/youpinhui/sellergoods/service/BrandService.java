package com.youpinhui.sellergoods.service;

import java.util.List;
import java.util.Map;

import com.youpinhui.entity.PageResult;
import com.youpinhui.pojo.TbBrand;

/**
 *  provider  Brand interface
 * @author Leon
 *
 */
public interface BrandService {
	
	/**
	 * find all 
	 * @return
	 */
	public List<TbBrand> findAll();

	/**
	 * query according pagination 
	 * @return
	 */
	public PageResult findPage(int page, int size);

	
	/**
	 * Add
	*/
	public void add(TbBrand brand);

	
	/**
	 * update
	 */
	public TbBrand findOne(long id);

	
	/**
	 * get one by id
	 * @param id
	 * @return
	 */
	public void update(TbBrand brand);
	
	
	/**
	 * batch delete
	 * @param ids
	 */
	public void delete(Long[] ids);

	
	/**
	 * 
	 * fuzzy search 
	 *  if the object  is not null , get the parameter from it as the search condition.
	 *
	 * @param pageNum current page number
	 * @param pageSize size of the page to display
	 * @return
	 */
	public PageResult findPage(TbBrand brand, int page, int size);
	
	
	/**
	 * 
	 * return select2 dropdown menu data 
	 */
	public List<Map> selectOptionList();
	
}
