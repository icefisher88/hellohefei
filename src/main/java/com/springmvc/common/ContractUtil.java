package com.springmvc.common;

import com.springmvc.entity.PurchaseContract;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class ContractUtil {
    static Logger logger = Logger.getLogger(ContractUtil.class.getName ()) ;
    public static PurchaseContract handleTranslateContract(PurchaseContract originContract){
        PurchaseContract newContract=originContract;

        //合同状态转换
        if(StringUtils.isNotEmpty(originContract.getContractStatus())) {
            String newStatus = DicUtil.getItemCode(MenuCodeEnum.HTZT_CG, originContract.getContractStatus(), "");
            newContract.setContractStatus(newStatus);
            logger.info("ContractStatus Change, From: "+originContract.getContractStatus()+" To: "+newStatus);
        }

        //合同密级转换
        if(StringUtils.isNotEmpty(originContract.getContractSecretLev())) {
            String newSecretLev = DicUtil.getItemCode(MenuCodeEnum.HTMJ, originContract.getContractSecretLev(), "");
            newContract.setContractSecretLev(newSecretLev);
            logger.info("ContractSecretLev Change, From: "+originContract.getContractSecretLev()+" To: "+newSecretLev);
        }

        //币种转换
        if(StringUtils.isNotEmpty(originContract.getCurrency())) {
            String newCurrency = DicUtil.getItemCode(MenuCodeEnum.BZ, originContract.getCurrency(), "");
            newContract.setCurrency(newCurrency);
            logger.info("Currency Change, From: "+originContract.getCurrency()+" To: "+newCurrency);
        }

        //计价方式转换
        if(StringUtils.isNotEmpty(originContract.getPricingManner())) {
            String newPricingManner = DicUtil.getItemCode(MenuCodeEnum.JJFS, originContract.getPricingManner(), "");
            newContract.setPricingManner(newPricingManner);
            logger.info("PricingManner Change, From: "+originContract.getPricingManner()+" To: "+newPricingManner);
        }
        //付款方式转换
        if(StringUtils.isNotEmpty(originContract.getPayWay())){
            String newPayWay = DicUtil.getItemCode(MenuCodeEnum.FKFS, originContract.getPayWay(), "");
            newContract.setPayWay(newPayWay);
            logger.info("PayWay Change, From: "+originContract.getPayWay()+" To: "+newPayWay);
        }
        //资金来源转换
        if(StringUtils.isNotEmpty(originContract.getCapitalSource())){
            String newCapitalSource = DicUtil.getItemCode(MenuCodeEnum.ZJLY, originContract.getCapitalSource(), "");
            newContract.setCapitalSource(newCapitalSource);
            logger.info("CapitalSource Change, From: "+originContract.getCapitalSource()+" To: "+newCapitalSource);
        }
        //是否招标转换
        if(StringUtils.isNotEmpty(originContract.getTender())){
            String newTender = DicUtil.getItemCode(MenuCodeEnum.SF, originContract.getTender(), "");
            newContract.setTender(newTender);
            logger.info("Tender Change, From: "+originContract.getTender()+" To: "+newTender);
        }
        //卖方国别转换
        if(StringUtils.isNotEmpty(originContract.getCountry())){
            String newCountry= DicUtil.getItemCode(MenuCodeEnum.GB, originContract.getCountry(), ">");
            newContract.setCountry(newCountry);
            logger.info("Country Change, From: "+originContract.getCountry()+" To: "+newCountry);
        }
        //卖方性质转换
        if(StringUtils.isNotEmpty(originContract.getSupplierClassify())){
            String newClass= DicUtil.getItemCode(MenuCodeEnum.GFXZ, originContract.getSupplierClassify(), "");
            newContract.setSupplierClassify(newClass);
            logger.info("SupplierClassify Change, From: "+originContract.getSupplierClassify()+" To: "+newClass);
        }
        //供应商类型转换
        if(StringUtils.isNotEmpty(originContract.getIsQualifiedSupplier())){
            String newQualifiedSupplier= DicUtil.getItemCode(MenuCodeEnum.GFLX, originContract.getIsQualifiedSupplier(), "");
            newContract.setIsQualifiedSupplier(newQualifiedSupplier);
            logger.info("QualifiedSupplier Change, From: "+originContract.getIsQualifiedSupplier()+" To: "+newQualifiedSupplier);
        }
        //是否集团内单位转换
        if(StringUtils.isNotEmpty(originContract.getIsinUnitCompany())){
            String newIsinUnitCompany= DicUtil.getItemCode(MenuCodeEnum.SF, originContract.getIsinUnitCompany(), "");
            newContract.setIsinUnitCompany(newIsinUnitCompany);
            logger.info("IsinUnitCompany Change, From: "+originContract.getIsinUnitCompany()+" To: "+newIsinUnitCompany);
        }

        //评标_评价方法转换
        if(StringUtils.isNotEmpty(originContract.getEvaluationMethod())){
            String newEvaluationMethod= DicUtil.getItemCode(MenuCodeEnum.PB_PJFF, originContract.getEvaluationMethod(), "");
            newContract.setEvaluationMethod(newEvaluationMethod);
            logger.info("EvaluationMethod Change, From: "+originContract.getEvaluationMethod()+" To: "+newEvaluationMethod);
        }

        //采购组织形式转换
        if(StringUtils.isNotEmpty(originContract.getPurchaseOrgForm())){
            String newPurchaseOrgForm= DicUtil.getItemCode(MenuCodeEnum.CGZZLX, originContract.getPurchaseOrgForm(), "");
            newContract.setPurchaseOrgForm(newPurchaseOrgForm);
            logger.info("PurchaseOrgForm Change, From: "+originContract.getPurchaseOrgForm()+" To: "+newPurchaseOrgForm);
        }

        //采购方式转换
        if(StringUtils.isNotEmpty(originContract.getPurchaseWay())){
            String newPurchaseWay= DicUtil.getItemCode(MenuCodeEnum.CGFS, originContract.getPurchaseWay(), "");
            newContract.setPurchaseWay(newPurchaseWay);
            logger.info("PurchaseWay Change, From: "+originContract.getPurchaseWay()+" To: "+newPurchaseWay);
        }
        //预算类型转换
        if(StringUtils.isNotEmpty(originContract.getBudgetTypes())){
            String newBudgetTypes= DicUtil.getItemCode(MenuCodeEnum.YSLX, originContract.getBudgetTypes(), "");
            newContract.setBudgetTypes(newBudgetTypes);
            logger.info("BudgetType Change, From: "+originContract.getBudgetTypes()+" To: "+newBudgetTypes);
        }
        //履约地点转换
        if(StringUtils.isNotEmpty(originContract.getFulfillmentPlace())){
            String newFulfillmentPlace= DicUtil.getItemCode(MenuCodeEnum.LYDD, originContract.getFulfillmentPlace(), "");
            newContract.setFulfillmentPlace(newFulfillmentPlace);
            logger.info("FulfillmentPlace Change, From: "+originContract.getFulfillmentPlace()+" To: "+newFulfillmentPlace);
        }
        //履约担保转换
        if(StringUtils.isNotEmpty(originContract.getPerformanceGuarantee())){
            String newGuarantee= DicUtil.getItemCode(MenuCodeEnum.LYDB, originContract.getPerformanceGuarantee(), "");
            newContract.setPerformanceGuarantee(newGuarantee);
            logger.info("PerformanceGuarantee Change, From: "+originContract.getPerformanceGuarantee()+" To: "+newGuarantee);
        }
        //履约状态转换
        if(StringUtils.isNotEmpty(originContract.getContKeepStatus())){
            String newKeepStatus= DicUtil.getItemCode(MenuCodeEnum.LVZT, originContract.getContKeepStatus(), "");
            newContract.setContKeepStatus(newKeepStatus);
            logger.info("ContKeepStatus Change, From: "+originContract.getContKeepStatus()+" To: "+newKeepStatus);
        }
        //履约状态转换
        if(StringUtils.isNotEmpty(originContract.getPerformanceStage())){
            String newPerformanceStage= DicUtil.getItemCode(MenuCodeEnum.LYJD, originContract.getPerformanceStage(), "");
            newContract.setPerformanceStage(newPerformanceStage);
            logger.info("PerformanceStage Change, From: "+originContract.getPerformanceStage()+" To: "+newPerformanceStage);
        }
        //履约评价转换
        if(StringUtils.isNotEmpty(originContract.getSupplierEvaluation())){
            String newEvaluation= DicUtil.getItemCode(MenuCodeEnum.LVPJ, originContract.getSupplierEvaluation(), "");
            newContract.setSupplierEvaluation(newEvaluation);
            logger.info("SupplierEvaluation Change, From: "+originContract.getSupplierEvaluation()+" To: "+newEvaluation);
        }
        //是否有变更转换
        if(StringUtils.isNotEmpty(originContract.getWhetherToChange())){
            String newWhetherToChange= DicUtil.getItemCode(MenuCodeEnum.SF, originContract.getWhetherToChange(), "");
            newContract.setWhetherToChange(newWhetherToChange);
            logger.info("WhetherToChange Change, From: "+originContract.getWhetherToChange()+" To: "+newWhetherToChange);
        }

        return newContract;
    }
}
