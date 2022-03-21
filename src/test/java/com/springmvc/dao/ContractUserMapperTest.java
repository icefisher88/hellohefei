package com.springmvc.dao;

import com.springmvc.common.DicUtil;
import com.springmvc.entity.ContractUser;
import junit.framework.TestCase;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @version V1.0
 * @Title:
 * @Package
 * @Description:
 * @author: runoob
 * @date:
 */
public class ContractUserMapperTest extends TestCase {
    private ApplicationContext applicationContext;
    @Autowired
    private ContractUserMapper mapper;
    public void setUp() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        // 导入需要测试的
        mapper = applicationContext.getBean(ContractUserMapper.class);
    }

    @Test
    public void testInsert() {
        ContractUser cUser=new ContractUser();
        cUser.setUserCode("CETCBW");
        cUser.setUserName("博微测试账号");
        cUser.setUserCompanyCode("01-0126-01-0126-C3940");
        cUser.setUserTenantCode("BWGS");
        cUser.setUserStatus(1);
        mapper.insert(cUser);
    }
    @Test
    public void testGetUserInfoByCode(){
        String userCode="CETCBW";
        ContractUser result = DicUtil.getUserInfoByCode(userCode);
        assert(StringUtils.isNotEmpty(result.getUserName()));
        ContractUser result2 = DicUtil.getUserInfoByCode("CETC");
        assert(result2==null);

    }
    @Test
    public void testGetProp(){
        String url=DicUtil.getProperty("url");
        System.out.println(url);
    }
}