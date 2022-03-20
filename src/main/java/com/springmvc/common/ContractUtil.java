package com.springmvc.common;

import com.springmvc.entity.PurchaseContract;
import com.springmvc.entity.SellContract;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContractUtil {
    static Logger logger = Logger.getLogger(ContractUtil.class.getName ()) ;
    private static FormulaEvaluator evaluator;
    public static List<SellContract> importSellContractFromExcel(File excelFile,String contractType) throws InvalidFormatException, IOException {
            List<SellContract> transSellContracts=new ArrayList<>();
            OPCPackage tarPackage=OPCPackage.open(excelFile, PackageAccess.READ);
            XSSFWorkbook xb= null;
            xb = new XSSFWorkbook(tarPackage);
            XSSFSheet xs=null;
            //解决从模板中获取Sheet0不正确的问题（隐藏sheet，先用Sheet名称匹配)
            if(contractType.equals(ContractEnum.JPHT.type))
                xs=xb.getSheet("军品合同数据");
            else if(contractType.equals(ContractEnum.MPHT.type))
                xs=xb.getSheet("民品合同数据");
            else if(contractType.equals(ContractEnum.GJHCJ.type))
                xs=xb.getSheet("国际化成交合同");
            else if(contractType.equals(ContractEnum.GJHSX.type))
                xs=xb.getSheet("国际化生效合同");
            else if(contractType.equals(ContractEnum.KJCX.type))
                xs=xb.getSheet("科技创新合同");
            else if(contractType.equals(ContractEnum.QTYW.type))
                xs=xb.getSheet("其他业务合同");
            if(xs==null)
            xs=xb.getSheetAt(0);
           int startIndex=2;
            int lastIndex=xs.getPhysicalNumberOfRows();
            logger.info("the open excel last rownum is:"+lastIndex);
            for(int i=startIndex;i<lastIndex;i++){
                XSSFRow curRow=xs.getRow(i);
                if(curRow==null||ContractUtil.getCellValueByCell(curRow.getCell(0))==null){
                    logger.info("the last hanld Row num is:"+(i-1));
                    break;//
                }
                SellContract finalContract = getSingleSellContract(curRow, contractType);
                transSellContracts.add(finalContract);
            }
        return transSellContracts;
    }

    public static List<PurchaseContract> importPurchaseContractFromExcel(File excelFile) throws InvalidFormatException, IOException {
        List<PurchaseContract> transPurchaseContracts=new ArrayList<>();
        OPCPackage tarPackage=OPCPackage.open(excelFile, PackageAccess.READ);
        XSSFWorkbook xb= null;
        XSSFSheet xs=null;
        xb = new XSSFWorkbook(tarPackage);
        if(xb.getSheet("采购合同信息")!=null)
            xs=xb.getSheet("采购合同信息");
        else
            xs=xb.getSheetAt(0);
        int startIndex=2;
        int lastIndex=xs.getPhysicalNumberOfRows();
        for(int i=startIndex;i<lastIndex;i++) {
            XSSFRow curRow = xs.getRow(i);
            if(curRow==null||ContractUtil.getCellValueByCell(curRow.getCell(0))==null){
                logger.info("the last hanld Row num is:"+(i-1));
                break;//
            }
            PurchaseContract finalContract = getSinglePurchaseContract(curRow);
            transPurchaseContracts.add(finalContract);
        }
        return transPurchaseContracts;
    }

    static PurchaseContract getSinglePurchaseContract(XSSFRow curRow){
        PurchaseContract cght=new PurchaseContract();
        String contractName= (String)getCellValueByCell(curRow.getCell(0));//合同名称
        String contractCode=(String)getCellValueByCell(curRow.getCell(1));//合同编号
        String contractStatus=(String)getCellValueByCell(curRow.getCell(2));//合同状态
        String contractSecretLev=(String)getCellValueByCell(curRow.getCell(3));//合同密级
        String purchaserName=(String)getCellValueByCell(curRow.getCell(4));//采购方
        String purchaserCode=(String)getCellValueByCell(curRow.getCell(5));//采购人代码
        Date signTime=null;//签订时间
        if(getCellValueByCell(curRow.getCell(6))!=null)
        {
            signTime=curRow.getCell(6).getDateCellValue();
        }
        Date effectiveDate=null;//生效时间
        if(getCellValueByCell(curRow.getCell(7))!=null)
        {
            effectiveDate=curRow.getCell(7).getDateCellValue();
        }
        String pricingManner=(String)getCellValueByCell(curRow.getCell(8));//计价方式
        BigDecimal contractAmount=new BigDecimal(0);//合同金额
        if(getCellValueByCell(curRow.getCell(9))!=null)
        {
            contractAmount=new BigDecimal(curRow.getCell(9).getNumericCellValue());
        }
        String currency=(String)getCellValueByCell(curRow.getCell(10));//币种
        BigDecimal exchangeRate=null;//汇率
        if(getCellValueByCell(curRow.getCell(11))!=null)
        {
            exchangeRate=new BigDecimal(curRow.getCell(11).getNumericCellValue());
        }
        BigDecimal taxRate=null;//税率
        if(getCellValueByCell(curRow.getCell(12))!=null)
        {
            taxRate=new BigDecimal(curRow.getCell(12).getNumericCellValue());
        }
        String payWay=(String)getCellValueByCell(curRow.getCell(13));//付款方式
        String capitalSource=(String)getCellValueByCell(curRow.getCell(14));//资金来源
        String frameworkAgreement=(String)getCellValueByCell(curRow.getCell(15));//框架协议编号
        String tender=(String)getCellValueByCell(curRow.getCell(16));//是否招标
        String blockNumber=(String)getCellValueByCell(curRow.getCell(17));//采购包和标段编号
        String blockName=(String)getCellValueByCell(curRow.getCell(18));//采购包和标段名称
        String note=(String)getCellValueByCell(curRow.getCell(19));//备注
        String supplierName=(String)getCellValueByCell(curRow.getCell(20));//卖方名称
        String supplierCode=(String)getCellValueByCell(curRow.getCell(21));//卖方代码
        String country=(String)getCellValueByCell(curRow.getCell(22));//卖方国别
        String supplierClassify=(String)getCellValueByCell(curRow.getCell(23));//卖方性质
        String isQualifiedSupplier=(String)getCellValueByCell(curRow.getCell(24));//供应商类型
        String isInUnitCompany=(String)getCellValueByCell(curRow.getCell(25));//是否集团内单位
        String purchaseOrgForm=(String)getCellValueByCell(curRow.getCell(26));//采购组织形式
        String purchaseWay=(String)getCellValueByCell(curRow.getCell(27));//采购方式
        String budgetTypes=(String)getCellValueByCell(curRow.getCell(28));//预算类型
        BigDecimal investmentBudget=null;//采购包或投标预算
        if(getCellValueByCell(curRow.getCell(29))!=null)
        {
            investmentBudget=new BigDecimal(curRow.getCell(29).getNumericCellValue());
        }
        String evaluationMethod=(String)getCellValueByCell(curRow.getCell(30));//评标评价方法
        String technicalWeight=(String)getCellValueByCell(curRow.getCell(31));//技术权重
        String businessWeight=(String)getCellValueByCell(curRow.getCell(32));//商务权重
        Date invitationDate=null;//发标日期
        if(getCellValueByCell(curRow.getCell(33))!=null)
        {
            invitationDate=curRow.getCell(33).getDateCellValue();
        }
        Date openingDate=null;//开标日期
        if((String)getCellValueByCell(curRow.getCell(34))!=null)
        {
            openingDate=curRow.getCell(34).getDateCellValue();
        }
        Date startTime=new Date();//开始时间
        if((String)getCellValueByCell(curRow.getCell(35))!=null)
        {
            startTime=curRow.getCell(35).getDateCellValue();
        }
        Date endTime=new Date();//结束时间
        if((String)getCellValueByCell(curRow.getCell(36))!=null)
        {
            endTime=curRow.getCell(36).getDateCellValue();
        }
        String fulfillmentPlace=(String)getCellValueByCell(curRow.getCell(37));//履约地点
        String performanceGuarantee=(String)getCellValueByCell(curRow.getCell(38));//履约担保
        String contKeepStatus=(String)getCellValueByCell(curRow.getCell(39));//履约状态
        String performanceStage=(String)getCellValueByCell(curRow.getCell(40));//履约阶段
        String instructions=(String)getCellValueByCell(curRow.getCell(41));//履约阶段说明
        String supplierEvaluation=(String)getCellValueByCell(curRow.getCell(42));//履约评价
        String whetherToChange=(String)getCellValueByCell(curRow.getCell(43));//是否有变更
        String changeOfSubsidiary=(String)getCellValueByCell(curRow.getCell(44));//变更明细
        String procurementPlatform=(String)getCellValueByCell(curRow.getCell(45));//电子招标平台
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
        //个性化设置
        cght.setCompanyCode("01-0126-01-0126-C3940");//
        cght.setCreateBy("CETCBW");
        cght.setTenantCode("BWGS");
        cght.setUploadFlag(0);
        return handleTranslateContract(cght);
    }

    /**
     * 获取处理后的单个销售合同
     * @param cRow
     * @param contractType
     * @return
     */
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
        contractSecretLev= (String)getCellValueByCell(cRow.getCell(2));
        contractOwner=(String)getCellValueByCell(cRow.getCell(3));
        ownerUnifyCode=(String)getCellValueByCell(cRow.getCell(4));
        ownerClassify=(String)getCellValueByCell(cRow.getCell(5));
        contractPartyB=(String)getCellValueByCell(cRow.getCell(6));
        partyBUnifyCode=(String)getCellValueByCell(cRow.getCell(7));
        if(getCellValueByCell(cRow.getCell(8))!=null)
        contractAmount=new BigDecimal(cRow.getCell(8).getNumericCellValue());
            currency=(String)getCellValueByCell(cRow.getCell(9));
        if(getCellValueByCell(cRow.getCell(10))!=null)
        {
            exchangeRate=new BigDecimal(cRow.getCell(10).getNumericCellValue());
        }
        if(getCellValueByCell(cRow.getCell(11))!=null)
        {
            signTime=cRow.getCell(11).getDateCellValue();
        }
        if(getCellValueByCell(cRow.getCell(12))!=null)
        {
            startTime=cRow.getCell(12).getDateCellValue();
        }
        if(getCellValueByCell(cRow.getCell(13))!=null)
        {
            endTime=cRow.getCell(13).getDateCellValue();
        }
            fourPlate=(String)getCellValueByCell(cRow.getCell(14));
            coreBusinessClass=(String)getCellValueByCell(cRow.getCell(15));
            projectCode=(String)getCellValueByCell(cRow.getCell(16));
            groupProjectId=(String)getCellValueByCell(cRow.getCell(17));
            contractRemark=(String)getCellValueByCell(cRow.getCell(18));
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

        sellContract.setCompanyCode("01-0126-01-0126-C3940");//
        sellContract.setCreateBy("CETCBW");
        sellContract.setTenantCode("BWGS");
        sellContract.setUploadFlag(0);

        //区分合同类型，进行处理
        if(contractType.equals(ContractEnum.JPHT.type))
        {
                sellContract.setArmyClass2((String)getCellValueByCell(cRow.getCell(19)));
                sellContract.setChargeArmyOffice((String)getCellValueByCell(cRow.getCell(20)));
                sellContract.setProductType((String)getCellValueByCell(cRow.getCell(21)));
                sellContract.setMakeType((String)getCellValueByCell(cRow.getCell(22)));
                sellContract.setProfessionalField((String)getCellValueByCell(cRow.getCell(23)));
                sellContract.setIsGreatNewCont((String)getCellValueByCell(cRow.getCell(24)));
                sellContract.setIsGreatProject((String)getCellValueByCell(cRow.getCell(25)));
        }
        else if(contractType.equals(ContractEnum.GJHCJ.type)||contractType.equals(ContractEnum.GJHSX.type))
        {
                sellContract.setChannel((String)getCellValueByCell(cRow.getCell(19)));
                sellContract.setImpExpTrade((String)getCellValueByCell(cRow.getCell(20)));
                sellContract.setClassifyOne((String)getCellValueByCell(cRow.getCell(21)));
                sellContract.setClassifyTwo((String)getCellValueByCell(cRow.getCell(22)));
                sellContract.setClassifyThree((String)getCellValueByCell(cRow.getCell(23)));
                sellContract.setNationality((String)getCellValueByCell(cRow.getCell(24)));
        }
        else if(contractType.equals(ContractEnum.KJCX.type))
        {
                sellContract.setTechEarnings((String)getCellValueByCell(cRow.getCell(19)));
                sellContract.setSciTechClassify((String)getCellValueByCell(cRow.getCell(20)));
                sellContract.setTechnicalB((String)getCellValueByCell(cRow.getCell(21)));
                sellContract.setTechnicalB2((String)getCellValueByCell(cRow.getCell(22)));
        }
        else if(contractType.equals(ContractEnum.MPHT.type))
        {
                sellContract.setIndustryClassify((String)getCellValueByCell(cRow.getCell(19)));
        }
        return handleTranslateContract(sellContract);
    }


    /**
     * 处理销售合同Value-->Code
     * @param originContract
     * @return
     */
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
            String newOwnerClassify =oldOwnerClassify;
            if(originContract.getTheirBusiness().equals(ContractEnum.JPHT.type))
                newOwnerClassify=DicUtil.getItemCode(MenuCodeEnum.JFFLJP1, oldOwnerClassify, "");
            else
                newOwnerClassify=DicUtil.getItemCode(MenuCodeEnum.JFFLJP2, oldOwnerClassify, "");

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
            String newIndustryClassify = DicUtil.getItemCode(MenuCodeEnum.HYFL, oldIndustryClassify, ">");
            newContract.setIndustryClassify(newIndustryClassify);
            logger.info("IndustryClassify Change, From: "+oldIndustryClassify+" To: "+newIndustryClassify);
        }
        return newContract;
    }

    /**
     * 处理单条采购合同Value-->Code
     * @param originContract
     * @return
     */
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

    /**
     * 处理EXCEL单元格
     * @param cell
     * @return
     */
    private static Object getCellValueByCell(Cell cell) {

        //判断是否为null或空串
        if (cell==null || cell.toString().trim().equals("")) {
            return null;
        }
        String cellValue = "";
        int cellType=cell.getCellType();
        if(cellType==Cell.CELL_TYPE_FORMULA){ //表达式类型
            cellType=evaluator.evaluate(cell).getCellType();
        }
        switch (cellType) {
            case Cell.CELL_TYPE_STRING: //字符串类型
                cellValue= cell.getStringCellValue().trim();
                cellValue= StringUtils.isEmpty(cellValue) ? "" : cellValue;
                break;
            case Cell.CELL_TYPE_BOOLEAN:  //布尔类型
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_NUMERIC: //数值类型
                if (HSSFDateUtil.isCellDateFormatted(cell)) {  //判断日期类型
                    return cell.getDateCellValue();
                } else {  //否，数值类型
                    return cell.getNumericCellValue();
                }
            default: //其它类型，返回null
                cellValue = null;
                break;
        }
        return cellValue;
    }
}
