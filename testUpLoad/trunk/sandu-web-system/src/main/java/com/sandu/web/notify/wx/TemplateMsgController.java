package com.sandu.web.notify.wx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sandu.api.notify.wx.TemplateMsgService;
import com.sandu.common.LoginContext;
import com.sandu.commons.LoginUser;
import com.sandu.commons.ResponseEnvelope;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "templateMsg", description = "发送微信模板消息")
@RestController
@RequestMapping("/v1/notify/wx")
public class TemplateMsgController {
	
	private Logger logger = LoggerFactory.getLogger(TemplateMsgController.class);
	
    @Autowired
    private TemplateMsgService templateMsgService;
    
    
    @ApiOperation(value = "保存用户渲染时的formId", response = ResponseEnvelope.class)
    @PostMapping("/saveUserRenderFormId")
    public ResponseEnvelope saveUserRenderFormId(Long designPlanId,String formId,String forwardPage,Integer renderType,Integer renderLevel) {
    	logger.info("保存用户渲染时的formId:designPlanId,"+designPlanId+"formId:"+formId+",forwardPage:"+forwardPage+",renderType:"+renderType+",renderLevel:"+renderLevel);
    	//验证输入参数 
    	if(designPlanId==null) {
			return new ResponseEnvelope<>(false, "方案id不能为空!");
		}
		if(renderType==null) {
			return new ResponseEnvelope<>(false, "渲染类型不能为空!");
		}else if(renderType!=0 && renderType!=1) {
			return new ResponseEnvelope<>(false, "渲染类型不正确(0:装进我家,1:替换渲染)!");
		}
		if(renderLevel==null) {
			return new ResponseEnvelope<>(false, "渲染级别不能为空!");
		}else if(renderLevel!=1 && renderLevel!=4 && renderLevel!=6 && renderLevel!=8) {
			return new ResponseEnvelope<>(false, "渲染级别不正确(1:普通照片级 4:720单点 6.视频 8:720多点)!");
		}
		

		
		//保存formId到redis
		LoginUser loginUser = LoginContext.getLoginUser(LoginUser.class);
		try {
			boolean flag = templateMsgService.saveUserRenderFormId(loginUser.getId().longValue(), designPlanId, formId, forwardPage,renderType,renderLevel);
			if(flag) {
				return new ResponseEnvelope<>(true, "保存成功!");
			}
			return new ResponseEnvelope<>(true, "保存失败!");
		}catch(Exception ex) {
			return new ResponseEnvelope<>(true, "保存失败!");
		}
		
    	
    }
    
    
    
	@ApiOperation(value = "发送渲染模板消息", response = ResponseEnvelope.class)
    @PostMapping("/sendRenderTemplateMsg")
    public ResponseEnvelope sendRenderTemplateMsg(Long userId,Long designPlanId,String designPlanName,String renderResult,Integer renderType,Integer renderLevel) {
		
		try {
			logger.info("发送渲染模板消息:userId,"+userId+"designPlanId:"+designPlanId+",designPlanName:"+designPlanName+",renderResult:"+renderResult+",renderType:"+renderType+",renderLevel:"+renderLevel);
			//验证输入参数 
			if(userId==null) {
				return new ResponseEnvelope<>(false, "用户id不能为空!");
			}
			if(designPlanId==null) {
				return new ResponseEnvelope<>(false, "方案id不能为空!");
			}
			if(renderType==null) {
				return new ResponseEnvelope<>(false, "渲染类型不能为空!");
			}else if(renderType!=0 && renderType!=1) {
				return new ResponseEnvelope<>(false, "渲染类型不正确(0:装进我家,1:替换渲染)!");
			}
			if(renderLevel==null) {
				return new ResponseEnvelope<>(false, "渲染级别不能为空!");
			}else if(renderLevel!=1 && renderLevel!=4 && renderLevel!=6 && renderLevel!=8) {
				return new ResponseEnvelope<>(false, "渲染级别不正确(1:普通照片级 4:720单点 6.视频 8:720多点)!");
			}
			//发送模板消息
			templateMsgService.sendRenderTemplateMsg(userId, designPlanId, designPlanName, renderResult,renderType,renderLevel);
			return new ResponseEnvelope<>(true, "发送成功!");
		}catch(Exception ex) {
			logger.error("发送渲染消息异常:",ex);
			return new ResponseEnvelope<>(false, "发送失败!");
		}         
    }
}
