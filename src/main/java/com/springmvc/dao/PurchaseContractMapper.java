package com.springmvc.dao;

import com.springmvc.entity.PurchaseContract;

public interface PurchaseContractMapper {
    int deleteByPrimaryKey(Integer contractid);

    int insert(PurchaseContract record);

    int insertSelective(PurchaseContract record);

    PurchaseContract selectByPrimaryKey(Integer contractid);

    int updateByPrimaryKeySelective(PurchaseContract record);

    int updateByPrimaryKey(PurchaseContract record);
}