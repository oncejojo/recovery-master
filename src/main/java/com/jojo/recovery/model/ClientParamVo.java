package com.jojo.recovery.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 丁许
 * @date 2019-01-25 9:48
 */
@Data
public class ClientParamVo implements Serializable {

	private Integer userId;

	private String message;
}
