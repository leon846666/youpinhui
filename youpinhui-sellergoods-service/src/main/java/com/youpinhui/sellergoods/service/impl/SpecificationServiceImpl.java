package com.youpinhui.sellergoods.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youpinhui.entity.PageResult;
import com.youpinhui.mapper.TbSpecificationMapper;
import com.youpinhui.mapper.TbSpecificationOptionMapper;
import com.youpinhui.pojo.TbSpecification;
import com.youpinhui.pojo.TbSpecificationExample;
import com.youpinhui.pojo.TbSpecificationExample.Criteria;
import com.youpinhui.pojo.TbSpecificationOption;
import com.youpinhui.pojo.TbSpecificationOptionExample;
import com.youpinhui.pojogroup.Specification;
import com.youpinhui.sellergoods.service.SpecificationService;


/**
 * Specification Interface implement class
 * @author  Leon
 *
 * Annotation service is using com.alibaba.dubbo.config.annotation.Service;
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;
	
	@Autowired
	private TbSpecificationOptionMapper tbSpecificationOptionMapper;
	
	/**
	 * find all specifications
	 */
	@Override
	public List<TbSpecification> findAll() {
		return specificationMapper.selectByExample(null);
	}

	/**
	 * query according pagination 
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbSpecification> page=   (Page<TbSpecification>) specificationMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * Add
	 */
	@Override
	public void add(Specification specification) {
		
		//get tbSpecification in object specification
		TbSpecification tbSpecification = specification.getSpecification();
		// Insert	
		specificationMapper.insert(tbSpecification);		
		 
		//get SpecificationOptionList in object specification
		List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();
		
		//foreach get every TbSpecificationOption object
		for (TbSpecificationOption option : specificationOptionList) {
			
			//assign attribute id
			option.setSpecId(tbSpecification.getId());
			//insert
			tbSpecificationOptionMapper.insert(option);
			
		}
		
	}

	
	/**
	 * update
	 */
	@Override
	public void update(Specification specification){
		
		//get tbSpecification in object specification
		TbSpecification tbSpecification = specification.getSpecification();
		// update	
		specificationMapper.updateByPrimaryKey(tbSpecification);		
		
		
		
		//delete the original data in table specification option 
		
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		com.youpinhui.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
		criteria.andSpecIdEqualTo(tbSpecification.getId());
		tbSpecificationOptionMapper.deleteByExample(example);
		
		//get SpecificationOptionList in object specification
		
		List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();
		
		//foreach get every TbSpecificationOption object
		for (TbSpecificationOption option : specificationOptionList) {
			
			//assign attribute id
			option.setSpecId(tbSpecification.getId());
			//insert again
			tbSpecificationOptionMapper.insert(option);
			
		}
	}	
	
	/**
	 * get one by id
	 * @param id
	 * @return
	 */
	@Override
	public Specification findOne(Long id){
		
		Specification specification = new Specification();
		TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
		specification.setSpecification(tbSpecification);
		
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		com.youpinhui.pojo.TbSpecificationOptionExample.Criteria createCriteria = example.createCriteria();
		createCriteria.andSpecIdEqualTo(id);
		List<TbSpecificationOption> specificationOptionList = tbSpecificationOptionMapper.selectByExample(example);
		System.out.println("size :"+specificationOptionList.size());
		specification.setSpecificationOptionList(specificationOptionList);
		return specification;
	}

	/**
	 * batch delete
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){

			// 1. delete the specification
			specificationMapper.deleteByPrimaryKey(id);
			
			// 2.delete the specificaiton options
			TbSpecificationOptionExample example = new TbSpecificationOptionExample();
			com.youpinhui.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
			criteria.andSpecIdEqualTo(id);
			tbSpecificationOptionMapper.deleteByExample(example);
			
		}		
	}
	
	/**
	 * fuzzy search 
	 *  if the object  is not null , get the parameter from it as the search condition.
	 */
	@Override
	public PageResult findPage(TbSpecification specification, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbSpecificationExample example=new TbSpecificationExample();
		Criteria criteria = example.createCriteria();
		
		if(specification!=null){			
						if(specification.getSpecName()!=null && specification.getSpecName().length()>0){
				criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
			}
	
		}
		
		Page<TbSpecification> page= (Page<TbSpecification>)specificationMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}


	@Override
	public List<Map> selectOptionList() {
	
		return specificationMapper.selectOptionList();
	}
	
}
