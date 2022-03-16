package com.springmvc.dao;

import com.springmvc.entity.ContractDic;

import java.util.List;

public interface ContractDicMapper {
    int deleteByPrimaryKey(String id);

    int insert(ContractDic record);

    int insertSelective(ContractDic record);

    ContractDic selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContractDic record);

    int updateByPrimaryKey(ContractDic record);

    List<ContractDic> selectAllDics();
}