package com.jojo.recovery.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author JoJo
 * @Date 2022/6/21 13:53
 * @Description
 * @Version 1.0
 */
@Data
public class BinFull {
    private Integer id;

    private Integer boxId;

    private Integer no;

    private Integer fullValue;

    private Integer tempValue;

    private Integer smokeFlay;

    private BigDecimal weight;

    private String devCode;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private Integer deleted;

    private String bucketName;
}
