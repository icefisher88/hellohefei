package com.springmvc.dao;

import com.springmvc.entity.ContractUser;

import java.util.List;

public interface ContractUserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(ContractUser record);

    int insertSelective(ContractUser record);

    ContractUser selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(ContractUser record);

    int updateByPrimaryKey(ContractUser record);

    List<ContractUser> getAllContractUserInfo();
}