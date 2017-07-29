package com.simple.freedom.dao;

import com.simple.freedom.beans.AreaSizeBean;

public interface IAreaSizeBeansMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AreaSizeBean record);

    int insertSelective(AreaSizeBean record);

    AreaSizeBean selectByPrimaryKey(String areaName);

    int updateByPrimaryKeySelective(AreaSizeBean record);

    int updateByPrimaryKey(AreaSizeBean record);
}