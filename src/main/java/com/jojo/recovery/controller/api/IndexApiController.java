package com.jojo.recovery.controller.api;

import cn.hutool.json.JSONObject;
import com.jojo.recovery.common.domain.AjaxResult;
import com.jojo.recovery.common.enums.JsonResultEnum;
import com.jojo.recovery.model.*;
import com.jojo.recovery.service.*;
import com.jojo.recovery.utils.OrderNumUtil;
import com.jojo.recovery.utils.WeChatUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author JoJo
 * @Date 2022/5/17 10:06
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping("/api")
public class IndexApiController {
    @Resource
    UserService userService;
    @Resource
    BannerService bannerService;
    @Resource
    ArticleService articleService;
    @Resource
    OrderService orderService;
    @Resource
    BalanceService balanceService;
    @Resource
    RankService rankService;
    @Resource
    BoxService boxService;
    @Resource
    BucketService bucketService;

    /**
     * @return com.jojo.recovery.common.domain.AjaxResult
     * @desc bannerList
     * @Date 2022/5/17 10:10
     * @Param []
     **/
    @GetMapping("/banner")
    public AjaxResult bannerList() {
        List<Banner> banners = bannerService.getList();
        return AjaxResult.successData(banners);
    }

    /**
     * @return com.jojo.recovery.common.domain.AjaxResult
     * @desc 文章列表
     * @Date 2022/5/19 13:48
     * @Param []
     **/
    @GetMapping("/article")
    public AjaxResult articleList() {
        List<Article> articleList = articleService.getList(new Article());
        return AjaxResult.successData(articleList);
    }

    /**
     * @return com.jojo.recovery.common.domain.AjaxResult
     * @desc 文章详情
     * @Date 2022/5/23 11:25
     * @Param [id]
     **/
    @GetMapping("/article/info")
    public AjaxResult articleInfo(int id) {
        Article article = articleService.getRecord(id);
        return AjaxResult.successData(article);
    }


    /**
     * @return com.jojo.recovery.common.domain.AjaxResult
     * @desc 个人订单
     * @Date 2022/5/17 10:28
     * @Param [order]
     **/
    @GetMapping("/order/list")
    public AjaxResult orderList(Order order) {
        User user = userService.getRecord(order.getUserId());
        if (user == null) {
            return AjaxResult.error(JsonResultEnum.USER_NULL);
        }
        List<Order> orders = orderService.getList(order);
        return AjaxResult.successData(orders);
    }

    /**
     * @return com.jojo.recovery.common.domain.AjaxResult
     * @desc 添加订单
     * @Date 2022/5/23 16:28
     * @Param [order]
     **/
    @PostMapping("/order/insert")
    public AjaxResult newOrder(Order order) {
        User user = userService.getRecord(order.getUserId());
        if (user == null) {
            return AjaxResult.error(JsonResultEnum.USER_NULL);
        }
        String orderNo = OrderNumUtil.getInstance().GenerateOrder();
        order.setOrderNo(orderNo);
        Bucket bucket = bucketService.getInfo(order.getBucketId());
        order.setMoney(bucket.getPrice().multiply(order.getWeight()));
        Box box = boxService.getRecordByCode(order.getRemark());
        if (box == null) {
            return AjaxResult.error("箱体不存在");
        }
        order.setAgentId(box.getBoxNo());
        order.setBoxId(box.getId());
        orderService.insert(order);

        Rank rank = rankService.getInfo(order.getUserId());
        if(rank == null) {
            Rank record = new Rank();
            record.setUserId(order.getUserId());
            record.setNumber(1);
            record.setWeight(order.getWeight());
            rankService.insert(record);
        }else {
            rank.setNumber(rank.getNumber()+1);
            rank.setWeight(rank.getWeight().add(order.getWeight()));
            rankService.update(rank);
        }

//        添加订单后增加用户金额
        user.setMoney(user.getMoney().add(order.getMoney()));
        userService.update(user);
        return AjaxResult.success();
    }

    /**
     * @return com.jojo.recovery.common.domain.AjaxResult
     * @desc 提现记录
     * @Date 2022/5/19 14:13
     * @Param [balance]
     **/
    @GetMapping("/balance/list")
    public AjaxResult balanceList(Balance balance) {
        User user = userService.getRecord(balance.getUserId());
        if (user == null) {
            return AjaxResult.error(JsonResultEnum.USER_NULL);
        }
        List<Balance> balances = balanceService.getList(balance);
        return AjaxResult.successData(balances);
    }

    /**
     * @return com.jojo.recovery.common.domain.AjaxResult
     * @desc 用户提现
     * @Date 2022/5/19 14:20
     * @Param [balance]
     **/
    @PostMapping("/balance/insert")
    public AjaxResult insetBalance(Balance balance) {
        User user = userService.getRecord(balance.getUserId());
        if (user == null) {
            return AjaxResult.error(JsonResultEnum.USER_NULL);
        }
        balanceService.insert(balance);
//        提现后减少用户余额
        BigDecimal money = user.getMoney();
        if (money.compareTo(balance.getMoney()) == -1) {
            return AjaxResult.error(JsonResultEnum.BALANCE_INSUFFICIENT);
        }
        user.setMoney(money.subtract(balance.getMoney()));
        userService.update(user);
        return AjaxResult.success();
    }

    /**
     * @return com.jojo.recovery.common.domain.AjaxResult
     * @desc 排行榜
     * @Date 2022/5/19 14:49
     * @Param []
     **/
    @GetMapping("/rank/list")
    public AjaxResult getRankList() {
        List<Rank> rankList = rankService.getList();
        for (Rank rank : rankList) {
            User user = userService.getRecord(rank.getUserId());
            if (user != null) {
                rank.setUsername(user.getUsername());
                rank.setAvatar(user.getAvatar());
            } else {
                rankList.remove(rank);
            }

        }
        return AjaxResult.successData(rankList);
    }

    /**
     * @return com.jojo.recovery.common.domain.AjaxResult
     * @desc 用户排名
     * @Date 2022/5/20 9:26
     * @Param [userId]
     **/
    @GetMapping("/rank/info")
    public AjaxResult getRankInfo(int userId) {
        User user = userService.getRecord(userId);
        if (user == null) {
            return AjaxResult.error(JsonResultEnum.USER_NULL);
        }
        Rank rank = rankService.getInfo(userId);
        if(rank == null) {
            return AjaxResult.error("未投递");
        }
        rank.setUsername(user.getUsername());
        rank.setAvatar(user.getAvatar());
        return AjaxResult.successData(rank);
    }
    @GetMapping("/code")
    public AjaxResult createCode(Integer id) {
        Box box = boxService.getRecord(id);

        JSONObject token = WeChatUtil.getAccessToken();
        String sceneStr = "message=" + box.getName();
        String code = WeChatUtil.getMiniQrcode(sceneStr, token.get("access_token").toString());

        return AjaxResult.success(code);
    }

    @GetMapping("/buckets")
    public AjaxResult getBuckets() {
        List<Bucket> buckets = bucketService.getList();
        return AjaxResult.successData(buckets);
    }
}
