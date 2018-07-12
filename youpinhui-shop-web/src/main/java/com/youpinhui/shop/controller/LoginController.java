package com.youpinhui.shop.controller;

import java.nio.charset.MalformedInputException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

	@RequestMapping("/getLoginUsername")
	public Map getLoginUsername() {
		Map map = new HashMap<>();
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		map.put("loginName", name);
		
		return map;
		
	}
}
