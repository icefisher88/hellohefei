package com.springmvc.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springmvc.common.ContractEnum;
import com.springmvc.common.ContractUtil;
import com.springmvc.entity.SellContract;
import junit.framework.TestCase;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SellContractMapperTest extends TestCase {
    private ApplicationContext applicationContext;
    @Autowired
    private SellContractMapper mapper;
    @Before
    public void setUp() throws Exception {
        applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        mapper=applicationContext.getBean(SellContractMapper.class);
    }
    @Test
    public void testGetEntityFromExcel() throws IOException, InvalidFormatException {
//        String filePath="C:\\sellcontract/jpht_mb.xlsx";
//        String contractType= ContractEnum.JPHT.type;
//        String filePath="C:\\sellcontract/gjhcjht_mb.xlsx";
//        String contractType= ContractEnum.GJHCJ.type;
//        String filePath="C:\\sellcontract/kjcxht_mb.xlsx";
//        String contractType= ContractEnum.KJCX.type;
        String filePath="C:\\sellcontract/mpht_mb.xlsx";
        String contractType= ContractEnum.MPHT.type;
        File tarFile=new File(filePath);
        List<SellContract> xx = ContractUtil.importSellContractFromExcel(tarFile, contractType);
       for(SellContract st:xx)
       {
           int result = mapper.insert(st);
           System.out.println("the insert result is:"+result);
       }
        ObjectMapper om=new ObjectMapper();
        String jsonResult=om.writeValueAsString(xx);
        System.out.println("the result is:"+jsonResult);

    }

}