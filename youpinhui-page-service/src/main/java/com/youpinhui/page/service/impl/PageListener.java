package com.youpinhui.page.service.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.youpinhui.page.service.ItemPageService;

@Component
public class PageListener implements MessageListener{

	@Autowired
	private ItemPageService itemPageService;
	
	@Override
	public void onMessage(Message message) {

		TextMessage textMessage = (TextMessage)message;
		try {
			String text = textMessage.getText();
			System.out.println("got the msg of goodsId to generate Page");
			
			boolean result = itemPageService.genItemHtml(Long.parseLong(text));
			System.out.println("page generate result : "+result);
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
}
