package com.youpinhui.sellergoods.service;
import java.util.List;

import com.youpinhui.entity.PageResult;
import com.youpinhui.entity.Result;
import com.youpinhui.pojo.TbSeller;


/**
 * provider interface
 * @author Administrator
 *
 */
public interface SellerService {

	/**
	 * find all
	 * @return
	 */
	public List<TbSeller> findAll();
	
	
	/**
	 * query according pagination 
	 * @return
	 */
	public PageResult findPage(int pageNum,int pageSize);
	
	
	/**
	 * Add
	*/
	public void add(TbSeller seller);
	
	
	/**
	 * update
	 */
	public void update(TbSeller seller);
	

	/**
	 * get one by id
	 * @param id
	 * @return
	 */
	public TbSeller findOne(String id);
	
	
	/**
	 * batch delete
	 * @param ids
	 */
	public void delete(String [] ids);

	/**
	 * fuzzy search 
	 * if the object  is not null , get the parameter from it as the search condition.
	 * @param pageNum current page number
	 * @param pageSize size of the page to display
	 * @return
	 */
	public PageResult findPage(TbSeller seller, int pageNum,int pageSize);
	
	/**
	 * update Seller status 
	 * 
	 * @param sellerId selected seller's id
	 * @param status  status to change
	 * 	
	 */
	public void updateStatus( String sellerId,String status);
	
	
	
}
