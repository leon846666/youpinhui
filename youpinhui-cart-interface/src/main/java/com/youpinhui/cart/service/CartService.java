package com.youpinhui.cart.service;

import java.util.List;

import com.youpinhui.pojogroup.Cart;

public interface CartService {
	
	/**
	 * add goods to Cart 
	 * @param list
	 * @param itemId
	 * @param number
	 * @return
	 */
	public List addGoodsToCartList(List<Cart> list,Long itemId,Integer number);	
}
