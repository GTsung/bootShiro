package com.home.bootShiro.domain;

import com.home.bootShiro.base.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

/**
 * @author guxc
 * @date 2020/3/1
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SysPermissionDO extends BaseDO<SysPermissionDO> {

    @Id
    private Long id;

    private String url;

    private String name;

}
