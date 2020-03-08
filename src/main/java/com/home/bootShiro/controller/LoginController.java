package com.home.bootShiro.controller;

import com.home.bootShiro.domain.SysUserDO;
import com.home.bootShiro.response.ResponseBO;
import com.home.bootShiro.util.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author guxc
 * @date 2020/3/1
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseBO login(String username, String password,boolean rememberMe) {
        password = MD5Utils.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password,rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return ResponseBO.ok();
        } catch (UnknownAccountException e) {
            return ResponseBO.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return ResponseBO.error(e.getMessage());
        } catch (LockedAccountException e) {
            return ResponseBO.error(e.getMessage());
        } catch (AuthenticationException e) {
            return ResponseBO.error("认证失败！");
        }
    }

    @RequestMapping("/")
    public String redirectIndex() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        SysUserDO user = (SysUserDO) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/403")
    public String forbid() {
        return "403";
    }
}
