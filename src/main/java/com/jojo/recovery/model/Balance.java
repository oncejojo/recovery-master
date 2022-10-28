package com.jojo.recovery.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Balance {
    private Integer id;

    private Integer userId;

    private String username;

    private String phone;

    private BigDecimal money;

    private Integer status;

    private Integer type;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;
}