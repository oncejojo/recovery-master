package com.jojo.recovery.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author JoJo     
 * @Date 2022/8/8 15:49
 **/
@Data
public class Order extends OrderFill{
    private Integer id;

    private String orderNo;

    private Integer boxId;

    private Integer bucketId;

    private Integer userId;

    private Integer agentId;

    private BigDecimal money;

    private BigDecimal weight;

    private Integer status;

    private Integer type;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;

}