package com.home.bootShiro.domain;

import com.home.bootShiro.base.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author guxc
 * @date 2020/3/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sys_user")
public class SysUserDO extends BaseDO<SysUserDO> implements Serializable{

    private Long id;

    private String userName;

    private String password;

    private String status;
}
