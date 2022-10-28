package com.jojo.recovery.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jojo.recovery.common.domain.AjaxResult;
import com.jojo.recovery.common.enums.JsonResultEnum;
import com.jojo.recovery.common.exception.JsonException;
import com.jojo.recovery.model.SysUser;
import com.jojo.recovery.model.Page;
import com.jojo.recovery.service.SysUserService;
import com.jojo.recovery.utils.MD5Util;
import com.jojo.recovery.utils.PageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.http.HTTPException;
import java.util.List;
import java.util.Objects;

/**
 * @Author JoJo
 * @Data 2021/10/28$ 13:35$
 * @Description
 * @Param $
 * @Return $
 */
@Controller
@RequestMapping("/admin")
public class SysUserController {
    @Resource
    SysUserService sysUserService;



    @PostMapping("/sysUser/insert")
    @ResponseBody
    public AjaxResult addRecord(SysUser sysUser) throws JsonException {
        SysUser sysUser1 = sysUserService.getRecordByName(sysUser.getUsername());
        if (sysUser1 != null) {
            return AjaxResult.error(JsonResultEnum.USER_EXISTS);
        }
        if (sysUser.getUsername() == "" || sysUser.getPassword() == ""){
            return AjaxResult.error(JsonResultEnum.PARAM_NULL);
        }

        sysUser.setPassword(MD5Util.string2MD5(sysUser.getPassword()));
        sysUserService.insert(sysUser);
        return AjaxResult.success();
    }

    @PostMapping("/sysUser/update")
    @ResponseBody
    public AjaxResult editRecord(SysUser sysUser) throws JsonException {
        SysUser sysUser1 = sysUserService.getRecordByName(sysUser.getUsername());
        if (sysUser1 != null && !Objects.equals(sysUser1.getId(), sysUser.getId())) {
            return AjaxResult.error(JsonResultEnum.USER_EXISTS);
        }
        if (sysUser.getUsername() == null || sysUser.getPassword() == "") {
            return AjaxResult.error(JsonResultEnum.PARAM_NULL);
        }

        SysUser oldUser = sysUserService.getRecord(sysUser.getId());
        if (!sysUser.getPassword().equals(oldUser.getPassword())){
            sysUser.setPassword(MD5Util.string2MD5(sysUser.getPassword()));
        }

        sysUserService.update(sysUser);
        return AjaxResult.success();
    }

    @GetMapping("/sysUser")
    public String getSySList(Page page, HttpServletRequest request, SysUser sysUser) throws HTTPException {
        PageHelper.startPage(page.getPage(), page.getRows());
        List<SysUser> sysUsers = sysUserService.getList(sysUser);
        PageInfo<SysUser> pageInfo = new PageInfo<>(sysUsers);
        request.setAttribute("pageInfo", pageInfo);
        PageUtil.searchParams(request);
        return "index/sysUser";
    }

    @GetMapping("/sysUser/info")
    @ResponseBody
    public AjaxResult getTarget(Integer id) throws JsonException {
        SysUser sysUser =  sysUserService.getRecord(id);

        return AjaxResult.successData(sysUser);
    }

    @PostMapping("/sysUser/delete")
    @ResponseBody
    public AjaxResult deleteTarget(int id) throws JsonException {
        SysUser sysUser = sysUserService.getRecord(id);
        if (sysUser == null) {
            return AjaxResult.error(JsonResultEnum.TARGET_NULL);
        }
        sysUserService.delete(id);
        return AjaxResult.success();
    }
}
