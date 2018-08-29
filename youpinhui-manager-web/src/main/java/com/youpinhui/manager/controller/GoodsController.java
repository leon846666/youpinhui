package com.youpinhui.manager.controller;


import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.youpinhui.entity.PageResult;
import com.youpinhui.entity.Result;
import com.youpinhui.page.service.ItemPageService;
import com.youpinhui.pojo.TbGoods;
import com.youpinhui.pojo.TbItem;
import com.youpinhui.pojogroup.Goods;
import com.youpinhui.sellergoods.service.GoodsService;


/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

	@Reference(timeout=100000)
	private GoodsService goodsService;
	
	/*@Reference(timeout=100000)
	private ItemSearchService itemSearchService;
	*/
	
	/**
	 * test case find all
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbGoods> findAll(){			
		return goodsService.findAll();
	}
	
	
	/**
	 * pagination
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return goodsService.findPage(page, rows);
	}
	
	
	
	/**
	 *  update
	 * @param goods
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody Goods goods){
		try {
			goodsService.update(goods);
			return new Result(true, "update success");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "update��failed");
		}
	}	
	
	/**
	 * get one by id
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public Goods findOne(Long id){
		return goodsService.findOne(id);		
	}
	
	/**
	 * batch delete
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			goodsService.delete(ids);
			
			//itemSearchService.deleteByGoodsIds(ids);
			
			return new Result(true, "delete success"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "delete failed");
		}
	}
	
		/**
		 * fuzzy search
	 * if the object is not null, get the parameter from it as the search condition.
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbGoods goods, int page, int rows  ){
		return goodsService.findPage(goods, page, rows);		
	}
	
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired 
	private Destination queueSolrDestination;
	
	/**
	 * 
	 * update status
	 */
	@RequestMapping("/updateStatus")
	private Result updateStatus(Long [] ids,String status) {
		try {
			goodsService.updateStatus(ids, status);
			
			if("1".equals(status)){
				
				// ***** import into solr
				// 1.get the data need to be imported
				List<TbItem> listItem = goodsService.searchItemListByGoodsIdListAndStatus(ids, status);
				
				// 2.import into solr
				//itemSearchService.importList(listItem);
				final String jsonString = JSON.toJSONString(listItem);
				
				jmsTemplate.send(queueSolrDestination,new MessageCreator() {
					
					@Override
					public Message createMessage(Session session) throws JMSException {
						// TODO Auto-generated method stub
						return session.createTextMessage(jsonString);
					}
				});
				
				
				// ***** generate goods html page
				// for loop to get each goodsId
				for (Long goodsId : ids) {
					// generate
					itemPageService.genItemHtml(goodsId);
				}
				
			}
			
			
			return  new Result(true,"suceess");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return  new Result(false,"failed");
		}
		
	}
	
	@Reference(timeout=40000)
	private ItemPageService itemPageService;
	
	
	/**
	 * generate html for item  
	 **/
	@RequestMapping("/genHtml")
	private void  geneHtml(Long goodsId) {
			itemPageService.genItemHtml(goodsId);
	}
	
}
