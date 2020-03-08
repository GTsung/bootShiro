package com.home.bootShiro.dao;

import com.home.bootShiro.domain.SysRole;
import com.home.bootShiro.domain.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author guxc
 * @date 2020/3/1
 */
@Mapper
public interface SysUserRoleDao extends ConditionMapper<SysUserRole>, MySqlMapper<SysUserRole> {


}
