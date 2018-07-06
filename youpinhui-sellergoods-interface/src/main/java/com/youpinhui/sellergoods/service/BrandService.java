package com.youpinhui.sellergoods.service;

import java.util.List;

import com.youpinhui.entity.PageResult;
import com.youpinhui.pojo.TbBrand;

/**
 *  Brand Interface
 * @author Leon
 *
 */
public interface BrandService {

	public List<TbBrand> findAll();

	public PageResult findPage(int page, int size);

	public void add(TbBrand brand);

	public TbBrand findOne(long id);
	
}
