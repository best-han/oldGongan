package com.windaka.suizhi.mpi.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.windaka.suizhi.mpi.dao.MsgSocketIdDao;

@Service
public class AdjustTaskData {
	@Resource
	MsgSocketIdDao msgSocketIdDao;
	
	@Transactional
	public void adjustTaskData(){
		msgSocketIdDao.updateMsgTaskData();
		msgSocketIdDao.updateCarTaskData();
		msgSocketIdDao.updatePersonTaskData();
		
	}
}
