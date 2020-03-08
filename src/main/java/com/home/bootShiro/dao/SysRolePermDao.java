package com.home.bootShiro.dao;

import com.home.bootShiro.domain.SysPermissionDO;
import com.home.bootShiro.domain.SysRolePerm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

@Mapper
public interface SysRolePermDao extends ConditionMapper<SysRolePerm>, MySqlMapper<SysRolePerm> {


}
