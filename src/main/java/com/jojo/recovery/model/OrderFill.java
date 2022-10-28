package com.jojo.recovery.model;

import lombok.Data;

import java.util.Date;

/**
 * @Author JoJo
 * @Date 2022/3/25 16:46
 * @Description
 * @Version 1.0
 */
@Data
public class OrderFill {
    private String username;

    private String phone;

    private String address;

    private String goodsName;

    private String goodsImage;

    private String boxName;

    private String timeRange = "";

    private Date startTime;

    private Date endTime;

    private String bucketName;

    private String agentName;

}
