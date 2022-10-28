package com.jojo.recovery.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jojo.recovery.common.domain.AjaxResult;
import com.jojo.recovery.common.enums.JsonResultEnum;
import com.jojo.recovery.common.exception.JsonException;
import com.jojo.recovery.controller.socket.Server;
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
    @Resource
    OrderService orderService;
    @Resource
    UserService userService;
    @Resource
    BannerService bannerService;
    @Resource
    BalanceService balanceService;
    @Resource
    ArticleService articleService;
    @Resource
    BoxService boxService;
    @Resource
    BucketService bucketService;
    @Resource
    BinFullService binFullService;

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

    @GetMapping("/home")
    public String toIndex(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer adminId = (Integer) session.getAttribute("adminId");
        User user = new User();
        Order order = new Order();
        if (adminId != null && adminId != 1) {
            order.setAgentId(adminId);
        }
        List<User> users = userService.getList(user);
        List<Order> orders = orderService.getList(order);
        request.setAttribute("userNum", users.size());
        request.setAttribute("orderNum", orders.size());
        return "index/home";
    }

    /**
     * @return java.lang.String
     * @desc banner列表
     * @Date 2022/3/22 15:38
     * @Param [page, request]
     **/
    @GetMapping("/banner")
    public String toBanner(Page page, HttpServletRequest request) throws JsonException {
        PageHelper.startPage(page.getPage(), page.getRows());
        List<Banner> banners = bannerService.getList();
        PageInfo<Banner> pageInfo = new PageInfo<>(banners);
        request.setAttribute("pageInfo", pageInfo);
        return "index/banner";
    }

    /**
     * @return java.lang.String
     * @desc 用户列表
     * @Date 2022/3/25 16:00
     * @Param [page, request, user]
     **/
    @GetMapping("/user")
    public String toUser(Page page, HttpServletRequest request, User user) throws JsonException {
        PageHelper.startPage(page.getPage(), page.getRows());
        List<User> users = userService.getList(user);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        request.setAttribute("pageInfo", pageInfo);
        PageUtil.searchParams(request);
        return "user/user";
    }

    /**
     * @return java.lang.String
     * @desc 订单列表
     * @Date 2022/3/25 16:00
     * @Param [page, request, order]
     **/
    @GetMapping("/order")
    public String toOrder(Page page, HttpServletRequest request, Order order) throws JsonException, ParseException {
        HttpSession session = request.getSession();
        Integer adminId = (Integer) session.getAttribute("adminId");
        if (adminId != null && adminId != 1) {
            order.setAgentId(adminId);
        }
        if (!order.getTimeRange().equals("")) {
            String[] time = order.getTimeRange().split("-");

            Date startTime = new SimpleDateFormat("yyyy/MM/dd").parse(time[0].trim());
            Date endTime = new SimpleDateFormat("yyyy/MM/dd").parse(time[1].trim());
            order.setStartTime(startTime);
            order.setEndTime(endTime);

        }
        Integer countNum = orderService.getList(order).size();

        PageHelper.startPage(page.getPage(), page.getRows());
        List<Order> orders = orderService.getList(order);
        for (Order record : orders) {
            User user = userService.getRecord(record.getUserId());
            if (user != null) {
                record.setUsername(user.getUsername());
            }
            Box box = boxService.getRecord(record.getBoxId());
            if (box != null) {
                record.setBoxName(box.getName());
            }
            Bucket bucket = bucketService.getInfo(record.getBucketId());
            if (bucket != null) {
                record.setBucketName(bucket.getName().substring(0, 2));
            }

            SysUser sysUser = sysUserService.getRecord(record.getAgentId());
            if (sysUser != null && sysUser.getRole() != 1){
                record.setAgentName(sysUser.getRemarks());
            }else {
                record.setAgentName("平台");
            }

        }
        PageInfo<Order> pageInfo = new PageInfo<>(orders);
        request.setAttribute("pageInfo", pageInfo);
        request.setAttribute("countNum", countNum);
        PageUtil.searchParams(request);
        return "index/order";
    }

    /**
     * @return java.lang.String
     * @desc 提现列表
     * @Date 2022/7/21 17:04
     * @Param [page, request, balance]
     **/
    @GetMapping("/balance")
    public String toBalance(Page page, HttpServletRequest request, Balance balance) throws JsonException {

        PageHelper.startPage(page.getPage(), page.getRows());
        List<Balance> balances = balanceService.getList(balance);

        PageInfo<Balance> pageInfo = new PageInfo<>(balances);
        request.setAttribute("pageInfo", pageInfo);
        PageUtil.searchParams(request);
        return "user/balance";
    }

    /**
     * @return java.lang.String
     * @desc 箱体列表
     * @Date 2022/7/21 17:04
     * @Param [page, request, box]
     **/
    @GetMapping("/box")
    public String toBox(Page page, HttpServletRequest request, Box box) throws JsonException {
        HttpSession session = request.getSession();
        Integer boxNo = (Integer) session.getAttribute("adminId");
        if (boxNo != null && boxNo != 1) {
            box.setBoxNo(boxNo);
        }
        PageHelper.startPage(page.getPage(), page.getRows());
        List<Box> boxes = boxService.getList(box);
        for (Box target : boxes) {
            if (Server.existSocketClientMap.containsKey(target.getCode())) {
                target.setConn(1);
            } else {
                target.setConn(0);
            }
            SysUser sysUser = sysUserService.getRecord(target.getBoxNo());
            if (sysUser != null && sysUser.getRole() != 1){
                target.setAgentName(sysUser.getRemarks());
            }else {
                target.setAgentName("平台");
            }

            List<BinFull> binFulls = binFullService.getFullList(target.getId());
            if (binFulls.size() != 0) {
                target.setStatus(1);
            }else {
                target.setStatus(0);
            }

        }

        PageInfo<Box> pageInfo = new PageInfo<>(boxes);
        request.setAttribute("pageInfo", pageInfo);
        PageUtil.searchParams(request);
        return "index/box";
    }

    /**
     * @return java.lang.String
     * @desc 回收箱状态列表
     * @Date 2022/7/21 17:04
     * @Param [page, request, id]
     **/
    @GetMapping("/full")
    public String toBinFull(Page page, HttpServletRequest request, Integer id) throws JsonException {
        BinFull binFull = new BinFull();
        binFull.setBoxId(id);
        PageHelper.startPage(page.getPage(), page.getRows());
        List<BinFull> binFulls = binFullService.getList(binFull);
        for (BinFull record : binFulls) {
            Bucket bucket = bucketService.getInfo(record.getNo());
            if (bucket != null) {
                record.setBucketName(bucket.getName());
            }
        }
        PageInfo<BinFull> pageInfo = new PageInfo<>(binFulls);
        request.setAttribute("pageInfo", pageInfo);
        PageUtil.searchParams(request);
        return "index/binFull";
    }

    /**
     * @return java.lang.String
     * @desc 价格设置
     * @Date 2022/7/21 17:05
     * @Param [page, request]
     **/
    @GetMapping("/bucket")
    public String toBucket(Page page, HttpServletRequest request) throws JsonException {
        PageHelper.startPage(page.getPage(), page.getRows());
        List<Bucket> buckets = bucketService.getList();

        PageInfo<Bucket> pageInfo = new PageInfo<>(buckets);
        request.setAttribute("pageInfo", pageInfo);
        PageUtil.searchParams(request);
        return "index/bucket";
    }

    /**
     * @return java.lang.String
     * @desc 服务列表
     * @Date 2022/3/25 15:59
     * @Param [page, request, goods]
     **/
    @GetMapping("/article")
    public String toGoods(Page page, HttpServletRequest request, Article article) throws JsonException {
        PageHelper.startPage(page.getPage(), page.getRows());
        List<Article> articles = articleService.getList(article);

        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        request.setAttribute("pageInfo", pageInfo);
        PageUtil.searchParams(request);
        return "article/article";
    }

    @GetMapping("/article/new")
    public String newBuild(int id, HttpServletRequest request) throws JsonException {
        Article article = articleService.getRecord(id);
        if (article != null) {
            request.setAttribute("target", article);
        }

        return "article/articleAdd";
    }
}
