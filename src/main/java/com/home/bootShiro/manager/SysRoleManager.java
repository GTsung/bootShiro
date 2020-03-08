package com.home.bootShiro.manager;

import com.home.bootShiro.base.BaseManager;
import com.home.bootShiro.builder.ConditionBuilder;
import com.home.bootShiro.dao.SysRoleDao;
import com.home.bootShiro.domain.SysRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author guxc
 * @date 2020/3/1
 */
@Slf4j
@Component
public class SysRoleManager extends BaseManager<SysRole, ConditionBuilder> {

    @Autowired
    private SysRoleDao roleDao;
}
