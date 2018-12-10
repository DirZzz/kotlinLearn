package com.sandu.api.miniprogram.output;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Yuxc
 * @date 2018/9/4
 */
@Data
public class ProprietorInfoVO implements Serializable {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "类型（0：0元设计，1：装修报价）")
    private Byte type;

    @ApiModelProperty(value = "名称")
    private String userName;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "省编码")
    private String provinceCode;

    @ApiModelProperty(value = "市编码")
    private String cityCode;

    @ApiModelProperty(value = "小区名称")
    private String areaName;

    @ApiModelProperty(value = "房屋面积(平方米)")
    private String houseAcreage;

    @ApiModelProperty(value = "来源类型（0：PC，1：小程序）")
    private Integer sourceType;

    @ApiModelProperty(value = "户型")
    private String houseType;

    @ApiModelProperty(value = "处理结果（0：未处理，1：已处理）")
    private Integer process;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreated;

    @ApiModelProperty(value = "修改人")
    private String modifier;

    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;

    @ApiModelProperty(value = "是否删除（0：未删除，1：已删除）")
    private Integer isDeleted;

    @ApiModelProperty(value = "是否初始化数据（0：原始数据，1：新数据）")
    private Integer isInit;

    @ApiModelProperty(value = "业务类型(0-业主信息;1-设计师信息;2-设计装修公司信息)")
    private Integer businessType;

    @ApiModelProperty(value = "公司类型(0-设计公司;1-装修公司)")
    private Integer companyType;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

}