package com.home.bootShiro.domain;

import com.home.bootShiro.base.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author guxc
 * @date 2020/3/1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="sys_role")
public class SysRole extends BaseDO<SysRole> {

    @Id
    private Long id;
    private String name;
    private String remark;
}
