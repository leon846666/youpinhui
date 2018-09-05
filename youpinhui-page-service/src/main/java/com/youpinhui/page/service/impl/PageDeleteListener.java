package com.youpinhui.page.service.impl;

import java.io.Serializable;
import java.util.Arrays;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.youpinhui.page.service.ItemPageService;
@Component
public class PageDeleteListener implements MessageListener {

	@Autowired
	private ItemPageService itemPageService;

	@Override
	public void onMessage(Message message) {
		
		ObjectMessage objectMessage=(ObjectMessage)message;
		Long[] goodsIds;
		try {
			goodsIds = (Long[])objectMessage.getObject();
			System.out.println("got the msg of delete page's id");

			boolean re = itemPageService.deleteItemHtml(goodsIds);
			System.out.println("delete result "+re);
			
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
