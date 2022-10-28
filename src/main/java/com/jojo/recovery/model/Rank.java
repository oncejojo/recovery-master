package com.jojo.recovery.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Rank extends RankFill{
    private Integer id;

    private Integer userId;

    private BigDecimal weight;

    private Integer number;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;

}