package com.jojo.recovery.model;

import lombok.Data;

import java.util.Date;

/**
 * @author JoJo
 * @Date 2022/7/21 10:02
 **/
@Data
public class Box {
    private Integer id;

    private String name;

    private String code;

    private Integer boxNo;

    private String address;

    private Date createTime;

    private Date updateTime;

    private Integer status;

    private Integer deleted;

    private Integer conn = 0;

    private String agentName;

}