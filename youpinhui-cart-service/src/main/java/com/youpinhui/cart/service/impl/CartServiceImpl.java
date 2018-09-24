package com.youpinhui.cart.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.sun.tools.doclets.internal.toolkit.MemberSummaryWriter;
import com.youpinhui.cart.service.CartService;
import com.youpinhui.mapper.TbItemMapper;
import com.youpinhui.pojo.TbItem;
import com.youpinhui.pojo.TbOrderItem;
import com.youpinhui.pojogroup.Cart;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private TbItemMapper tbItemMapper;
	
	
	@Override
	public List addGoodsToCartList(List<Cart> cartList, Long itemId, Integer number) {
		// 1. get SKU object by itemId
		TbItem item = tbItemMapper.selectByPrimaryKey(itemId);
		if(item==null){
			throw new RuntimeException("goods doesn't exist");
		}
		if(!item.getStatus().equals("1")){
			throw new RuntimeException("goods status is not valid");
		}
		
		// 2. get sellerId by SKU 
		String sellerId = item.getSellerId();
		// 3. get Cart Object by sellerId
		Cart cart = findCartBySellerId(cartList,sellerId);
		// 4. if the cart list doesn't contain the  cart object  from the seller
			
		if(cart==null){
			
			// 4.1 create a new cart object from the new seller 
			cart = new Cart();
			cart.setSellerId(sellerId);//ID
			cart.setSellName(item.getSeller());//seller name
			List<TbOrderItem> orderItemsList= new ArrayList<>(); 
			TbOrderItem orderItem = createTbOrderItem(item, number);
			
			orderItemsList.add(orderItem); 
			
			// 4.2 put the new cart object into cart list
			cart.setOrderItemList(orderItemsList);
			cartList.add(cart);
		}else{
			// 5. if the cart contains the cart object from the seller 
			// check if the goods is already in the cart object
			TbOrderItem orderItem = searchOrderItemByItemId(cart.getOrderItemList(), itemId);
			//  5.1 if it doesn't exist create new subitem into the  cart object 
			if(orderItem==null){
				orderItem=	createTbOrderItem(item, number);
				cart.getOrderItemList().add(orderItem); 
			}else{
				//  5.2 if it exist update the quantity and the price	
				orderItem.setNum(orderItem.getNum()+number);
				orderItem.setTotalFee(new BigDecimal(orderItem.getPrice().doubleValue()*orderItem.getNum()));
				
				
				if(orderItem.getNum()<=0){
					cart.getOrderItemList().remove(orderItem);
				}
				if(cart.getOrderItemList().size()==0){
					cartList.remove(cart);
				}
			}
		
		}
		
		
		
		return cartList;
	}
	private TbOrderItem createTbOrderItem(TbItem item,Integer number){
		TbOrderItem orderItem = new TbOrderItem();
		
		orderItem.setGoodsId(item.getGoodsId());
		orderItem.setItemId(item.getId());
		orderItem.setNum(number);
		orderItem.setPicPath(item.getImage());
		orderItem.setPrice(item.getPrice());
		orderItem.setSellerId(item.getSellerId());
		orderItem.setTitle(item.getTitle());
		orderItem.setTotalFee(new BigDecimal(item.getPrice().doubleValue()*number));
		return orderItem;
	}
	private TbOrderItem searchOrderItemByItemId(List<TbOrderItem> list,Long itemId){
		for (TbOrderItem orderItem : list) {
			if(orderItem.getItemId().longValue()==itemId){
				return orderItem;
			}
		}
		
		return null;
	}
	private Cart findCartBySellerId(List<Cart> list,String sellerId){
		
		for (Cart cart : list) {
			if(cart.getSellerId().equals(sellerId)){
				return cart;
			}
		}
		return null;
	}
	
}
