package com.youpinhui.sellergoods.service.impl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.support.json.JSONParser;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youpinhui.entity.PageResult;
import com.youpinhui.mapper.TbBrandMapper;
import com.youpinhui.mapper.TbGoodsDescMapper;
import com.youpinhui.mapper.TbGoodsMapper;
import com.youpinhui.mapper.TbItemCatMapper;
import com.youpinhui.mapper.TbItemMapper;
import com.youpinhui.mapper.TbSellerMapper;
import com.youpinhui.pojo.TbBrand;
import com.youpinhui.pojo.TbGoods;
import com.youpinhui.pojo.TbGoodsDesc;
import com.youpinhui.pojo.TbGoodsExample;
import com.youpinhui.pojo.TbGoodsExample.Criteria;
import com.youpinhui.pojo.TbItem;
import com.youpinhui.pojo.TbItemCat;
import com.youpinhui.pojo.TbItemExample;
import com.youpinhui.pojo.TbSeller;
import com.youpinhui.pojogroup.Goods;
import com.youpinhui.sellergoods.service.GoodsService;


/**
 * Interface implement class
 * @author Leon
 *
 * Annotation service is using com.alibaba.dubbo.config.annotation.Service;
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private TbGoodsMapper goodsMapper;
	@Autowired
	private TbGoodsDescMapper goodsDescMapper;
	
	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Autowired
	private TbBrandMapper tbBrandMapper;
	
	@Autowired 
	private TbSellerMapper sellerMapper;
	
	/**
	 * find all
	 */
	@Override
	public List<TbGoods> findAll() {
		return goodsMapper.selectByExample(null);
	}

	/**
	 *  query according pagination 
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbGoods> page=   (Page<TbGoods>) goodsMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * Add
	 */
	@Override
	public void add(Goods goods) {
		
		goods.getGoods().setAuditStatus("0");// status of new created goods is 0;
		goodsMapper.insert(goods.getGoods());// insert  goods SPU info
		
		goods.getGoodsDesc().setGoodsId(goods.getGoods().getId()); // set the goodsDesc ID by good id.
		goodsDescMapper.insert(goods.getGoodsDesc());
		goods.getGoods().setIsMarketable("1");
		// title = goodsname + specification  iphone8 64g 

		//if the use of specification is enable
		//insert the  SKUs
		insertItemList(goods);
	}
	
	private void insertItemList(Goods goods){
		if ("1".equals(goods.getGoods().getIsEnableSpec())) {
			for (TbItem item : goods.getItemList()) {
				String title=goods.getGoods().getGoodsName(); //SPU name
				
				Map<String,Object> map = JSON.parseObject(item.getSpec());
				for( String key:map.keySet()){
					 title += " "+map.get(key);
				}
				item.setTitle(title);
				setItemValues(item,goods);
				itemMapper.insert(item);
		}
		}else{// if is  disable;
			TbItem item = new TbItem();
			
			item.setTitle(goods.getGoods().getGoodsName());  //title
			item.setPrice(goods.getGoods().getPrice()); // price
			item.setStatus("1");    // status 
			item.setNum(9999);   //stoc
			item.setIsDefault("1");  // defalut
			item.setSpec("{}");
			setItemValues(item,goods);
			itemMapper.insert(item);
			
		}
	}
	
	private void setItemValues(TbItem item,Goods goods){
		item.setCreateTime(new Date());  //date
		item.setUpdateTime(new Date());  //update date
		
		item.setCategoryid(goods.getGoods().getCategory3Id());  //category id
		item.setGoodsId(goods.getGoods().getId()); //good id
		item.setSellerId(goods.getGoods().getSellerId()); // seller id
		//category name
		TbItemCat itemCatory = itemCatMapper.selectByPrimaryKey(item.getCategoryid());
		item.setCategory(itemCatory.getName());
		
		//brand name
		TbBrand brand = tbBrandMapper.selectByPrimaryKey(goods.getGoods().getBrandId());
		item.setBrand(brand.getName());
		
		//seller name
		TbSeller seller = sellerMapper.selectByPrimaryKey(item.getSellerId());
		item.setSeller(seller.getNickName());
		
		//images
		String itemImages = goods.getGoodsDesc().getItemImages();
		List<Map> imgList = JSON.parseArray(itemImages,Map.class);
		
		if (imgList.size()>0) {
			item.setImage((String) imgList.get(0).get("url"));
		}
	}

	
	/**
	 * update
	 */
	@Override
	public void update(Goods goods){
		
		
		goodsMapper.updateByPrimaryKey(goods.getGoods());
		
		goodsDescMapper.updateByPrimaryKey(goods.getGoodsDesc());
		
		//delete the original SKU
		TbItemExample example = new TbItemExample();
		com.youpinhui.pojo.TbItemExample.Criteria criteria = example.createCriteria();
		criteria.andGoodsIdEqualTo(goods.getGoods().getId());
		itemMapper.deleteByExample(example);
		
		//insert the new SKUs
		insertItemList(goods);
		
	}	
	
	/**
	 * get one by id
	 * @param id
	 * @return
	 */
	@Override
	public Goods findOne(Long id){
		 Goods goods = new Goods();
		 
		 TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
		 goods.setGoods(tbGoods);
		 
		 TbGoodsDesc tbGoodDesc = goodsDescMapper.selectByPrimaryKey(id);
		 goods.setGoodsDesc(tbGoodDesc);
		 
		
		TbItemExample example= new TbItemExample();
		com.youpinhui.pojo.TbItemExample.Criteria criteria = example.createCriteria();
		criteria.andGoodsIdEqualTo(id);
		List<TbItem> itemList = itemMapper.selectByExample(example);
		 goods.setItemList(itemList);
		 
		return goods;
	}

	/**
	 * batch delete
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			//goodsMapper.deleteByPrimaryKey(id);
			
			//mark the goods is_delete
			TbGoods goods = goodsMapper.selectByPrimaryKey(id);
			goods.setIsDelete("1");
			goodsMapper.updateByPrimaryKey(goods);
			
		}		
	}
	
	/**
	*	fuzzy search 
 	* if the object  is not null , get the parameter from it as the search condition.
	*
	*/
		@Override
	public PageResult findPage(TbGoods goods, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbGoodsExample example=new TbGoodsExample();
		Criteria criteria = example.createCriteria();
		criteria.andIsDeleteIsNull();
		criteria.andIsMarketableNotEqualTo("0");
		if(goods!=null){			
			if(goods.getSellerId()!=null && goods.getSellerId().length()>0){
				criteria.andSellerIdLike("%"+goods.getSellerId()+"%");
			}
			if(goods.getGoodsName()!=null && goods.getGoodsName().length()>0){
				criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
			}
			if(goods.getAuditStatus()!=null && goods.getAuditStatus().length()>0){
				criteria.andAuditStatusLike("%"+goods.getAuditStatus()+"%");
			}
			if(goods.getIsMarketable()!=null && goods.getIsMarketable().length()>0){
				criteria.andIsMarketableLike("%"+goods.getIsMarketable()+"%");
			}
			if(goods.getCaption()!=null && goods.getCaption().length()>0){
				criteria.andCaptionLike("%"+goods.getCaption()+"%");
			}
			if(goods.getSmallPic()!=null && goods.getSmallPic().length()>0){
				criteria.andSmallPicLike("%"+goods.getSmallPic()+"%");
			}
			if(goods.getIsEnableSpec()!=null && goods.getIsEnableSpec().length()>0){
				criteria.andIsEnableSpecLike("%"+goods.getIsEnableSpec()+"%");
			}
			if(goods.getIsDelete()!=null && goods.getIsDelete().length()>0){
				criteria.andIsDeleteLike("%"+goods.getIsDelete()+"%");
			}
	
		}
		
		Page<TbGoods> page= (Page<TbGoods>)goodsMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public void updateStatus(Long[] ids, String status) {
	
		for (int i = 0; i < ids.length; i++) {
			TbGoods goods = goodsMapper.selectByPrimaryKey(ids[i]);
			goods.setAuditStatus(status);
			goodsMapper.updateByPrimaryKey(goods);
		}
		
	}

	@Override
	public void updateMarketable(Long[] ids) {

		for (int i = 0; i < ids.length; i++) {
			TbGoods goods = goodsMapper.selectByPrimaryKey(ids[i]);
			goods.setIsMarketable("0");
			goodsMapper.updateByPrimaryKey(goods);
		}
		
	}
	
	/*
	 * 
	 * search SKU by SPU ids
	 *  
	 **/
	public List<TbItem> searchItemListByGoodsIdListAndStatus(Long[] goodsIds,String status) {
		
		
		TbItemExample example = new TbItemExample();
		com.youpinhui.pojo.TbItemExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(status);
		criteria.andGoodsIdIn(Arrays.asList(goodsIds));
		
		
		List<TbItem> listItem = itemMapper.selectByExample(example);
		
		return listItem;
	}
	
	
}
