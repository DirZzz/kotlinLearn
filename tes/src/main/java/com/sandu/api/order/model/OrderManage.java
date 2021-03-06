package com.sandu.api.order.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderManage implements Serializable {

    private Integer id;

    private Integer intermediaryId;

    private String intermediaryName;

    private String tradePartner;

    private Double tradePrice;

    private Date tradeDate;

    private String creator;

    private Date gmtCreate;

    private String modifier;

    private Date gmtModified;

    private Integer isDeleted;

}