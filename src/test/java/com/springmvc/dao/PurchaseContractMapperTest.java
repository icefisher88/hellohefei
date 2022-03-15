package com.springmvc.dao;

import com.alibaba.fastjson.JSONArray;
import com.springmvc.entity.PurchaseContract;
import junit.framework.TestCase;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PurchaseContractMapperTest extends TestCase {
    private ApplicationContext applicationContext;
    @Autowired
    private PurchaseContractMapper mapper;
    @Before
    public void setUp(){
        applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
        mapper=applicationContext.getBean(PurchaseContractMapper.class);
    }
    @After
    public void tearDown(){

    }
    @Test
    public void testInsert() {
    }
    @Test
    public void testInsertByExcel() {
       String  filePath="E:\\cght_mb.xlsx";
        System.out.println(filePath);
       Boolean execute=false;
        File targetFile=new File(filePath);
        if(targetFile.exists()){
            try {
                OPCPackage tarPackage=OPCPackage.open(targetFile);
                XSSFWorkbook xb=new XSSFWorkbook(tarPackage);
                XSSFSheet xs=xb.getSheetAt(0);
                System.out.println(xs.getSheetName());
                int startIndex=2;
                int lastIndex=xs.getPhysicalNumberOfRows();
                System.out.println(lastIndex+"");
                for(int i=startIndex;i<lastIndex;i++){
                    XSSFRow curRow=xs.getRow(i);
                    PurchaseContract cght=getSingleContract(curRow);
                    if(cght!=null)
                    {
                        if(execute)
                        {
                            mapper.insert(cght);
                        }
                        else
                        {
                           System.out.println(JSONArray.toJSON(cght).toString());
                        }
                    }
                    else
                    {
                        System.out.println("实体转换不正确");
                    }
                }
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            finally {

            }
        }

    }
    public static PurchaseContract getSingleContract(XSSFRow curRow) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        PurchaseContract cght=new PurchaseContract();
        String contractName=curRow.getCell(0).getStringCellValue();//合同名称
        String contractCode=curRow.getCell(1).getStringCellValue();//合同编号
        String contractStatus=curRow.getCell(2).getStringCellValue();//合同状态
        String contractSecretLev=curRow.getCell(3).getStringCellValue();//合同密级
        String purchaserName=curRow.getCell(4).getStringCellValue();//采购方
        String purchaserCode=curRow.getCell(5).getStringCellValue();//采购人代码
        Date signTime=null;//签订时间
        if(curRow.getCell(6).getDateCellValue()!=null)
        {
            signTime=curRow.getCell(6).getDateCellValue();
        }
        Date effectiveDate=null;//生效时间
        if(curRow.getCell(7).getDateCellValue()!=null)
        {
            effectiveDate=curRow.getCell(6).getDateCellValue();
        }
        String pricingManner=curRow.getCell(8).getStringCellValue();//计价方式
        BigDecimal contractAmount=new BigDecimal(0);//合同金额
        if(StringUtils.isNotEmpty(curRow.getCell(9).getStringCellValue()))
        {
            contractAmount=new BigDecimal(curRow.getCell(9).getStringCellValue());
        }
        String currency=curRow.getCell(10).getStringCellValue();//币种
        BigDecimal exchangeRate=null;//汇率
        if(StringUtils.isNotEmpty(curRow.getCell(11).getStringCellValue()))
        {
            exchangeRate=new BigDecimal(curRow.getCell(11).getStringCellValue());
        }
        BigDecimal taxRate=null;//税率
        if(StringUtils.isNotEmpty(curRow.getCell(12).getStringCellValue()))
        {
            taxRate=new BigDecimal(curRow.getCell(12).getStringCellValue());
        }
        String payWay=curRow.getCell(13).getStringCellValue();//付款方式
        String capitalSource=curRow.getCell(14).getStringCellValue();//资金来源
        String frameworkAgreement=curRow.getCell(15).getStringCellValue();//框架协议编号
        String tender=curRow.getCell(16).getStringCellValue();//是否招标
        String blockNumber=curRow.getCell(17).getStringCellValue();//采购包和标段编号
        String blockName=curRow.getCell(18).getStringCellValue();//采购包和标段名称
        String note=curRow.getCell(19).getStringCellValue();//备注
        String supplierName=curRow.getCell(20).getStringCellValue();//卖方名称
        String supplierCode=curRow.getCell(21).getStringCellValue();//卖方代码
        String country=curRow.getCell(22).getStringCellValue();//卖方国别
        String supplierClassify=curRow.getCell(23).getStringCellValue();//卖方性质
        String isQualifiedSupplier=curRow.getCell(24).getStringCellValue();//供应商类型
        String isInUnitCompany=curRow.getCell(25).getStringCellValue();//是否集团内单位
        String purchaseOrgForm=curRow.getCell(26).getStringCellValue();//采购组织形式
        String purchaseWay=curRow.getCell(26).getStringCellValue();//采购方式
        String budgetTypes=curRow.getCell(27).getStringCellValue();//预算类型
        BigDecimal investmentBudget=null;//采购包或投标预算
        if(StringUtils.isNotEmpty(curRow.getCell(28).getStringCellValue()))
        {
            investmentBudget=new BigDecimal(curRow.getCell(28).getStringCellValue());
        }
        String evaluationMethod=curRow.getCell(29).getStringCellValue();//评标评价方法
        String technicalWeight=curRow.getCell(30).getStringCellValue();//技术权重
        String businessWeight=curRow.getCell(31).getStringCellValue();//商务权重
        Date invitationDate=null;//发标日期
        if(StringUtils.isNotEmpty(curRow.getCell(32).getStringCellValue()))
        {
            invitationDate=sdf.parse(curRow.getCell(32).getStringCellValue());
        }
        Date openingDate=null;//开标日期
        if(StringUtils.isNotEmpty(curRow.getCell(33).getStringCellValue()))
        {
            openingDate=sdf.parse(curRow.getCell(33).getStringCellValue());
        }
        Date startTime=new Date();//开始时间
        if(StringUtils.isNotEmpty(curRow.getCell(34).getStringCellValue()))
        {
            startTime=sdf.parse(curRow.getCell(34).getStringCellValue());
        }
        Date endTime=new Date();//结束时间
        if(StringUtils.isNotEmpty(curRow.getCell(35).getStringCellValue()))
        {
            endTime=sdf.parse(curRow.getCell(35).getStringCellValue());
        }
        String fulfillmentPlace=curRow.getCell(36).getStringCellValue();//履约地点
        String performanceGuarantee=curRow.getCell(37).getStringCellValue();//履约担保
        String contKeepStatus=curRow.getCell(38).getStringCellValue();//履约状态
        String performanceStage=curRow.getCell(39).getStringCellValue();//履约阶段
        String instructions=curRow.getCell(40).getStringCellValue();//履约阶段说明
        String supplierEvaluation=curRow.getCell(41).getStringCellValue();//履约评价
        String whetherToChange=curRow.getCell(42).getStringCellValue();//是否有变更
        String changeOfSubsidiary=curRow.getCell(43).getStringCellValue();//变更明细
        String procurementPlatform=curRow.getCell(44).getStringCellValue();//电子招标平台
        //---------------赋值给实体对象
        cght.setContractCode(contractCode);
        cght.setContractName(contractName);
        cght.setContractStatus(contractStatus);
        cght.setContractSecretLev(contractSecretLev);
        cght.setPurchaserName(purchaserName);
        cght.setPurchaserCode(purchaserCode);
        cght.setSignTime(signTime);
        cght.setEffectiveDate(effectiveDate);
        cght.setPricingManner(pricingManner);
        cght.setContractAmount(contractAmount);
        cght.setCurrency(currency);
        cght.setExchangeRate(exchangeRate);
        cght.setTaxRate(taxRate);
        cght.setPayWay(payWay);
        cght.setCapitalSource(capitalSource);
        cght.setFrameworkAgreement(frameworkAgreement);
        cght.setTender(tender);
        cght.setBlockNumber(blockNumber);
        cght.setBlockName(blockName);
        cght.setNote(note);
        cght.setSupplierName(supplierName);
        cght.setSupplierCode(supplierCode);
        cght.setCountry(country);
        cght.setSupplierClassify(supplierClassify);
        cght.setIsQualifiedSupplier(isQualifiedSupplier);
        cght.setIsinUnitCompany(isInUnitCompany);
        cght.setPurchaseOrgForm(purchaseOrgForm);
        cght.setPurchaseWay(purchaseWay);
        cght.setBudgetTypes(budgetTypes);
        cght.setInvestmentBudget(investmentBudget);
        cght.setEvaluationMethod(evaluationMethod);
        cght.setTechnicalWeight(technicalWeight);
        cght.setBusinessWeight(businessWeight);
        cght.setInvitationDate(invitationDate);
        cght.setOpeningDate(openingDate);
        cght.setStartTime(startTime);
        cght.setEndTime(endTime);
        cght.setFulfillmentPlace(fulfillmentPlace);
        cght.setPerformanceGuarantee(performanceGuarantee);
        cght.setContKeepStatus(contKeepStatus);
        cght.setPerformanceStage(performanceStage);
        cght.setInstructions(instructions);
        cght.setSupplierEvaluation(supplierEvaluation);
        cght.setWhetherToChange(whetherToChange);
        cght.setChangeOfSubsidiary(changeOfSubsidiary);
        cght.setProcurementPlatform(procurementPlatform);
        return cght;
    }

}