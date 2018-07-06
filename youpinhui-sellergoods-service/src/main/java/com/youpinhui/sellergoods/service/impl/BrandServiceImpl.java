package com.youpinhui.sellergoods.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.youpinhui.mapper.TbBrandMapper;
import com.youpinhui.pojo.TbBrand;
import com.youpinhui.sellergoods.service.BrandService;

/**
 *  Brand Interface
 * @author Leon
 *
 */
@Service
public class BrandServiceImpl implements BrandService{

	@Autowired
	private TbBrandMapper tbBrandMapper;
	
	public List<TbBrand> findAll() {
		System.out.println("adas");
		return tbBrandMapper.selectByExample(null);
	}
	
}
