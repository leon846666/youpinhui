package com.youpinhui.search.service.impl;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.youpinhui.pojo.TbItem;
import com.youpinhui.search.service.ItemSearchService;

@Component
public class ItemDeleteListener implements MessageListener {

	@Autowired
	private ItemSearchService itemSearchService;
	
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		ObjectMessage objectMessageMessage = (ObjectMessage)message;
		try {
			Long [] ids = (Long[]) objectMessageMessage.getObject();
			System.out.println("got the message in ActiveMQ for deleteing");
			itemSearchService.deleteByGoodsIds(ids);
			System.out.println("complete delete");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
