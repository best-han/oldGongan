package com.windaka.suizhi.user.dao;

import com.windaka.suizhi.api.user.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @description: 部门管理Dao
 * @author: hjt
 * @create: 2018-12-06 10:30
 * @version: 1.0.0
 **/
@Mapper
public interface DeptDao {
    /**
    * @Author: hjt
    * @Date: 2018/12/6
    * @Description: 删除部门信息，伪删除
    */
    @Update("UPDATE ht_dept SET del_flag = 0 WHERE dept_id = #{deptId,jdbcType=VARCHAR}" +
            " and area_id =  #{areaId,jdbcType=VARCHAR}")
    int delete(@Param("deptId") String deptId, @Param("areaId") String areaId);
    /**
    * @Author: hjt
    * @Date: 2018/12/6
    * @Description: 部门新增
    */
    int save(Dept record);
    /**
    * @Author: hjt
    * @Date: 2018/12/6
    * @Description: 根据部门ID查询部门信息
    */
    Dept queryById(@Param("deptId") String deptId, @Param("areaId") String areaId);
    /**
    * @Author: hjt
    * @Date: 2018/12/6
    * @Description: 修改部门信息
    */
    int update(Dept record);
    /**
    * @Author: hjt
    * @Date: 2018/12/6
    * @Description: 根据条件查询列表条数
    */
    int queryCount(Map<String, Object> param);
    /**
    * @Author: hjt
    * @Date: 2018/12/6
    * @Description: 查询当前区域下的部门列表
    */
    List<Map<String,Object>> queryList(Map<String, Object> param);

}
