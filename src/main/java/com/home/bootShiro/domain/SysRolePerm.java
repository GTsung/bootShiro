package com.home.bootShiro.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author guxc
 * @date 2020/3/1
 */
@Data
@Table(name="sys_role_permission")
public class SysRolePerm {

    @Id
    private Long id;
    private Long rId;
    private Long pId;
}
