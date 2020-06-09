package com.windaka.suizhi.mpi.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface RoomDao {

	/**
	 * 查询房屋性质及其数量 hjt
	 * @return
	 */
	List<Map<String,Object>> queryRoomPropertyNum(Map<String, Object> params);

	/**
	 *  根据小区编码查询房屋记录总数据，用于分组查询的限制条数limit
	 * @param params
	 * @return
	 */
	int queryRoomRecordNumByXqCodes(Map<String, Object> params);

	/**
	 * 查询一段时间内新增的出租房屋数量 hjt
	 * @param params
	 * @return
	 */
	int queryRoomRentAddNum(Map<String, Object> params);

	/**
	 * 根据room的主键manageId查其详细名字（76号楼1单元101）  hjt
	 * @param manageId
	 * @return
	 */
	String 	queryRoomNameByManageId(String manageId);

	/**
	 * 根据ownerId和xqCode查roomIds(可能一人在该小区有多套房产)  hjt
	 * @param
	 * @return
	 */
	List<Map<String,Object>> queryXqRoomIdByOwnerId(Map<String, Object> params);


}
