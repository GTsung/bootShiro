package com.home.bootShiro.manager;

import com.home.bootShiro.base.BaseManager;
import com.home.bootShiro.builder.ConditionBuilder;
import com.home.bootShiro.dao.SysUserDao;
import com.home.bootShiro.domain.SysUserDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

/**
 * @author guxc
 * @date 2020/3/1
 */
@Component
@Slf4j
public class SysUserManager extends BaseManager<SysUserDO, ConditionBuilder> {

    @Autowired
    private SysUserDao userDao;

    public SysUserDO getByName(String username) {
        Example example = new Example(SysUserDO.class);
        example.and().andEqualTo("userName",username);
        return userDao.selectOneByExample(example);
    }
}
