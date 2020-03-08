package com.home.bootShiro.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author guxc
 * @date 2020/3/1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseDO<T> {

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 删除标记
     */
    private Integer delFlag;

    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 创建用户
     */
    protected String createUser;

    /**
     * 更新时间
     */
    protected Date updateTime;

    /**
     * 更新用户
     */
    protected String updateUser;
}
