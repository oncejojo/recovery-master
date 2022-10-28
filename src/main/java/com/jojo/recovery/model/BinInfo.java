package com.jojo.recovery.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author JoJo
 * @Date 2022/5/30 10:43
 * @Description
 * @Version 1.0
 */
@Data
public class BinInfo extends ClientParamVo{
    private Integer no;

    private String scan;

    private BigDecimal weight;

    private String unit;
}
