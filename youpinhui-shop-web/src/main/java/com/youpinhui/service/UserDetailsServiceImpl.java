package com.youpinhui.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.youpinhui.pojo.TbSeller;
import com.youpinhui.sellergoods.service.SellerService;



public class UserDetailsServiceImpl implements UserDetailsService {

	private SellerService sellerService;
	
	//choose to use setter as the way of DI;
	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//System.out.println("inside of UserDetailsServiceImpl");
		
		List<GrantedAuthority> grantAuth=new ArrayList<>();
		grantAuth.add(new SimpleGrantedAuthority("ROLE_SELLER"));
		
		// 1. get the object by username(id);
		TbSeller tbSeller = sellerService.findOne(username);
		
		// 2. check if the object found.
		if(tbSeller!=null){
			
			// 3. check the seller status is 1 or not, 1 means the seller is settled.
			if(tbSeller.getStatus().equals("1")){
				// 4 return  a user with an antuority
				return new User(username,tbSeller.getPassword(),grantAuth);
			}else{
				return null;
			}
		}
		// if not found , return null;
		else{
			return null;
		}
		
	
	}

}
