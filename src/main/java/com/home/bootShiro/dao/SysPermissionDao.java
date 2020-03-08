package com.home.bootShiro.dao;

import com.home.bootShiro.base.BaseDao;
import com.home.bootShiro.domain.SysPermissionDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysPermissionDao extends BaseDao<SysPermissionDO> {

    @Select("select p.* from sys_permission p " +
            "left join sys_role_permission rp on rp.pid=p.id\n" +
            "left join sys_role r on rp.rid=r.id\n" +
            "left join sys_user_role ur on ur.rid=r.id\n" +
            "left join sys_user u on u.id=ur.uid\n" +
            "where u.user_name=#{username}")
    List<SysPermissionDO> getByUsername(String username);
}
