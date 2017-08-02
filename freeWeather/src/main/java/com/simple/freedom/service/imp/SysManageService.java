package com.simple.freedom.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simple.freedom.dao.ISysManageMapper;
import com.simple.freedom.service.ISysManageService;

@Service
public class SysManageService implements ISysManageService{
	
	@Autowired
	ISysManageMapper sysManageMapper;

	@Override
	public List<String> selectCity(String proName) {
		return sysManageMapper.selectCity(proName);
	}
}
