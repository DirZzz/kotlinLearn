package com.sandu.web.miniprogram;

import com.google.common.base.Strings;
import com.sandu.api.miniprogram.input.ProprietorInfoAdd;
import com.sandu.api.miniprogram.input.UnionPlatformUpdate;
import com.sandu.api.miniprogram.model.ProprietorInfo;
import com.sandu.api.miniprogram.output.ProprietorInfoVO;
import com.sandu.api.miniprogram.service.ProprietorInfoService;
import com.sandu.commons.ResponseEnvelope;
import com.sandu.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Yuxc
 * @Description:
 * @Date: Created in 18:07 2018/9/4
 */
@Api(tags = "proprietor", description = "小程序矩阵落地页")
@RestController
@RequestMapping("/v1/proprietor")
public class ProprietorInfoController extends BaseController {

    @Resource
    private ProprietorInfoService proprietorInfoService;

    @ApiOperation(value = "新增业主信息", response = ResponseEnvelope.class)
    @PostMapping
    public ResponseEnvelope addProprietor(@Valid @RequestBody ProprietorInfoAdd proprietorInfoAdd, BindingResult validResult) {
        //1.校验参数
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }
        //检验手机号码唯一性
        ProprietorInfo proprietorInfo = proprietorInfoService.queryProprietorByPhone(proprietorInfoAdd.getMobile(),
                proprietorInfoAdd.getBusinessType(),proprietorInfoAdd.getType()==null?null:proprietorInfoAdd.getType().intValue());
        if (proprietorInfo != null) {
            return new ResponseEnvelope<>(false, "您已成功预约，请勿反复提交，我们会尽快和您联系");
        }
        //进行数据新增
        int result = proprietorInfoService.addProprietor(proprietorInfoAdd);
        if (result > 0) {
            return new ResponseEnvelope<>(true, "创建成功!");
        }
        return new ResponseEnvelope<>(false, "创建失败!");
    }

    @ApiOperation(value = "业主信息列表", response = ProprietorInfoVO.class)
    @GetMapping
    public ResponseEnvelope ProprietorList() {

        List<ProprietorInfo> proprietorInfoList = proprietorInfoService.queryProprietor();
        List<ProprietorInfoVO> results = new ArrayList<>(proprietorInfoList.size());
        //数据转换
        for (int i = 0; i < proprietorInfoList.size(); i++) {

            ProprietorInfoVO vo = new ProprietorInfoVO();
            StringBuilder hideName = new StringBuilder();
            String initName = proprietorInfoList.get(i).getUserName();

            //隐藏名称信息(张*)
            for (int j = 0; j < initName.length(); j++) {
                if (initName.length() == 2) {
                    hideName.append(initName.substring(j, j + 1)).append("*");
                    break;
                }
                if (j != 0 && j != initName.length() - 1) {
                    hideName.append("*");
                } else {
                    hideName.append(initName.substring(j, j + 1));
                }
            }
            vo.setUserName(hideName.toString());

            //隐藏手机信息(138xxxx1234)
            String headPhone = proprietorInfoList.get(i).getMobile().substring(0, 3);
            String footPhone = proprietorInfoList.get(i).getMobile().substring(7, 11);
            vo.setMobile(headPhone + "xxxx" + footPhone);
            results.add(vo);
        }
        if (results.size() > 0 && results != null) {
            return new ResponseEnvelope<ProprietorInfoVO>(true, results);
        }
        return new ResponseEnvelope<ProprietorInfoVO>(false, "没有数据了");
    }

    @ApiOperation(value = "更新平台点击量", response = ResponseEnvelope.class)
    @PostMapping("/platform")
    public ResponseEnvelope updatePlatform(@Valid @RequestBody UnionPlatformUpdate update, BindingResult validResult) {
        //1.校验参数
        if (validResult.hasErrors()) {
            return processValidError(validResult, new ResponseEnvelope());
        }
        if(Strings.isNullOrEmpty(update.getPlatName())) {
            return new ResponseEnvelope<>(false, "平台名称不合法!");
        }
        //进行数据新增
        int result = proprietorInfoService.updatePlatform(update);
        if (result > 0) {
            return new ResponseEnvelope<>(true, "成功!");
        }
        return new ResponseEnvelope<>(false, "失败!");
    }

}
