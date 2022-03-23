package com.springmvc.dao;

import com.springmvc.entity.SellContract;

import java.util.List;

public interface SellContractMapper {
    int deleteByPrimaryKey(Integer contractid);

    int insert(SellContract record);

    int insertSelective(SellContract record);

    SellContract selectByPrimaryKey(Integer contractid);

    int updateByPrimaryKeySelective(SellContract record);

    int updateByPrimaryKey(SellContract record);
    //获取所有销售合同
    List<SellContract> getAllSellContract();
    //根据合同类型、推送状态查询合同
    public List<SellContract> getAllSellContractByType(int queryType, String queryContractType);
}