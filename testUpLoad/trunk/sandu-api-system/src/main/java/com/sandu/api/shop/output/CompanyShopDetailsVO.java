package com.sandu.api.shop.output;

import com.sandu.api.pic.model.po.ResPicPO;
import com.sandu.api.shop.model.CompanyShop;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 店铺详情界面
 *
 * @auth xiaoxc
 * @data 2018-06-06
 */
@Data
public class CompanyShopDetailsVO implements Serializable {

    @ApiModelProperty(value="店铺Id")
    private Integer shopId;

    @ApiModelProperty(value="店铺编码")
    private String shopCode;

    @ApiModelProperty(value="店铺名称")
    private String shopName;

    @ApiModelProperty(value = "联系人姓名")
    private String contactName;

    @ApiModelProperty(value = "联系人电话")
    private String contactPhone;

    @ApiModelProperty(value = "详细地址")
    private String shopAddress;

    @ApiModelProperty(value = "店铺类型值(品牌馆or家居建材or设计师or设计公司or装修公司or工长)")
    private Integer businessTypeValue;

    @ApiModelProperty(value = "店铺类型名称(品牌馆or家居建材or设计师or设计公司or装修公司or工长)")
    private String businessTypeName;

    @ApiModelProperty(value = "分类信息值(产品分类or擅长风格or施工类型)")
    private String categoryIds;

    @ApiModelProperty(value = "分类信息名称(产品分类or擅长风格or施工类型)")
    private String categoryName;

    @ApiModelProperty(value = "区域省编码")
    private String provinceCode;

    @ApiModelProperty(value = "区域市编码")
    private String cityCode;

    @ApiModelProperty(value = "区域区编码")
    private String areaCode;

    @ApiModelProperty(value = "区域街道编码")
    private String streetCode;

    @ApiModelProperty(value = "区域省名称")
    private String provinceName;

    @ApiModelProperty(value = "区域市名称")
    private String cityName;

    @ApiModelProperty(value = "区域区名称")
    private String areaName;

    @ApiModelProperty(value = "区域街道名称")
    private String streetName;

    @ApiModelProperty(value = "logoId")
    private Integer logoId;

    @ApiModelProperty(value = "logo地址")
    private String logoUrl;

    @ApiModelProperty(value = "店铺封面资源Ids")
    private String coverResIds;

    @ApiModelProperty(value = "店铺封面资源集合地址")
    private List<ResPicPO> coverList;

    @ApiModelProperty(value = "店铺封面资源类型1:图片列表,2:全景图,3:视频")
    private Integer coverResType;

    @ApiModelProperty(value = "店铺介绍")
    private String shopIntroduced;

    @ApiModelProperty(value = "店铺介绍文本Id")
    private Long introducedFileId;

    @ApiModelProperty(value = "店铺介绍文本路径")
    private String introducedFilePath;

    @ApiModelProperty(value = "发布平台value")
    private String releasePlatformValues;

    @ApiModelProperty(value = "发布平台名称")
    private String releasePlatformName;

    //add by wangHaiLin
    @ApiModelProperty(value = "经度")
    private Double longitude;
    @ApiModelProperty(value = "纬度")
    private Double latitude;
    @ApiModelProperty(value = "设计最低价格")
    private Double designFeeStarting;
    @ApiModelProperty(value = "设计最高价格")
    private Double designFeeEnding;

    /**装修报价区间**/
    private Double decorationPriceStart;
    /**装修报价区间**/
    private Double decorationPriceEnd;
    /**装修方式:0.半包,1.全包**/
    private Integer decorationType;
    /**从业年限**/
    private Integer workingYears;

    /**
     * 转换vo对象
     *
     * @param shop
     * @return
     */
    public CompanyShopDetailsVO getShopDetailsVOFromShop(CompanyShop shop) {
        CompanyShopDetailsVO shopVO = new CompanyShopDetailsVO();
        if (shop != null) {
            shopVO.setShopId(shop.getId());
            shopVO.setShopCode(shop.getShopCode());
            shopVO.setShopName(shop.getShopName());
            shopVO.setBusinessTypeValue(shop.getBusinessType());
            shopVO.setContactName(shop.getContactName());
            shopVO.setContactPhone(shop.getContactPhone());
            shopVO.setProvinceCode(shop.getProvinceCode());
            shopVO.setCityCode(shop.getCityCode());
            shopVO.setAreaCode(shop.getAreaCode());
            shopVO.setStreetCode(shop.getStreetCode());
            shopVO.setCategoryIds(shop.getCategoryIds());
            shopVO.setShopAddress(shop.getShopAddress());
            shopVO.setShopIntroduced(shop.getShopIntroduced());
            shopVO.setReleasePlatformValues(shop.getReleasePlatformValues());
            shopVO.setLogoId(shop.getLogoPicId());
            shopVO.setCoverResIds(shop.getCoverResIds());
            shopVO.setCoverResType(shop.getCoverResType());
            shopVO.setIntroducedFileId(shop.getIntroducedFileId());
            shopVO.setLatitude(shop.getLatitude());
            shopVO.setLongitude(shop.getLongitude());
            shopVO.setDecorationPriceStart(shop.getDecorationPriceStart());
            shopVO.setDecorationPriceEnd(shop.getDecorationPriceEnd());
            shopVO.setDecorationType(shop.getDecorationType());
            shopVO.setWorkingYears(shop.getWorkingYears());
            if (shop.getBusinessType()==4||shop.getBusinessType()==3){
                shopVO.setDesignFeeStarting(shop.getDesignFeeStarting());
                shopVO.setDesignFeeEnding(shop.getDesignFeeEnding());
            }
        }
        return shopVO;
    }

}
