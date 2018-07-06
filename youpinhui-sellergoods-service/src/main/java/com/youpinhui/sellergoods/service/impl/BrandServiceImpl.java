package com.youpinhui.sellergoods.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youpinhui.entity.PageResult;
import com.youpinhui.mapper.TbBrandMapper;
import com.youpinhui.pojo.TbBrand;
import com.youpinhui.sellergoods.service.BrandService;

/**
 *  Brand Interface implement class
 * @author Leon
 *
 */
@Service
public class BrandServiceImpl implements BrandService{

	@Autowired
	private TbBrandMapper tbBrandMapper;
	
	public List<TbBrand> findAll() {
		return tbBrandMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int page, int size) {
		PageHelper.startPage(page, size);
		Page<TbBrand> pageTbBrand = (Page<TbBrand> ) tbBrandMapper.selectByExample(null);
		
		return new PageResult(pageTbBrand.getTotal(),pageTbBrand.getResult());
	}

	@Override
	public void add(TbBrand brand) {
		tbBrandMapper.insert(brand);
		
	}

	@Override
	public TbBrand findOne(long id) {
		
		return tbBrandMapper.selectByPrimaryKey(id);
	}
	
}
