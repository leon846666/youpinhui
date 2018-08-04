package com.youpinhui.content.service.impl;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youpinhui.content.service.ContentService;
import com.youpinhui.entity.PageResult;
import com.youpinhui.mapper.TbContentMapper;
import com.youpinhui.pojo.TbContent;
import com.youpinhui.pojo.TbContentExample;
import com.youpinhui.pojo.TbContentExample.Criteria;


/**
 * Interface implement class
 * @author Leon
 *
 * Annotation service is using com.alibaba.dubbo.config.annotation.Service;
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	/**
	 * find all
	 */
	@Override
	public List<TbContent> findAll() {
		return contentMapper.selectByExample(null);
	}

	/**
	 *  query according pagination 
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbContent> page=   (Page<TbContent>) contentMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * Add
	 */
	@Override
	public void add(TbContent content) {
		contentMapper.insert(content);		
		redisTemplate.boundHashOps("content").delete(content.getCategoryId());
		
		
	}

	
	/**
	 * update
	 */
	@Override
	public void update(TbContent content){
		//find the old category Id
		Long categoryId = contentMapper.selectByPrimaryKey(content.getId()).getCategoryId();
		
		//delete the old cache
		redisTemplate.boundHashOps("content").delete(categoryId);
		
		contentMapper.updateByPrimaryKey(content);

		//delete the current categoryid cache
		if(categoryId.longValue()!=content.getCategoryId().longValue()){
			redisTemplate.boundHashOps("content").delete(content.getCategoryId());
		}
		
	}	
	
	/**
	 * get one by id
	 * @param id
	 * @return
	 */
	@Override
	public TbContent findOne(Long id){
		return contentMapper.selectByPrimaryKey(id);
	}

	/**
	 * batch delete
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			
			//delete cache
			TbContent tbContent = contentMapper.selectByPrimaryKey(id);
			redisTemplate.boundHashOps("content").delete(tbContent.getCategoryId());
			contentMapper.deleteByPrimaryKey(id);
			
		}		
	}
	
	/**
	*	fuzzy search 
 	* if the object  is not null , get the parameter from it as the search condition.
	*
	*/
		@Override
	public PageResult findPage(TbContent content, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbContentExample example=new TbContentExample();
		Criteria criteria = example.createCriteria();
		
		if(content!=null){			
						if(content.getTitle()!=null && content.getTitle().length()>0){
				criteria.andTitleLike("%"+content.getTitle()+"%");
			}
			if(content.getUrl()!=null && content.getUrl().length()>0){
				criteria.andUrlLike("%"+content.getUrl()+"%");
			}
			if(content.getPic()!=null && content.getPic().length()>0){
				criteria.andPicLike("%"+content.getPic()+"%");
			}
			if(content.getStatus()!=null && content.getStatus().length()>0){
				criteria.andStatusLike("%"+content.getStatus()+"%");
			}
	
		}
		
		Page<TbContent> page= (Page<TbContent>)contentMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}
		

	@Override
	public List<TbContent> findByCategoryId(Long categoryId) {
		
		List<TbContent> listContent = (List<TbContent>) redisTemplate.boundHashOps("content").get(categoryId);
		
		if(listContent==null){
			System.out.println("find data from database...");
			TbContentExample example = new TbContentExample();
			
			Criteria criteria = example.createCriteria();
			//category id
			criteria.andCategoryIdEqualTo(categoryId);
			// status must be valid
			criteria.andStatusEqualTo("1");
			
			//sort the order by the column in database;
			example.setOrderByClause("sort_order");
			listContent = contentMapper.selectByExample(example);
			System.out.println("cache data...");
			redisTemplate.boundHashOps("content").put(categoryId, listContent);
			
		}else{
			System.out.println("found data fron cache....");
		}
		return listContent
		
	;
	}
	
}
