package com.youpinhui.sellergoods.service;
import java.util.List;
import java.util.Map;

import com.youpinhui.entity.PageResult;
import com.youpinhui.pojo.TbTypeTemplate;


/**
 * provider TypeTemplate interface
 * @author Leon
 *
 */
public interface TypeTemplateService {

	/**
	 * find all
	 * @return
	 */
	public List<TbTypeTemplate> findAll();
	
	
	/**
	 * query according pagination 
	 * @return
	 */
	public PageResult findPage(int pageNum,int pageSize);
	
	
	/**
	 * Add
	*/
	public void add(TbTypeTemplate typeTemplate);
	
	
	/**
	 * update
	 */
	public void update(TbTypeTemplate typeTemplate);
	

	/**
	 * get one by id
	 * @param id
	 * @return
	 */
	public TbTypeTemplate findOne(Long id);
	
	
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
	public PageResult findPage(TbTypeTemplate typeTemplate, int pageNum,int pageSize);


	public List<Map> selectOptionList();
	
	
	//get the specification list by the type_template id;
	public List<Map> findSpecList(Long id);
	
}
