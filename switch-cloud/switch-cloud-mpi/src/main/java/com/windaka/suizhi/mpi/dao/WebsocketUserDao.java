package com.windaka.suizhi.mpi.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WebsocketUserDao {
    String queryIsSuperByUserId(String userId);
}
