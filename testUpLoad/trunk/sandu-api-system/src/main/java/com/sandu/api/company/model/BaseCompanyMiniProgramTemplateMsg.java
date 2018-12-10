package com.sandu.api.company.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class BaseCompanyMiniProgramTemplateMsg implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final Integer TEMPLATE_TYPE_RENDER=1;
	
	private Long id;
	/**
	 * 企业微信appid
	 */
	private String appid; 
	/**
	 * 模板类型(1:渲染模板消息,2:...)
	 */
	private Integer templateType;
	
	/**
	 * 微信小程序后台配置的模板id
	 */
	private String templateId;
	
	private String creator;
	
	private Date gmtCreate;
	
	private String modifier;
	
	private Date gmtModified;
	
	private Integer isDeleted;

}
