package com.sandu.api.shop.output;

import com.sandu.api.shop.model.CompanyShop;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 店铺管理列表展示Vo界面
 *
 * @auth xiaoxc
 * @data 2018-06-04
 */
@Data
public class CompanyShopVO implements Serializable {

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

    @ApiModelProperty(value = "区域区名称")
    private String areaName;

    @ApiModelProperty(value = "发布平台名称")
    private String releasePlatformName;

    @ApiModelProperty(value = "发布状态 1:发布，0:未发布")
    private Integer displayStatus;

    @ApiModelProperty(value = "是否被拉黑 1:被拉黑，0:未被拉黑")
    private Integer isBlacklist;

    /**
     * 转换vo对象
     *
     * @param shop
     * @return
     */
    public CompanyShopVO getCompanyShopVOFromCompanyShop(CompanyShop shop) {
        CompanyShopVO shopVO = new CompanyShopVO();
        if (shop != null) {
            shopVO.setShopId(shop.getId());
            shopVO.setShopCode(shop.getShopCode());
            shopVO.setShopName(shop.getShopName());
            shopVO.setContactName(shop.getContactName());
            shopVO.setContactPhone(shop.getContactPhone());
            shopVO.setDisplayStatus(shop.getDisplayStatus());
            shopVO.setIsBlacklist(shop.getIsBlacklist());
        }
        return shopVO;
    }

}
