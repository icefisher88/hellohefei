package com.springmvc.dao;

import com.springmvc.entity.Uploadfile;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

public class UploadfileMapperTest extends TestCase {

    private ApplicationContext applicationContext;
    @Autowired
    private UploadfileMapper mapper;
    @Before
    public void setUp(){
        applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        mapper=applicationContext.getBean(UploadfileMapper.class);
    }
    @After
    public void tearDown(){

    }
    @Test
    public void testInsert(){

                Uploadfile uf=new Uploadfile();
                uf.setOriginname("test.xls");
                uf.setNewname("220302163939.xls");
                uf.setSize("125KB");
                uf.setStatus("new");
                uf.setUploaddate(new Date());
                int result=mapper.insert(uf);
                System.out.println("the result is:"+result);
                assert (result==1);
    }

    @Test
    public void testGetAllUploadFiles() {
        List<Uploadfile> xx = mapper.getAllUploadFiles();
        System.out.println(xx.get(0).getOriginname());
        assert (xx.size()==1);
    }
}