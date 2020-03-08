package com.home.bootShiro.shiro;

import com.home.bootShiro.dao.*;
import com.home.bootShiro.domain.SysPermissionDO;
import com.home.bootShiro.domain.SysRole;
import com.home.bootShiro.domain.SysUserDO;
import com.home.bootShiro.manager.SysUserManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Security;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author guxc
 * @date 2020/3/1
 */
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserManager userManager;
    @Autowired
    private SysRoleDao roleDao;
    @Autowired
    private SysPermissionDao permissionDao;

    // 权限认证
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUserDO userDO = (SysUserDO) SecurityUtils.getSubject().getPrincipal();
        String username=userDO.getUserName();
        log.info("==========用户名"+username+"获取权限===");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        List<SysRole> roles = roleDao.getByUsername(username);
        Set<String> roleNames = roles.stream().map(SysRole::getName).collect(Collectors.toSet());
        authorizationInfo.setRoles(roleNames);
        List<SysPermissionDO> permissionDOS = permissionDao.getByUsername(username);
        Set<String> permmissionName = permissionDOS.stream().map(SysPermissionDO::getName).collect(Collectors.toSet());
        authorizationInfo.setStringPermissions(permmissionName);
        return authorizationInfo;
    }

    // 登录认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        log.info("----------用户" + username + "---认证");
        SysUserDO userDO = userManager.getByName(username);
        if (userDO == null) {
            throw new UnknownAccountException("用户名或密码错误！");
        }
        if (!password.equals(userDO.getPassword())) {
            throw new IncorrectCredentialsException("用户名或密码错误！");
        }
        if (userDO.getStatus().equals("0")) {
            throw new LockedAccountException("账号已被锁定,请联系管理员！");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userDO, password, getName());
        return info;
    }
}
