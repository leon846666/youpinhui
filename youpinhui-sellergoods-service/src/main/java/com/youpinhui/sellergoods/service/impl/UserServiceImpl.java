package com.youpinhui.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.youpinhui.sellergoods.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		System.out.println("dddddddddddddddddddd");
		return "item";
	}

}
