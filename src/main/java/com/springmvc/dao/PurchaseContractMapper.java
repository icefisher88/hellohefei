package com.springmvc.dao;

import com.springmvc.entity.PurchaseContract;

import java.util.List;

public interface PurchaseContractMapper {
    int deleteByPrimaryKey(Integer contractid);

    int insert(PurchaseContract record);

    int insertSelective(PurchaseContract record);

    PurchaseContract selectByPrimaryKey(Integer contractid);

    int updateByPrimaryKeySelective(PurchaseContract record);

    int updateByPrimaryKey(PurchaseContract record);

    //获取所有采购合同
    List<PurchaseContract> getAllPurchaseContract();
    //根据推送状态查询合同
    public List<PurchaseContract> getAllPurchaseContractByType(int queryType);
}