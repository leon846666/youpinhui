package com.youpinhui.sellergoods.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youpinhui.entity.PageResult;
import com.youpinhui.mapper.TbBrandMapper;
import com.youpinhui.pojo.TbBrand;
import com.youpinhui.pojo.TbBrandExample;
import com.youpinhui.pojo.TbBrandExample.Criteria;
import com.youpinhui.sellergoods.service.BrandService;

/**
 *  Brand Interface implement class
 * @author Leon
 *
 * Annotation service is using com.alibaba.dubbo.config.annotation.Service;
 */
@Service
@Transactional
public class BrandServiceImpl implements BrandService{

	@Autowired
	private TbBrandMapper tbBrandMapper;
	
	public List<TbBrand> findAll() {
		return tbBrandMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(int page, int size) {
		System.out.println("dddddddddddddddddddd");
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

	@Override
	public void update(TbBrand brand) {
		 tbBrandMapper.updateByPrimaryKey(brand);
		
	}

	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			tbBrandMapper.deleteByPrimaryKey(id);
			}
	}

	@Override
	public PageResult findPage(TbBrand brand, int pageNumber, int pageSize) {
		PageHelper.startPage(pageNumber, pageSize);
		TbBrandExample example = new TbBrandExample();
		
		Criteria criteria = example.createCriteria();
		if(brand!=null){
			
			if (brand.getName()!=null&&brand.getName().length()>0) {
				criteria.andNameLike("%" + brand.getName() + "%");
			}
			if (brand.getFirstChar()!=null&&brand.getFirstChar().length()>0) {
				criteria.andFirstCharLike("%" + brand.getFirstChar() + "%");
			}
		}
		
		Page<TbBrand> page = (Page<TbBrand> ) tbBrandMapper.selectByExample(example);
		
		return new PageResult(page.getTotal(),page.getResult());
	}

	@Override	
	public List<Map> selectOptionList() {
		// TODO Auto-generated method stub
		return tbBrandMapper.selectOptionList();
	}
	
}
