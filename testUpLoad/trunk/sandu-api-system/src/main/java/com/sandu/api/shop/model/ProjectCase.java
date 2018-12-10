package com.sandu.api.shop.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * 工程案例--实体类
 *
 * @auth xiaoxc
 * @data 2018-06-19
 */
@Data
public class ProjectCase implements Serializable {

    /** 主键ID **/
    private Integer id;
    /**  店铺Id  **/
    private Integer shopId;
    /**  案例标题  **/
    private String caseTitle;
    /**  发布状态(1是0否)  **/
    private Integer releaseStatus;
    /**  图片Id  **/
   /* private BigInteger picId;*/
    /**  文本内容  **/
    private String content;
    /**  文本内容  **/
    private BigInteger fileId;
    /**  系统编码  **/
    private String sysCode;
    /**  创建者  **/
    private String creator;
    /**  创建时间  **/
    private Date gmtCreate;
    /**  修改人  **/
    private String modifier;
    /**  修改时间  **/
    private Date gmtModified;
    /**  是否删除  **/
    private Integer isDeleted;
    /**  备注  **/
    private String remark;
    /** 富文本图片ids **/
    private String picIds;
}
