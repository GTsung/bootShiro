package com.home.bootShiro.util;

import com.home.bootShiro.domain.SysUserDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import java.util.Optional;

@Slf4j
public class UserUtil {

    public static final String DEFAULT_USER = "系统-System";

    public static SysUserDO currentUser() {
        SysUserDO currentUser = null;
        try {
            currentUser = (SysUserDO) SecurityUtils.getSubject().getSession().getAttribute("user");
        } catch (Exception e) {
            log.info("获取cas session 中的用户信息失败，使用默认用户");
        }
        return Optional.ofNullable(currentUser)
                .orElseGet(() -> SysUserDO.builder().id(0L).userName("System").build());
    }

    public static String getUserName() {
        return currentUser().getUserName();
    }


}
