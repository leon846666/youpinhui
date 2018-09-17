package com.youpinhui.user.service.impl;

import java.security.KeyStore.PrivateKeyEntry;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youpinhui.entity.PageResult;
import com.youpinhui.mapper.TbUserMapper;
import com.youpinhui.pojo.TbUser;
import com.youpinhui.pojo.TbUserExample;
import com.youpinhui.pojo.TbUserExample.Criteria;
import com.youpinhui.user.service.UserService;


/**
 * Interface implement class
 * @author Leon
 *
 * Annotation service is using com.alibaba.dubbo.config.annotation.Service;
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private JmsTemplate jmsTemplage;
	
	@Autowired
	private Destination destination;
	
	@Value("${templateCode}")
	private String templateCode;
	
	@Value("${signName}")
	private String signName;
	
	/**
	 * find all
	 */
	@Override
	public List<TbUser> findAll() {
		return userMapper.selectByExample(null);
	}

	/**
	 *  query according pagination 
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbUser> page=   (Page<TbUser>) userMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * Add
	 */
	@Override
	public void add(TbUser user) {
		
		user.setCreated(new Date());//register date
		user.setUpdated(new Date());//update date
		user.setSourceType("1");// user source 
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		
		userMapper.insert(user);	
		
		
		
	}

	
	/**
	 * update
	 */
	@Override
	public void update(TbUser user){
		userMapper.updateByPrimaryKey(user);
	}	
	
	/**
	 * get one by id
	 * @param id
	 * @return
	 */
	@Override
	public TbUser findOne(Long id){
		return userMapper.selectByPrimaryKey(id);
	}

	/**
	 * batch delete
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			userMapper.deleteByPrimaryKey(id);
		}		
	}
	
	/**
	*	fuzzy search 
 	* if the object  is not null , get the parameter from it as the search condition.
	*
	*/
		@Override
	public PageResult findPage(TbUser user, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbUserExample example=new TbUserExample();
		Criteria criteria = example.createCriteria();
		
		if(user!=null){			
						if(user.getUsername()!=null && user.getUsername().length()>0){
				criteria.andUsernameLike("%"+user.getUsername()+"%");
			}
			if(user.getPassword()!=null && user.getPassword().length()>0){
				criteria.andPasswordLike("%"+user.getPassword()+"%");
			}
			if(user.getPhone()!=null && user.getPhone().length()>0){
				criteria.andPhoneLike("%"+user.getPhone()+"%");
			}
			if(user.getEmail()!=null && user.getEmail().length()>0){
				criteria.andEmailLike("%"+user.getEmail()+"%");
			}
			if(user.getSourceType()!=null && user.getSourceType().length()>0){
				criteria.andSourceTypeLike("%"+user.getSourceType()+"%");
			}
			if(user.getNickName()!=null && user.getNickName().length()>0){
				criteria.andNickNameLike("%"+user.getNickName()+"%");
			}
			if(user.getName()!=null && user.getName().length()>0){
				criteria.andNameLike("%"+user.getName()+"%");
			}
			if(user.getStatus()!=null && user.getStatus().length()>0){
				criteria.andStatusLike("%"+user.getStatus()+"%");
			}
			if(user.getHeadPic()!=null && user.getHeadPic().length()>0){
				criteria.andHeadPicLike("%"+user.getHeadPic()+"%");
			}
			if(user.getQq()!=null && user.getQq().length()>0){
				criteria.andQqLike("%"+user.getQq()+"%");
			}
			if(user.getIsMobileCheck()!=null && user.getIsMobileCheck().length()>0){
				criteria.andIsMobileCheckLike("%"+user.getIsMobileCheck()+"%");
			}
			if(user.getIsEmailCheck()!=null && user.getIsEmailCheck().length()>0){
				criteria.andIsEmailCheckLike("%"+user.getIsEmailCheck()+"%");
			}
			if(user.getSex()!=null && user.getSex().length()>0){
				criteria.andSexLike("%"+user.getSex()+"%");
			}
	
		}
		
		Page<TbUser> page= (Page<TbUser>)userMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public void createVerifiCode(final String phoneNum) {
		// 1.generate a code
		final String code=(long)(Math.random()*1000000)+"";
		
		// 2. put the code into redis
		redisTemplate.boundHashOps("verifiCode").put(phoneNum, code);
		
		System.out.println("code : "+code);
		// 3. send the code to activeMQ
		jmsTemplage.send(destination,new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				
			//	phoneNum"), map.get("templateCode"), map.get("signName"), map.get("code"));
				MapMessage message = session.createMapMessage();
				message.setString("phoneNum", phoneNum);
				message.setString("templateCode", "SMS_144451219");
			
				message.setString("signName", "优品惠");
				
				
				message.setString("code", code);
				
				return message;
			}
		});
		
		
	}

	@Override
	public boolean checkVerificationCode(String phone,String code) {
		// TODO Auto-generated method stub
		String codeInRedis =(String) redisTemplate.boundHashOps("verifiCode").get(phone);
		if(codeInRedis==null){
			return false;
		}
		if(!codeInRedis.equals(code)){
			return false;
		}
		return true;
	}
	
}
