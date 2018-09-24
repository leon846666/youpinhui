package com.youpinhui.cart.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.youpinhui.cart.service.CartService;
import com.youpinhui.entity.Result;
import com.youpinhui.pojogroup.Cart;
import com.youpinhui.utils.CookieUtil;

import javassist.bytecode.analysis.Util;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Reference
	private CartService cartService;
	
	@RequestMapping("/findCartList")
	public List<Cart> findCartList() {
		// cookie only can save string value(json value);
		String cartListString = CookieUtil.getCookieValue(request, "cartList","UTF-8");
		
		if(cartListString==null|| cartListString.equals("")||cartListString.equals("null")){
			cartListString="[]";
		}

		List<Cart> cartList_cookie = JSON.parseArray(cartListString,Cart.class);
				
		return cartList_cookie;
	}

	@RequestMapping("/addGoodsToCartList")
	public Result addGoodsToCartList(Long itemId,Integer number) {
		
		try {
			// get cart list from cookie
			List<Cart> cartList_cookie = findCartList();
			// call the function in service
			cartList_cookie = cartService.addGoodsToCartList(cartList_cookie, itemId, number);
			// put the new cart into cookie
			CookieUtil.setCookie(request, response, "cartList", JSON.toJSONString(cartList_cookie),3600*24,"UTF-8");
			
			return new Result(true,"add to cart success ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return new Result(false,"add to cart failed");
		}
	}
	
}
