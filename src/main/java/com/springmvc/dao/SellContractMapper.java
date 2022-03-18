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
    //获取所有合同
    List<SellContract> getAllSellContract();
    //获取所有未推送合同
    List<SellContract> getAllUnuploadSellContract();
    //获取所有已推送合同
    List<SellContract> getAllUploadedSellContract();
    //获取所有推送失败合同
    List<SellContract> getAllUploadFailedSellContract();
    //合同推送后更新推送状态
    int updateSCUploadFlagByPrimaryKey(SellContract record);
}