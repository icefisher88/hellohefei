package com.springmvc.dao;

import com.springmvc.entity.Uploadfile;

import java.util.List;

public interface UploadfileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Uploadfile record);

    int insertSelective(Uploadfile record);

    Uploadfile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Uploadfile record);

    int updateByPrimaryKey(Uploadfile record);

    List<Uploadfile> getAllUploadFiles();
}