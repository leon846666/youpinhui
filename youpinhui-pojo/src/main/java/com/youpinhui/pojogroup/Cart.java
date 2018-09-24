package com.youpinhui.pojogroup;

import java.io.Serializable;
import java.util.List;


import com.youpinhui.pojo.TbOrderItem;



/**
 *  
 *  Object Cart
 *  
 */

public class Cart implements Serializable {

	public Cart(String sellerId, String sellName, List<TbOrderItem> orderItemList) {
		super();
		this.sellerId = sellerId;
		this.sellName = sellName;
		this.orderItemList = orderItemList;
	}

	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String sellerId;
	private String sellName;
	
	private List<TbOrderItem> orderItemList;//ordered Items list

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellName() {
		return sellName;
	}

	public void setSellName(String sellName) {
		this.sellName = sellName;
	}

	public List<TbOrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<TbOrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
}
