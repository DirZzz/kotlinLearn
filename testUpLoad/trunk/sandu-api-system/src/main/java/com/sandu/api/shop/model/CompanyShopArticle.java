package com.sandu.api.shop.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 店铺博文对应实体
 * @author WangHaiLin
 * @date 2018/8/9  10:04
 */
@Data
public class CompanyShopArticle implements Serializable {
    /**主键ID**/
    private Long id;
    /**店铺ID**/
    private Long shopId;
    /**博文标题**/
    private String title;
    /**发布状态（1是；0否）**/
    private Integer releaseStatus;
    /**图片Ids**/
    private String picIds;
    /**内容**/
    private String content;
    /**系统编码**/
    private String sysCode;
    /**创建者**/
    private String creator;
    /**创建时间**/
    private Date gmtCreate;
    /**更新者**/
    private String modifier;
    /**更新时间**/
    private Date gmtModified;
    /**是否删除**/
    private Integer isDeleted;
    /**备注**/
    private String remark;
    /**浏览次数**/
    private Integer browseCount;
    /**发布时间**/
    private Date releaseTime;
}
