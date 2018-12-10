package com.sandu.service.banner.impl;

import com.google.common.base.Strings;
import com.sandu.api.banner.input.BaseBannerAdAdd;
import com.sandu.api.banner.input.BaseBannerAdUpdate;
import com.sandu.api.banner.input.BaseBannerListQuery;
import com.sandu.api.banner.model.BaseBannerAd;
import com.sandu.api.banner.model.po.BannerPO;
import com.sandu.api.banner.output.BaseBannerVO;
import com.sandu.api.banner.service.BaseBannerAdService;
import com.sandu.commons.LoginUser;
import com.sandu.commons.Utils;
import com.sandu.service.banner.dao.BaseBannerAdMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 基础-广告 Service实现类
 * @author WangHaiLin
 * @date 2018/5/16  20:57
 */
@Service("baseBannerAdService")
public class BaseBannerAdServiceImpl implements BaseBannerAdService {
    private final static Logger logger = LoggerFactory.getLogger(BaseBannerAdServiceImpl.class);

    @Autowired
    private BaseBannerAdMapper baseBannerAdMapper;

    @Override
    public int addBaseBanner(BaseBannerAdAdd bannerAdd, LoginUser loginUser) {
        BaseBannerAd banner=bannerAdd.getBannerFromBannerAdd(bannerAdd);
        //存储系统字段
        this.saveSystemInfo(banner,loginUser);
        int result = baseBannerAdMapper.insertBanner(banner);
        if (result>0){
            return banner.getId();
        }
        return 0;
    }

    @Override
    public int deleteBanner(Integer bannerId,LoginUser loginUser) {
        BaseBannerAd banner =baseBannerAdMapper.getBannerById(bannerId);
        this.saveSystemInfo(banner,loginUser);
        return baseBannerAdMapper.deleteBanner(banner);
    }

    @Override
    public int updateBanner(BaseBannerAdUpdate update,LoginUser loginUser) {
        //获取旧值
        BaseBannerAd bannerAd = baseBannerAdMapper.getBannerById(update.getBannerId());
        //修改属性值
        BaseBannerAd banner = this.getBannerFromBannerUpdate(bannerAd,update);
        //存储修改者和修改时间
        this.saveSystemInfo(banner,loginUser);
        logger.error("修改时入参BaseBannerAd：${}",banner);
        return baseBannerAdMapper.updateBanner(banner);
    }

    //数据转换 BaserBannerUpdate 转换成 BaseBannerAd
    public BaseBannerAd getBannerFromBannerUpdate(BaseBannerAd banner,BaseBannerAdUpdate update){
        if (update.getPositionId()!=null){
            banner.setPositionId(update.getPositionId());
        }if (update.getBannerName()!=null){
            banner.setName(update.getBannerName());
        }if (update.getResBannerPicId()!=null){
            banner.setResBannerPicId(update.getResBannerPicId());
        }if (update.getRefModelId()!=null){
            banner.setRefModelId(update.getRefModelId());
        }if (update.getStatus()!=null){
            banner.setStatus(update.getStatus());
        }if (update.getSequence()!=null){
            banner.setSequence(update.getSequence());
        }
        banner.setUrl(Strings.nullToEmpty(update.getSkipPath()));
        return banner;
    }

    @Override
    public BaseBannerAd getBannerById(Integer bannerId) {
        return baseBannerAdMapper.getBannerById(bannerId);
    }

    @Override
    public List<BaseBannerVO> getBannerList(BannerPO queryList) {
        return baseBannerAdMapper.getBannerList(queryList);
    }

    @Override
    public int getCount(BannerPO bannerPO) {
        return baseBannerAdMapper.getBannerCount(bannerPO);
    }

    @Override
    public List<BaseBannerAd> getBannerByModelPosition(BaseBannerListQuery query) {
        return baseBannerAdMapper.getBannerByModelPosition(query);
    }

    @Override
    public List<BaseBannerAd> getBaseBannerByRefModelId(Integer refModelId) {
        return baseBannerAdMapper.getBannerByRefModelId(refModelId);
    }

    /**
     * 自动存储系统字段
     * @param banner BannerAd实体
     * @param loginUser 当前登录用户
     */
    public void saveSystemInfo(BaseBannerAd banner, LoginUser loginUser) {
        if(banner != null){
            //新增
            if(banner.getId() == null){
                banner.setGmtCreate(new Date());
                if (null!=loginUser.getLoginName()){
                    banner.setCreator(loginUser.getLoginName());
                }
                banner.setIsDeleted(0);
                if(banner.getSysCode()==null || "".equals(banner.getSysCode())){
                    banner.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
                }
            }
            //修改
            banner.setGmtModified(new Date());
            if (null!=loginUser.getLoginName()){
                banner.setModifier(loginUser.getLoginName());
            }
        }
    }
}
