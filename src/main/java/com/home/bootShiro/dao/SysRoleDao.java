package com.home.bootShiro.dao;

import com.home.bootShiro.base.BaseDao;
import com.home.bootShiro.domain.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysRoleDao extends BaseDao<SysRole> {

    @Select("select r.* from sys_role r " +
            "left join sys_user_role ur on r.id=ur.rid\n" +
            "left join sys_user u on u.id=ur.uid\n" +
            "where u.user_name=#{username}")
    List<SysRole> getByUsername(String username);
}
