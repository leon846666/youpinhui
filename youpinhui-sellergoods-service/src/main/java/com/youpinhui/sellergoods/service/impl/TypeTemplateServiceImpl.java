package com.youpinhui.sellergoods.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.JsonExpectationsHelper;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youpinhui.entity.PageResult;
import com.youpinhui.mapper.TbSpecificationMapper;
import com.youpinhui.mapper.TbSpecificationOptionMapper;
import com.youpinhui.mapper.TbTypeTemplateMapper;
import com.youpinhui.pojo.TbSpecificationExample;
import com.youpinhui.pojo.TbSpecificationOption;
import com.youpinhui.pojo.TbSpecificationOptionExample;
import com.youpinhui.pojo.TbTypeTemplate;
import com.youpinhui.pojo.TbTypeTemplateExample;
import com.youpinhui.pojo.TbTypeTemplateExample.Criteria;
import com.youpinhui.sellergoods.service.TypeTemplateService;



/**
 * Interface implement class
 * @author Leon
 *
 * Annotation service is using com.alibaba.dubbo.config.annotation.Service;
 */
@Service
public class TypeTemplateServiceImpl implements TypeTemplateService {

	@Autowired
	private TbTypeTemplateMapper typeTemplateMapper;
	
	/**
	 * find all
	 */
	@Override
	public List<TbTypeTemplate> findAll() {
		return typeTemplateMapper.selectByExample(null);
	}

	/**
	 *  query according pagination 
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbTypeTemplate> page=   (Page<TbTypeTemplate>) typeTemplateMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * Add
	 */
	@Override
	public void add(TbTypeTemplate typeTemplate) {
		typeTemplateMapper.insert(typeTemplate);		
	}

	
	/**
	 * update
	 */
	@Override
	public void update(TbTypeTemplate typeTemplate){
		typeTemplateMapper.updateByPrimaryKey(typeTemplate);
	}	
	
	/**
	 * get one by id
	 * @param id
	 * @return
	 */
	@Override
	public TbTypeTemplate findOne(Long id){
		return typeTemplateMapper.selectByPrimaryKey(id);
	}

	/**
	 * batch delete
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			typeTemplateMapper.deleteByPrimaryKey(id);
		}		
	}
	
	/**
	*	fuzzy search 
 	* if the object  is not null , get the parameter from it as the search condition.
	*
	*/
		@Override
	public PageResult findPage(TbTypeTemplate typeTemplate, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbTypeTemplateExample example=new TbTypeTemplateExample();
		Criteria criteria = example.createCriteria();
		
		if(typeTemplate!=null){			
						if(typeTemplate.getName()!=null && typeTemplate.getName().length()>0){
				criteria.andNameLike("%"+typeTemplate.getName()+"%");
			}
			if(typeTemplate.getSpecIds()!=null && typeTemplate.getSpecIds().length()>0){
				criteria.andSpecIdsLike("%"+typeTemplate.getSpecIds()+"%");
			}
			if(typeTemplate.getBrandIds()!=null && typeTemplate.getBrandIds().length()>0){
				criteria.andBrandIdsLike("%"+typeTemplate.getBrandIds()+"%");
			}
			if(typeTemplate.getCustomAttributeItems()!=null && typeTemplate.getCustomAttributeItems().length()>0){
				criteria.andCustomAttributeItemsLike("%"+typeTemplate.getCustomAttributeItems()+"%");
			}
	
		}
		
		Page<TbTypeTemplate> page= (Page<TbTypeTemplate>)typeTemplateMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

		@Override	
		public List<Map> selectOptionList() {
			// TODO Auto-generated method stub
			return typeTemplateMapper.selectOptionList();
		}
		
		// 4. DI the TbspecificationMapper 	
		
		@Autowired
		private TbSpecificationOptionMapper specificationOptionMapper;

		@Override
		public List<Map> findSpecList(Long id) {
			
			// 1. get the typeTemplate
			TbTypeTemplate typeTemplate = typeTemplateMapper.selectByPrimaryKey(id);
			
			// 2. get the typeTemlate spec_ids
			//  and convert into list 
			List<Map> list = JSON.parseArray(typeTemplate.getSpecIds(), Map.class);
			
			// 3. iterator this list
			for (Map map : list) {
				Integer specId =(Integer) map.get("id");
				
			// 5. search the id
				TbSpecificationOptionExample example = new TbSpecificationOptionExample();
				com.youpinhui.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
				criteria.andSpecIdEqualTo(new Long(specId));
				List<TbSpecificationOption> options = specificationOptionMapper.selectByExample(example);
				
				map.put("options", options);
			}
			
			return list;
		}
}
