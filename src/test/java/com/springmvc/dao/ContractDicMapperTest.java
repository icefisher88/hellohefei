package com.springmvc.dao;

import com.springmvc.common.DicUtil;
import com.springmvc.common.MenuCodeEnum;
import com.springmvc.entity.ContractDic;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContractDicMapperTest extends TestCase {
    private ApplicationContext applicationContext;
    @Autowired
    private ContractDicMapper mapper;
    @Before
    public void setUp() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        // 导入需要测试的
        mapper = applicationContext.getBean(ContractDicMapper.class);
    }


    @Test
    public void testCheckUtil()
    {
        String result=DicUtil.getItemCode(MenuCodeEnum.HXYWFL,"非核心业务（贸易业务）","");
        String result2=DicUtil.getItemCode(MenuCodeEnum.HTMJ,"秘密","");
        String result3=DicUtil.getItemCode(MenuCodeEnum.SFZDZX,"重大专项>载人航天",">");
        System.out.println("the size is:"+result+","+result2+","+result3);
    }

}