package com.springmvc.common;

import com.springmvc.dao.ContractDicMapper;
import com.springmvc.entity.ContractDic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public  class DicUtil {
//    public static ApplicationContext applicationContext;
//    @Autowired
//    public static  ContractDicMapper mapper;
//    public static  List<ContractDic> dics;
//    static{
//   applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
//        mapper=applicationContext.getBean(ContractDicMapper.class);
//List<ContractDic> dics = mapper.selectAllDics();
//        System.out.println(dics.size());
//    }
    private  ApplicationContext applicationContext;
    @Autowired
    private ContractDicMapper mapper;
    public static  ContractDicMapper javamapper;
    @PostConstruct
    public void init(){
        applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        mapper=applicationContext.getBean(ContractDicMapper.class);
        javamapper=mapper;
    }
    public static void main(String[] args) {

        List<ContractDic> dics =javamapper.selectAllDics();
        System.out.println(dics.size());
    }
    public void getDics()
    {

    }
}
