package com.springmvc.dao;

import com.springmvc.entity.Message;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

public class MessageMapperTest {

    private ApplicationContext applicationContext;

    @Autowired
    private MessageMapper mapper;
    @Before
    public void setUp() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        // 导入需要测试的
        mapper = applicationContext.getBean(MessageMapper.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void insert() {
        Message mess=new Message();
        mess.setCommand("吃饭");
        mess.setContent("睡觉");
        mess.setDescription("ok");
        int result=mapper.insert(mess);
        System.out.println(result);
        assert(result==1);
    }
}