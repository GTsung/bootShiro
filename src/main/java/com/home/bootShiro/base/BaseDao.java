package com.home.bootShiro.base;

import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface BaseDao<T> extends Mapper<T>, ConditionMapper<T>, MySqlMapper<T> {
}
