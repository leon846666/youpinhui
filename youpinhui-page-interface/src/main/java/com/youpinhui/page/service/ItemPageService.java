package com.youpinhui.page.service;

public interface ItemPageService {

	/**
	 * 
	 * genrate item detail page
	 * 
	 */
	public boolean genItemHtml(Long goodsId);		
	
	
	
	/**
	 * 
	 * 
	 * delete item detail page 
	 */
	
	public boolean deleteItemHtml(Long[] goodsIds);
}
