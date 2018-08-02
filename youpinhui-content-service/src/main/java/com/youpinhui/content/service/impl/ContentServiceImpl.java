package com.youpinhui.content.service.impl;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
	}

	
	/**
	 * update
	 */
	@Override
	public void update(TbContent content){
		contentMapper.updateByPrimaryKey(content);
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
		TbContentExample example = new TbContentExample();
		
		Criteria criteria = example.createCriteria();
		//category id
		criteria.andCategoryIdEqualTo(categoryId);
		// status must be valid
		criteria.andStatusEqualTo("1");
		
		//sort the order by the column in database;
		example.setOrderByClause("sort_order");
		List<TbContent> listTbContent = contentMapper.selectByExample(example);
		return listTbContent;
	}
	
}
