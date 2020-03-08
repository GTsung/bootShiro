package com.home.bootShiro.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @date 2018/10/15
 */
@AllArgsConstructor
public enum DelFlagEnum implements BaseEnum<Integer> {
    PERSISTENT(0, "未删除"),
    DELETED(1, "已删除"),
    PRE_DELETED(2, "预删除"),
    ;

    @Getter
    private Integer code;

    @Getter
    private String desc;


}
