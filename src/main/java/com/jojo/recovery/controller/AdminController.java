package com.jojo.recovery.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jojo.recovery.common.domain.AjaxResult;
import com.jojo.recovery.common.enums.JsonResultEnum;
import com.jojo.recovery.common.exception.JsonException;
import com.jojo.recovery.model.*;
import com.jojo.recovery.service.*;
import com.jojo.recovery.utils.ImageFileUtil;
import com.jojo.recovery.utils.MD5Util;
import com.jojo.recovery.utils.PageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author JoJo
 * @Date 2022/4/29 15:25
 * @Description
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Resource
    SysUserService sysUserService;
    @GetMapping
    public String index() {
        return "login";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }


    /**
     * @author JoJo
     * @Date 2022/3/22 9:51
     **/
    @PostMapping("/login")
    @ResponseBody
    public AjaxResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) throws JsonException {
        String remember = request.getParameter("remember");
        SysUser user = sysUserService.loginAction(username, password);
        if (user == null) {
            return AjaxResult.error(JsonResultEnum.ADMIN_USER_NULL);

        } else if (!MD5Util.string2MD5(password).equals(user.getPassword())) {
            return AjaxResult.error(JsonResultEnum.ADMIN_PASS_ERROR);
        }
        HttpSession session = request.getSession();
        if (remember != null) {
            Cookie cookieUser = new Cookie("username", username);
            Cookie cookiePass = new Cookie("password", password);
            cookiePass.setMaxAge(60 * 60 * 24);
            cookieUser.setMaxAge(60 * 60 * 24);
            response.addCookie(cookieUser);
            response.addCookie(cookiePass);
            session.setAttribute("rememberU", username);
            session.setAttribute("rememberP", password);
        } else {
            session.removeAttribute("rememberU");
            session.removeAttribute("rememberP");
        }

        session.setAttribute("adminId", user.getId());
        session.setAttribute("adminName", user.getUsername());
        session.setAttribute("admin", user);
        return AjaxResult.success();
    }

    @RequestMapping(value = "/logout")
    public void logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        // 移除session
        session.removeAttribute("admin");
        // 设置cookie值和时间为空
        Cookie cookie = new Cookie("S_L_ID", "");
        cookie.setValue(null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        try {
            // 跳转到登录页面
            response.sendRedirect("/admin/login");
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @GetMapping("/sysUser/edit")
    public String editSysUser() {
        return "index/sysUser";
    }

//    @PostMapping("/sysUser/update")
//    @ResponseBody
//    public AjaxResult editSysUser(SysUser sysUser, HttpServletRequest request) throws JsonException {
//        if (sysUser.getUsername() == null || sysUser.getUsername() == "") {
//            return AjaxResult.error(JsonResultEnum.PARAM_NULL);
//        }
//        if (sysUser.getPassword() == null || sysUser.getPassword() == "") {
//            return AjaxResult.error(JsonResultEnum.PARAM_NULL);
//        }
//        sysUser.setPassword(MD5Util.string2MD5(sysUser.getPassword()));
//        sysUserService.update(sysUser);
//        HttpSession session = request.getSession();
//        session.setAttribute("adminId", sysUser.getId());
//        session.setAttribute("adminName", sysUser.getUsername());
//        session.setAttribute("admin", sysUser);
//        return AjaxResult.success();
//    }

    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult upload(MultipartFile file) throws JsonException, IOException {
        String url = ImageFileUtil.uploadFile(file);
        return AjaxResult.successData(url);
    }


}
