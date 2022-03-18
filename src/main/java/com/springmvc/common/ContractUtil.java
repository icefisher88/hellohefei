package com.springmvc.common;

import com.springmvc.entity.PurchaseContract;
import com.springmvc.entity.SellContract;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContractUtil {
    static Logger logger = Logger.getLogger(ContractUtil.class.getName ()) ;

    public static List<SellContract> importContractFromExcel(File excelFile,String contractType) throws InvalidFormatException, IOException {

        List<SellContract> transSellContracts=new ArrayList<>();
//        try {
            OPCPackage tarPackage=OPCPackage.open(excelFile, PackageAccess.READ);
            XSSFWorkbook xb= null;
            xb = new XSSFWorkbook(tarPackage);
            XSSFSheet xs=xb.getSheetAt(0);
            int startIndex=2;
            int lastIndex=xs.getLastRowNum();
            logger.debug("the open excel last rownum is:"+lastIndex);
            for(int i=startIndex;i<lastIndex;i++){
                XSSFRow curRow=xs.getRow(i);
                if(StringUtils.isEmpty(curRow.getCell(0).getStringCellValue())){
                    logger.debug("the last hanld Row num is:"+(i-1));
                    break;//
                }
                SellContract finalContract = getSingleSellContract(curRow, contractType);
                transSellContracts.add(finalContract);
            }
//        }catch (InvalidFormatException e) {
//            logger.error("打开Excel文件出错");
//            e.printStackTrace();
//        } catch (IOException e) {
//            logger.error("Excel文件I/O读写出错");
//            e.printStackTrace();
//        }
        return transSellContracts;
    }

    static SellContract getSingleSellContract(XSSFRow cRow,String contractType)
    {
        SellContract sellContract=new SellContract();
        String theirBusiness=contractType;//所属业务
        String contractName=null;//合同名称
        String contractCode=null;//合同编号
        String contractSecretLev=null;//合同密级
        String contractOwner=null;//合同甲方
        String ownerUnifyCode=null;//甲方代码
        String ownerClassify=null;//甲方分类
        String contractPartyB=null;//合同乙方
        String partyBUnifyCode=null;//乙方代码
        BigDecimal contractAmount=null;//合同金额
        String currency=null;//币种
        BigDecimal exchangeRate=null;//汇率
        Date signTime=null;//签订时间
        Date startTime=new Date();//开始时间
        Date endTime=new Date();//结束时间
        String fourPlate=null;//四大板块
        String coreBusinessClass=null;//核心业务分类
        String projectCode=null;//项目令号
        String groupProjectId=null;//集团项目ID
        String contractRemark=null;//合同备注说明
        contractName=cRow.getCell(0).getStringCellValue();
        contractCode=cRow.getCell(1).getStringCellValue();
        if(StringUtils.isNotEmpty(cRow.getCell(2).getStringCellValue()))
            contractSecretLev=cRow.getCell(2).getStringCellValue();
        if(StringUtils.isNotEmpty(cRow.getCell(3).getStringCellValue()))
            contractOwner=cRow.getCell(3).getStringCellValue();
        if(StringUtils.isNotEmpty(cRow.getCell(3).getStringCellValue()))
            contractOwner=cRow.getCell(3).getStringCellValue();
        if(StringUtils.isNotEmpty(cRow.getCell(4).getStringCellValue()))
            ownerUnifyCode=cRow.getCell(4).getStringCellValue();
        if(StringUtils.isNotEmpty(cRow.getCell(5).getStringCellValue()))
            ownerClassify=cRow.getCell(5).getStringCellValue();
        if(StringUtils.isNotEmpty(cRow.getCell(6).getStringCellValue()))
            contractPartyB=cRow.getCell(6).getStringCellValue();
        if(StringUtils.isNotEmpty(cRow.getCell(7).getStringCellValue()))
            partyBUnifyCode=cRow.getCell(7).getStringCellValue();
        if(cRow.getCell(8).getNumericCellValue()>0)
        {
            contractAmount=new BigDecimal(cRow.getCell(8).getNumericCellValue());
        }
        if(StringUtils.isNotEmpty(cRow.getCell(9).getStringCellValue()))
            currency=cRow.getCell(9).getStringCellValue();
        if(cRow.getCell(10).getNumericCellValue()>0)
        {
            exchangeRate=new BigDecimal(cRow.getCell(10).getNumericCellValue());
        }
        if(cRow.getCell(11).getDateCellValue()!=null)
        {
            signTime=cRow.getCell(11).getDateCellValue();
        }
        if(cRow.getCell(12).getDateCellValue()!=null)
        {
            startTime=cRow.getCell(12).getDateCellValue();
        }
        if(cRow.getCell(13).getDateCellValue()!=null)
        {
            endTime=cRow.getCell(13).getDateCellValue();
        }
        if(StringUtils.isNotEmpty(cRow.getCell(14).getStringCellValue()))
            fourPlate=cRow.getCell(14).getStringCellValue();
        if(StringUtils.isNotEmpty(cRow.getCell(15).getStringCellValue()))
            coreBusinessClass=cRow.getCell(15).getStringCellValue();
        if(StringUtils.isNotEmpty(cRow.getCell(16).getStringCellValue()))
            projectCode=cRow.getCell(16).getStringCellValue();
        if(StringUtils.isNotEmpty(cRow.getCell(17).getStringCellValue()))
            groupProjectId=cRow.getCell(17).getStringCellValue();
        if(StringUtils.isNotEmpty(cRow.getCell(18).getStringCellValue()))
            contractRemark=cRow.getCell(18).getStringCellValue();
        //实例化基础属性
        sellContract.setContractCode(contractCode);
        sellContract.setContractName(contractName);
        sellContract.setTheirBusiness(theirBusiness);
        sellContract.setContractSecretLev(contractSecretLev);
        sellContract.setContractAmount(contractAmount);
        sellContract.setCurrency(currency);
        sellContract.setExchangeRate(exchangeRate);
        sellContract.setSignTime(signTime);
        sellContract.setStartTime(startTime);
        sellContract.setEndTime(endTime);
        sellContract.setContractOwner(contractOwner);
        sellContract.setOwnerClassify(ownerClassify);
        sellContract.setContractPartyB(contractPartyB);
        sellContract.setPartyBUnifyCode(partyBUnifyCode);
        sellContract.setContractRemark(contractRemark);
        sellContract.setFourPlate(fourPlate);
        sellContract.setCoreBusinessClass(coreBusinessClass);
        sellContract.setProjectCode(projectCode);
        sellContract.setGroupProjectId(groupProjectId);
        sellContract.setOwnerUnifyCode(ownerUnifyCode);
        //区分合同类型，进行处理
        if(contractType.equals(ContractEnum.JPHT.type))
        {
            if(StringUtils.isNotEmpty(cRow.getCell(19).getStringCellValue()))
                sellContract.setArmyClass2(cRow.getCell(19).getStringCellValue());
            if(StringUtils.isNotEmpty(cRow.getCell(20).getStringCellValue()))
                sellContract.setChargeArmyOffice(cRow.getCell(20).getStringCellValue());
            if(StringUtils.isNotEmpty(cRow.getCell(21).getStringCellValue()))
                sellContract.setProductType(cRow.getCell(21).getStringCellValue());
            if(StringUtils.isNotEmpty(cRow.getCell(22).getStringCellValue()))
                sellContract.setMakeType(cRow.getCell(22).getStringCellValue());
            if(StringUtils.isNotEmpty(cRow.getCell(23).getStringCellValue()))
                sellContract.setProfessionalField(cRow.getCell(23).getStringCellValue());
            if(StringUtils.isNotEmpty(cRow.getCell(24).getStringCellValue()))
                sellContract.setIsGreatNewCont(cRow.getCell(24).getStringCellValue());
            if(StringUtils.isNotEmpty(cRow.getCell(25).getStringCellValue()))
                sellContract.setIsGreatProject(cRow.getCell(25).getStringCellValue());
        }
        else if(contractType.equals(ContractEnum.GJHCJ.type)||contractType.equals(ContractEnum.GJHSX.type))
        {
            if(StringUtils.isNotEmpty(cRow.getCell(19).getStringCellValue()))
                sellContract.setChannel(cRow.getCell(19).getStringCellValue());
            if(StringUtils.isNotEmpty(cRow.getCell(20).getStringCellValue()))
                sellContract.setImpExpTrade(cRow.getCell(20).getStringCellValue());
            if(StringUtils.isNotEmpty(cRow.getCell(21).getStringCellValue()))
                sellContract.setClassifyOne(cRow.getCell(21).getStringCellValue());
            if(StringUtils.isNotEmpty(cRow.getCell(22).getStringCellValue()))
                sellContract.setClassifyTwo(cRow.getCell(22).getStringCellValue());
            if(StringUtils.isNotEmpty(cRow.getCell(23).getStringCellValue()))
                sellContract.setClassifyThree(cRow.getCell(23).getStringCellValue());
            if(StringUtils.isNotEmpty(cRow.getCell(24).getStringCellValue()))
                sellContract.setNationality(cRow.getCell(24).getStringCellValue());
        }
        else if(contractType.equals(ContractEnum.KJCX.type))
        {
            if(StringUtils.isNotEmpty(cRow.getCell(19).getStringCellValue()))
                sellContract.setTechEarnings(cRow.getCell(19).getStringCellValue());
            if(StringUtils.isNotEmpty(cRow.getCell(20).getStringCellValue()))
                sellContract.setSciTechClassify(cRow.getCell(20).getStringCellValue());
            if(StringUtils.isNotEmpty(cRow.getCell(21).getStringCellValue()))
                sellContract.setTechnicalB(cRow.getCell(21).getStringCellValue());
            if(StringUtils.isNotEmpty(cRow.getCell(22).getStringCellValue()))
                sellContract.setTechnicalB2(cRow.getCell(22).getStringCellValue());
        }
        else if(contractType.equals(ContractEnum.MPHT.type))
        {
            if(StringUtils.isNotEmpty(cRow.getCell(19).getStringCellValue()))
                sellContract.setIndustryClassify(cRow.getCell(19).getStringCellValue());
        }
        return handleTranslateContract(sellContract);
    }

    public static SellContract handleTranslateContract(SellContract originContract){
        SellContract newContract=originContract;
        //合同密级转换
        if(StringUtils.isNotEmpty(originContract.getContractSecretLev())) {
            String oldSecretLev=originContract.getContractSecretLev();
            String newSecretLev = DicUtil.getItemCode(MenuCodeEnum.HTMJ, oldSecretLev, "");
            newContract.setContractSecretLev(newSecretLev);
            logger.info("ContractSecretLev Change, From: "+oldSecretLev+" To: "+newSecretLev);
        }
        //甲方分类转换
        if(StringUtils.isNotEmpty(originContract.getOwnerClassify())) {
            String oldOwnerClassify=originContract.getOwnerClassify();
            String newOwnerClassify = DicUtil.getItemCode(MenuCodeEnum.JFFL, oldOwnerClassify, "");
            newContract.setOwnerClassify(newOwnerClassify);
            logger.info("OwnerClassify Change, From: "+oldOwnerClassify+" To: "+newOwnerClassify);
        }
        //币种转换
        if(StringUtils.isNotEmpty(originContract.getCurrency())) {
            String oldCurrency=originContract.getCurrency();
            String newCurrency = DicUtil.getItemCode(MenuCodeEnum.BZ, oldCurrency, "");
            newContract.setCurrency(newCurrency);
            logger.info("Currency Change, From: "+oldCurrency+" To: "+newCurrency);
        }
        //四大板块转换
        if(StringUtils.isNotEmpty(originContract.getFourPlate())) {
            String oldFourPlate=originContract.getFourPlate();
            String newFourPlate = DicUtil.getItemCode(MenuCodeEnum.SDBK, oldFourPlate, ">");
            newContract.setFourPlate(newFourPlate);
            logger.info("FourPlate Change, From: "+oldFourPlate+" To: "+newFourPlate);
        }
        //核心业务分类转换
        if(StringUtils.isNotEmpty(originContract.getCoreBusinessClass())) {
            String oldCoreBusinessClass=originContract.getCoreBusinessClass();
            String newCoreBusinessClass = DicUtil.getItemCode(MenuCodeEnum.HXYWFL, oldCoreBusinessClass, "");
            newContract.setCoreBusinessClass(newCoreBusinessClass);
            logger.info("CoreBusinessClass Change, From: "+oldCoreBusinessClass+" To: "+newCoreBusinessClass);
        }
        //合同甲方分类转换
        if(StringUtils.isNotEmpty(originContract.getArmyClass2())) {
            String oldArmyClass2=originContract.getArmyClass2();
            String newArmyClass2 = DicUtil.getItemCode(MenuCodeEnum.JFFLJP2, oldArmyClass2, "");
            newContract.setArmyClass2(newArmyClass2);
            logger.info("ArmyClass2 Change, From: "+oldArmyClass2+" To: "+newArmyClass2);
        }
        //主管军方机关转换
        if(StringUtils.isNotEmpty(originContract.getChargeArmyOffice())) {
            String oldChargeArmyOffice=originContract.getChargeArmyOffice();
            String newChargeArmyOffice = DicUtil.getItemCode(MenuCodeEnum.ZGJFJG, oldChargeArmyOffice, ">");
            newContract.setChargeArmyOffice(newChargeArmyOffice);
            logger.info("ChargeArmyOffice Change, From: "+oldChargeArmyOffice+" To: "+newChargeArmyOffice);
        }

        //产品类型转换
        if(StringUtils.isNotEmpty(originContract.getProductType())) {
            String oldProductType=originContract.getProductType();
            String newProductType = DicUtil.getItemCode(MenuCodeEnum.CPLX, oldProductType, "");
            newContract.setProductType(newProductType);
            logger.info("ProductType Change, From: "+oldProductType+" To: "+newProductType);
        }
        //制造类型转换
        if(StringUtils.isNotEmpty(originContract.getMakeType())) {
            String oldMakeType=originContract.getMakeType();
            String newMakeType = DicUtil.getItemCode(MenuCodeEnum.ZZLX, oldMakeType, "");
            newContract.setMakeType(newMakeType);
            logger.info("MakeType Change, From: "+oldMakeType+" To: "+newMakeType);
        }

        //专业领域转换
        if(StringUtils.isNotEmpty(originContract.getProfessionalField())) {
            String oldProfessionalField=originContract.getProfessionalField();
            String newProfessionalField = DicUtil.getItemCode(MenuCodeEnum.ZYLY, oldProfessionalField, ">");
            newContract.setProfessionalField(newProfessionalField);
            logger.info("ProfessionalField Change, From: "+oldProfessionalField+" To: "+newProfessionalField);
        }
        //是否重大新签合同转换
        if(StringUtils.isNotEmpty(originContract.getIsGreatNewCont())) {
            String oldIsGreatNewCont=originContract.getIsGreatNewCont();
            String newIsGreatNewCont = DicUtil.getItemCode(MenuCodeEnum.SF, oldIsGreatNewCont, "");
            newContract.setIsGreatNewCont(newIsGreatNewCont);
            logger.info("IsGreatNewCont Change, From: "+oldIsGreatNewCont+" To: "+newIsGreatNewCont);
        }

        //是否重大专项转换
        if(StringUtils.isNotEmpty(originContract.getIsGreatProject())) {
            String oldIsGreatProject=originContract.getIsGreatProject();
            String newIsGreatProject = DicUtil.getItemCode(MenuCodeEnum.SFZDZX, oldIsGreatProject, ">");
            newContract.setIsGreatProject(newIsGreatProject);
            logger.info("IsGreatProject Change, From: "+oldIsGreatProject+" To: "+newIsGreatProject);
        }
        //渠道转换
        if(StringUtils.isNotEmpty(originContract.getChannel())) {
            String oldChannel=originContract.getChannel();
            String newChannel = DicUtil.getItemCode(MenuCodeEnum.QD, oldChannel, "");
            newContract.setChannel(newChannel);
            logger.info("Channel Change, From: "+oldChannel+" To: "+newChannel);
        }
        //进出口方式转换
        if(StringUtils.isNotEmpty(originContract.getImpExpTrade())) {
            String oldImpExpTrade=originContract.getImpExpTrade();
            String newImpExpTrade = DicUtil.getItemCode(MenuCodeEnum.JCKFS, oldImpExpTrade, "");
            newContract.setImpExpTrade(newImpExpTrade);
            logger.info("Channel Change, From: "+oldImpExpTrade+" To: "+newImpExpTrade);
        }
        //国际化分类一转换
        if(StringUtils.isNotEmpty(originContract.getClassifyOne())) {
            String oldClassifyOne=originContract.getClassifyOne();
            String newClassifyOne = DicUtil.getItemCode(MenuCodeEnum.GJHFL1, oldClassifyOne, ">");
            newContract.setClassifyOne(newClassifyOne);
            logger.info("ClassifyOne Change, From: "+oldClassifyOne+" To: "+newClassifyOne);
        }
        //国际化分类二转换
        if(StringUtils.isNotEmpty(originContract.getClassifyTwo())) {
            String oldClassifyTwo=originContract.getClassifyTwo();
            String newClassifyTwo = DicUtil.getItemCode(MenuCodeEnum.GJHFL2, oldClassifyTwo, "");
            newContract.setClassifyTwo(newClassifyTwo);
            logger.info("ClassifyTwo Change, From: "+oldClassifyTwo+" To: "+newClassifyTwo);
        }
        //国际化分类三转换
        if(StringUtils.isNotEmpty(originContract.getClassifyThree())) {
            String oldClassifyThree=originContract.getClassifyThree();
            String newClassifyThree = DicUtil.getItemCode(MenuCodeEnum.GJHFL3, oldClassifyThree, ">");
            newContract.setClassifyThree(newClassifyThree);
            logger.info("ClassifyThree Change, From: "+oldClassifyThree+" To: "+newClassifyThree);
        }

        //国际化国别转换
        if(StringUtils.isNotEmpty(originContract.getNationality())) {
            String oldNationality=originContract.getNationality();
            String newNationality = DicUtil.getItemCode(MenuCodeEnum.GB, oldNationality, ">");
            newContract.setNationality(newNationality);
            logger.info("Nationality Change, From: "+oldNationality+" To: "+newNationality);
        }

        //合同性质分类转换
        if(StringUtils.isNotEmpty(originContract.getTechEarnings())) {
            String oldTechEarnings=originContract.getTechEarnings();
            String newTechEarnings = DicUtil.getItemCode(MenuCodeEnum.HTXZFL, oldTechEarnings, "");
            newContract.setTechEarnings(newTechEarnings);
            logger.info("TechEarnings Change, From: "+oldTechEarnings+" To: "+newTechEarnings);
        }
        //科技创新分类转换
        if(StringUtils.isNotEmpty(originContract.getSciTechClassify())) {
            String oldSciTechClassify=originContract.getSciTechClassify();
            String newSciTechClassify = DicUtil.getItemCode(MenuCodeEnum.KJCXFL, oldSciTechClassify, ">");
            newContract.setSciTechClassify(newSciTechClassify);
            logger.info("SciTechClassify Change, From: "+oldSciTechClassify+" To: "+newSciTechClassify);
        }
        //技术收益分类转换
        if(StringUtils.isNotEmpty(originContract.getTechnicalB())) {
            String oldTechnicalB=originContract.getTechnicalB();
            String newTechnicalB = DicUtil.getItemCode(MenuCodeEnum.JSSY, oldTechnicalB, "");
            newContract.setTechnicalB(newTechnicalB);
            logger.info("TechnicalB Change, From: "+oldTechnicalB+" To: "+newTechnicalB);
        }
        //技术收益二分类转换
        if(StringUtils.isNotEmpty(originContract.getTechnicalB2())) {
            String oldTechnicalB2=originContract.getTechnicalB2();
            String newTechnicalB2 = DicUtil.getItemCode(MenuCodeEnum.JSSY2, oldTechnicalB2, "");
            newContract.setTechnicalB2(newTechnicalB2);
            logger.info("TechnicalB2 Change, From: "+oldTechnicalB2+" To: "+newTechnicalB2);
        }
        //行业分类转换
        if(StringUtils.isNotEmpty(originContract.getIndustryClassify())) {
            String oldIndustryClassify=originContract.getIndustryClassify();
            String newIndustryClassify = DicUtil.getItemCode(MenuCodeEnum.JSSY2, oldIndustryClassify, "");
            newContract.setIndustryClassify(newIndustryClassify);
            logger.info("IndustryClassify Change, From: "+oldIndustryClassify+" To: "+newIndustryClassify);
        }
        return newContract;
    }

    public static PurchaseContract handleTranslateContract(PurchaseContract originContract){
        PurchaseContract newContract=originContract;

        //合同状态转换
        if(StringUtils.isNotEmpty(originContract.getContractStatus())) {
            String oldStatus=originContract.getContractStatus();
            String newStatus = DicUtil.getItemCode(MenuCodeEnum.HTZT_CG, oldStatus, "");
            newContract.setContractStatus(newStatus);
            logger.info("ContractStatus Change, From: "+oldStatus+" To: "+newStatus);
        }

        //合同密级转换
        if(StringUtils.isNotEmpty(originContract.getContractSecretLev())) {
            String oldSecretLev=originContract.getContractSecretLev();
            String newSecretLev = DicUtil.getItemCode(MenuCodeEnum.HTMJ, oldSecretLev, "");
            newContract.setContractSecretLev(newSecretLev);
            logger.info("ContractSecretLev Change, From: "+oldSecretLev+" To: "+newSecretLev);
        }

        //币种转换
        if(StringUtils.isNotEmpty(originContract.getCurrency())) {
            String oldCurrency=originContract.getCurrency();
            String newCurrency = DicUtil.getItemCode(MenuCodeEnum.BZ, oldCurrency, "");
            newContract.setCurrency(newCurrency);
            logger.info("Currency Change, From: "+oldCurrency+" To: "+newCurrency);
        }

        //计价方式转换
        if(StringUtils.isNotEmpty(originContract.getPricingManner())) {
            String oldPricingManner=originContract.getPricingManner();
            String newPricingManner = DicUtil.getItemCode(MenuCodeEnum.JJFS, oldPricingManner, "");
            newContract.setPricingManner(newPricingManner);
            logger.info("PricingManner Change, From: "+oldPricingManner+" To: "+newPricingManner);
        }
        //付款方式转换
        if(StringUtils.isNotEmpty(originContract.getPayWay())){
            String oldPayWay=originContract.getPayWay();
            String newPayWay = DicUtil.getItemCode(MenuCodeEnum.FKFS, oldPayWay, "");
            newContract.setPayWay(newPayWay);
            logger.info("PayWay Change, From: "+oldPayWay+" To: "+newPayWay);
        }
        //资金来源转换
        if(StringUtils.isNotEmpty(originContract.getCapitalSource())){
            String oldCapitalSource=originContract.getCapitalSource();
            String newCapitalSource = DicUtil.getItemCode(MenuCodeEnum.ZJLY, oldCapitalSource, "");
            newContract.setCapitalSource(newCapitalSource);
            logger.info("CapitalSource Change, From: "+oldCapitalSource+" To: "+newCapitalSource);
        }
        //是否招标转换
        if(StringUtils.isNotEmpty(originContract.getTender())){
            String oldTender=originContract.getTender();
            String newTender = DicUtil.getItemCode(MenuCodeEnum.SF, oldTender, "");
            newContract.setTender(newTender);
            logger.info("Tender Change, From: "+oldTender+" To: "+newTender);
        }
        //卖方国别转换
        if(StringUtils.isNotEmpty(originContract.getCountry())){
            String oldCountry=originContract.getCountry();
            String newCountry= DicUtil.getItemCode(MenuCodeEnum.GB, oldCountry, ">");
            newContract.setCountry(newCountry);
            logger.info("Country Change, From: "+oldCountry+" To: "+newCountry);
        }
        //卖方性质转换
        if(StringUtils.isNotEmpty(originContract.getSupplierClassify())){
            String oldClass=originContract.getSupplierClassify();
            String newClass= DicUtil.getItemCode(MenuCodeEnum.GFXZ, oldClass, "");
            newContract.setSupplierClassify(newClass);
            logger.info("SupplierClassify Change, From: "+oldClass+" To: "+newClass);
        }
        //供应商类型转换
        if(StringUtils.isNotEmpty(originContract.getIsQualifiedSupplier())){
            String oldQualifiedSupplier=originContract.getIsQualifiedSupplier();
            String newQualifiedSupplier= DicUtil.getItemCode(MenuCodeEnum.GFLX, oldQualifiedSupplier, "");
            newContract.setIsQualifiedSupplier(newQualifiedSupplier);
            logger.info("QualifiedSupplier Change, From: "+oldQualifiedSupplier+" To: "+newQualifiedSupplier);
        }
        //是否集团内单位转换
        if(StringUtils.isNotEmpty(originContract.getIsinUnitCompany())){
            String oldIsinUnitCompany=originContract.getIsinUnitCompany();
            String newIsinUnitCompany= DicUtil.getItemCode(MenuCodeEnum.SF, oldIsinUnitCompany, "");
            newContract.setIsinUnitCompany(newIsinUnitCompany);
            logger.info("IsinUnitCompany Change, From: "+oldIsinUnitCompany+" To: "+newIsinUnitCompany);
        }

        //评标_评价方法转换
        if(StringUtils.isNotEmpty(originContract.getEvaluationMethod())){
            String oldEvaluationMethod=originContract.getEvaluationMethod();
            String newEvaluationMethod= DicUtil.getItemCode(MenuCodeEnum.PB_PJFF, oldEvaluationMethod, "");
            newContract.setEvaluationMethod(newEvaluationMethod);
            logger.info("EvaluationMethod Change, From: "+oldEvaluationMethod+" To: "+newEvaluationMethod);
        }

        //采购组织形式转换
        if(StringUtils.isNotEmpty(originContract.getPurchaseOrgForm())){
            String oldPurchaseOrgForm=originContract.getPurchaseOrgForm();
            String newPurchaseOrgForm= DicUtil.getItemCode(MenuCodeEnum.CGZZLX,oldPurchaseOrgForm, "");
            newContract.setPurchaseOrgForm(newPurchaseOrgForm);
            logger.info("PurchaseOrgForm Change, From: "+oldPurchaseOrgForm+" To: "+newPurchaseOrgForm);
        }

        //采购方式转换
        if(StringUtils.isNotEmpty(originContract.getPurchaseWay())){
            String oldPurchaseWay=originContract.getPurchaseWay();
            String newPurchaseWay= DicUtil.getItemCode(MenuCodeEnum.CGFS, oldPurchaseWay, "");
            newContract.setPurchaseWay(newPurchaseWay);
            logger.info("PurchaseWay Change, From: "+oldPurchaseWay+" To: "+newPurchaseWay);
        }
        //预算类型转换
        if(StringUtils.isNotEmpty(originContract.getBudgetTypes())){
            String oldBudgetTypes=originContract.getBudgetTypes();
            String newBudgetTypes= DicUtil.getItemCode(MenuCodeEnum.YSLX, oldBudgetTypes, "");
            newContract.setBudgetTypes(newBudgetTypes);
            logger.info("BudgetType Change, From: "+oldBudgetTypes+" To: "+newBudgetTypes);
        }
        //履约地点转换
        if(StringUtils.isNotEmpty(originContract.getFulfillmentPlace())){
            String oldFulfillmentPlace=originContract.getFulfillmentPlace();
            String newFulfillmentPlace= DicUtil.getItemCode(MenuCodeEnum.LYDD, oldFulfillmentPlace, "");
            newContract.setFulfillmentPlace(newFulfillmentPlace);
            logger.info("FulfillmentPlace Change, From: "+oldFulfillmentPlace+" To: "+newFulfillmentPlace);
        }
        //履约担保转换
        if(StringUtils.isNotEmpty(originContract.getPerformanceGuarantee())){
            String oldPerformanceGuarantee=originContract.getPerformanceGuarantee();
            String newGuarantee= DicUtil.getItemCode(MenuCodeEnum.LYDB, oldPerformanceGuarantee, "");
            newContract.setPerformanceGuarantee(newGuarantee);
            logger.info("PerformanceGuarantee Change, From: "+oldPerformanceGuarantee+" To: "+newGuarantee);
        }
        //履约状态转换
        if(StringUtils.isNotEmpty(originContract.getContKeepStatus())){
            String oldContKeepStatus=originContract.getContKeepStatus();
            String newKeepStatus= DicUtil.getItemCode(MenuCodeEnum.LVZT, oldContKeepStatus, "");
            newContract.setContKeepStatus(newKeepStatus);
            logger.info("ContKeepStatus Change, From: "+oldContKeepStatus+" To: "+newKeepStatus);
        }
        //履约状态转换
        if(StringUtils.isNotEmpty(originContract.getPerformanceStage())){
            String oldPerformanceStage=originContract.getPerformanceStage();
            String newPerformanceStage= DicUtil.getItemCode(MenuCodeEnum.LYJD, oldPerformanceStage, "");
            newContract.setPerformanceStage(newPerformanceStage);
            logger.info("PerformanceStage Change, From: "+oldPerformanceStage+" To: "+newPerformanceStage);
        }
        //履约评价转换
        if(StringUtils.isNotEmpty(originContract.getSupplierEvaluation())){
            String oldSupplierEvaluation=originContract.getSupplierEvaluation();
            String newEvaluation= DicUtil.getItemCode(MenuCodeEnum.LVPJ, oldSupplierEvaluation, "");
            newContract.setSupplierEvaluation(newEvaluation);
            logger.info("SupplierEvaluation Change, From: "+oldSupplierEvaluation+" To: "+newEvaluation);
        }
        //是否有变更转换
        if(StringUtils.isNotEmpty(originContract.getWhetherToChange())){
            String oldWhetherToChange=originContract.getWhetherToChange();
            String newWhetherToChange= DicUtil.getItemCode(MenuCodeEnum.SF, oldWhetherToChange, "");
            newContract.setWhetherToChange(newWhetherToChange);
            logger.info("WhetherToChange Change, From: "+oldWhetherToChange+" To: "+newWhetherToChange);
        }

        return newContract;
    }
}
