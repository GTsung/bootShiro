package com.home.bootShiro.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author guxc
 * @date 2020/3/1
 */
@Data
@Table(name="sys_user_role")
public class SysUserRole {
    @Id
    private Long id;
    private Long uId;
    private Long rId;
}
