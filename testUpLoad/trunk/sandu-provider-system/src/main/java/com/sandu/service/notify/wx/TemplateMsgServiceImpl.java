package com.sandu.service.notify.wx;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.company.model.BaseCompanyMiniProgramConfig;
import com.sandu.api.company.model.BaseCompanyMiniProgramTemplateMsg;
import com.sandu.api.company.service.BaseCompanyMiniProgramConfigService;
import com.sandu.api.company.service.BaseCompanyMiniProgramTemplateMsgService;
import com.sandu.api.notify.wx.TemplateMsgService;
import com.sandu.api.user.model.SysUser;
import com.sandu.api.user.service.SysUserService;
import com.sandu.commons.gson.GsonUtil;
import com.sandu.commons.http.HttpClientUtil;
import com.sandu.service.redis.RedisService;

@Service("templateMsgService")
public class TemplateMsgServiceImpl  implements TemplateMsgService{

	private Logger logger = LoggerFactory.getLogger(TemplateMsgServiceImpl.class);
	
    @Autowired
    private BaseCompanyMiniProgramConfigService miniProgramConfigService;
    
    @Autowired
    private BaseCompanyMiniProgramTemplateMsgService miniProgramTemplateMsgService;
    
    @Resource
    private RedisService redisService;
    
    @Autowired
    private SysUserService sysUserService;
    
    private static final String REDIS_ACCESS_TOKEN_KEY_PREFIX="template_message:access_token:";
    private static final String REDIS_USER_RENDER_KEY_PREFIX="template_message:user_render_plan:";
  //  private static final String TEMPLATE_ID = "1lb_5X18bdPQisTX3Xf7DcbiNg-ArMOiHMY3KAHgTww"; //微信渲染通知模板id
 //   private static final String TEMPLATE_ID = "8XeBGkp1lwrnTLrB0Y8nVyXK7PLBdx0VUSA_nayj990";
    private static Map<Integer,String> RENDER_TYPE_MAP = new HashMap<Integer,String>();
    static {
    	RENDER_TYPE_MAP.put(0, "normal_render"); //装进我家
    	RENDER_TYPE_MAP.put(1, "replace_render"); //替换渲染
    }
    
    private static Map<Integer,String> RENDER_LEVEL_MAP = new HashMap<Integer,String>();
    static {
    	RENDER_LEVEL_MAP.put(1, "common_render");
    	RENDER_LEVEL_MAP.put(4, "panorama_render");
    	RENDER_LEVEL_MAP.put(6, "video");
    	RENDER_LEVEL_MAP.put(8, "roam720");
    }
    
    
    @Override
    public boolean saveUserRenderFormId(Long userId,Long designPlanId,String formId,String forwardPage,Integer renderType,Integer renderLevel) {
    	SysUser user = sysUserService.get(userId.intValue());
    	if(user!=null) {
    		Map<String,String> userMap = new HashMap<String,String>();
        	userMap.put("appid", user.getAppId());
        	userMap.put("openid", user.getOpenId());
        	userMap.put("page", forwardPage);
        	userMap.put("formId", formId);
        	String key = REDIS_USER_RENDER_KEY_PREFIX+RENDER_TYPE_MAP.get(renderType)+":"+RENDER_LEVEL_MAP.get(renderLevel)+":"+userId.toString()+"-"+designPlanId.toString();
        	redisService.set(key, GsonUtil.toJson(userMap), Long.valueOf(7*24*60*60)); //7天有效,跟微信的过期时间保持一致
        	logger.info("formId保存成功:"+key);
        	return true;
    	}
    	return false;
    	
    }
    
    
    @Override
    public synchronized void sendRenderTemplateMsg(Long userId, Long designPlanId,String designPlanName,String renderResult,Integer renderType,Integer renderLevel) {
    	String key = REDIS_USER_RENDER_KEY_PREFIX+RENDER_TYPE_MAP.get(renderType)+":"+RENDER_LEVEL_MAP.get(renderLevel)+":"+userId.toString()+"-"+designPlanId.toString();
    	Object obj = redisService.get(key);
    	if(obj!=null) {
    		Map userMap = GsonUtil.fromJson(obj.toString(),Map.class);
    		//获取模块数据
    		Map data = this.getRenderTempalteData(designPlanName,renderResult);
    		//发送消息
        	send((String)userMap.get("appid"), (String)userMap.get("openid"), 
        			(String)userMap.get("page"), (String)userMap.get("formId"), data);
        	redisService.remove(key);
    	}else {
    		logger.info("渲染模板消息通知失败:未找到用户渲染方案对应的formId:"+key);
    		throw new RuntimeException("渲染模板消息通知失败:未找到用户渲染方案对应的formId:"+key);
    	}
    }
    
    /**
	 * 获取模板数据,替换模板里面的配置参数
	 * @return
	 */
	private Map getRenderTempalteData(String designPlanName,String renderResult) {
		Map<String, Map> keywordsMap = new LinkedHashMap<String, Map>();
		Map<String, String> keywordTempMap = null;
		
		keywordTempMap = new HashMap<String, String>();
		keywordTempMap.put("value", designPlanName);
		keywordsMap.put("keyword1", keywordTempMap);
		
		keywordTempMap = new HashMap<String, String>();
		keywordTempMap.put("value", renderResult);
		keywordsMap.put("keyword2", keywordTempMap);
		
		return keywordsMap;
	}
	
	/**
	 * 调用微信发送模板消息接口
	 * @param appid
	 * @param openid
	 * @param templateId
	 * @param page
	 * @param formId
	 * @param data
	 */
	private void send(String appid,String openid, String page, String formId, Map data) {
		//0.根据appid获取小程序配置信息
		BaseCompanyMiniProgramConfig config = miniProgramConfigService.getMiniProgramConfig(appid);
        if (config == null || StringUtils.isBlank(config.getAppSecret())) {
            logger.error("appid错误或者服务器未配置secret:" + appid);
            throw new RuntimeException("appid错误或者服务器未配置secret");
        }

        BaseCompanyMiniProgramTemplateMsg templateMsg = miniProgramTemplateMsgService.getMiniProgramTempateMsg(appid, BaseCompanyMiniProgramTemplateMsg.TEMPLATE_TYPE_RENDER);
        if (templateMsg == null || StringUtils.isBlank(templateMsg.getTemplateId())) {
            logger.error("未配置渲染模板消息id" + appid);
            throw new RuntimeException("未配置渲染模板消息id");
        }
        String templateId = templateMsg.getTemplateId();
        //1.获取accessToken
        String accessToken = this.getAccessToken(appid, config.getAppSecret());
        logger.info("openid:"+openid+",accessToken:"+accessToken);
        if(StringUtils.isBlank(accessToken)) {
        	 throw new RuntimeException("获取accessToken失败");
        }
        
        //2.发送模板消息   
        String reqUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token={0}";
        Map<String, String> paramsMap = this.getTemplateMsgParam(openid, templateId, page, formId, data); 
        String paramsJson = GsonUtil.toJson(paramsMap);
        logger.info("发送模板消息参数:"+paramsJson);
        String resultStr = HttpClientUtil.doPostJsonMethod(MessageFormat.format(reqUrl,accessToken),paramsJson);
       
        //3.解析返回结果
        Map<String, Object> map = GsonUtil.fromJson(resultStr, Map.class);
        if (map != null) {
        	Double errcode = (Double)map.get("errcode");
            if (errcode==null || errcode.intValue()!=0) {
                logger.error("发送渲染模板消息返回:" + resultStr);
                throw new RuntimeException("发送渲染模板消息失败");
            }
        }
	}
	/**
	 * 获取accessToken:先从缓存拿,拿不到再去请求微信接口.获取到的token有效期是2个小时
	 * @param appid
	 * @param secret
	 * @return
	 */
	private String getAccessToken(String appid,String secret) {
		Object obj = redisService.get(REDIS_ACCESS_TOKEN_KEY_PREFIX+appid);
		if(obj==null) {
			return callToGetAccessToken(appid,secret);
		}else {
			//验证token是否有效
			String accessToken = obj.toString();
			String url = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=" + accessToken;
			String resultStr = HttpClientUtil.doGetMethod(url);
			if (resultStr!=null && resultStr.contains("ip_list")) {
				return accessToken;
			}else {
				logger.info("无效token,重新取token:"+appid);
				return callToGetAccessToken(appid,secret);
			}
		}
	}
	
	private String callToGetAccessToken(String appid,String secret) {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";
		String resultStr = HttpClientUtil.doGetMethod(MessageFormat.format(url,appid,secret));
		Map<String, Object> map = GsonUtil.fromJson(resultStr, Map.class);
		if (map != null) {
            String accessToken = (String) map.get("access_token");
            if (StringUtils.isBlank(accessToken)) {
                logger.error("获取accessToken返回:" + resultStr);
                return null;
            }
            redisService.set(REDIS_ACCESS_TOKEN_KEY_PREFIX+appid,accessToken,Long.valueOf(1800));//官方描述2个小时有效,实际却没有.这里改成半小时.
            return accessToken;
        }
		return null;
	}
	
	public static void main(String[] args) {
		
		Map<String, Map> dataMap = new HashMap<String, Map>();
		Map<String, Map> keywordsMap = new LinkedHashMap<String, Map>();
		Map<String, String> keywordTempMap = null;
		
		dataMap.put("data", keywordsMap);
		keywordTempMap = new HashMap<String, String>();
		keywordTempMap.put("value", "aaa");
		keywordsMap.put("keyword1", keywordTempMap);
		
		keywordTempMap = new HashMap<String, String>();
		keywordTempMap.put("value", "bbb");
		keywordsMap.put("keyword2", keywordTempMap);
		
	 
		 Map paramsMap = new HashMap();
		 paramsMap.put("touser", "o-D9N5UU24UYBRmLdxvoTntHalJQ");
		 paramsMap.put("template_id", "abc"); 
		 paramsMap.put("page", "/pages/my-tasks/my-tasks");
		 
		 paramsMap.put("form_id", "1532670076365");
		 paramsMap.put("data", dataMap);
		
		 System.out.println(GsonUtil.toJson(dataMap));
	       //2.发送模板消息   
      //  String reqUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token={0}";
       
       // String resultStr = HttpClientUtil.doPostJsonMethod(MessageFormat.format(reqUrl,"12_wbsKqHRknUz3zYSnceqUdz78zVqLlq8g-t4BIPv_3h9y8WX9D_M0H-ENnG4FxmvqnyeDdApdPsGEtIOwctmXBVSpWh3OgqqUEF9Z8krGUs_xlJodZYBAtz7aS8Q3zfS-tYv5sNXCRORpuQLINPHaAFAWYQ"),GsonUtil.toJson(paramsMap));
       // System.out.println(resultStr);
		//String url = "https://system.ci.sanduspace.com/v1/notify/wx/sendRenderTemplateMsg";
		/*Map<String, String> paramsMap = new HashMap<String,String>();
		paramsMap.put("userId","111");
		 paramsMap.put("formId","aaa");
	        String resultStr = HttpClientUtil.doPostMethod(  url,paramsMap);
		 */
		//System.out.println(resultStr);
	}
	
	/**
	 * 获取调用微信发送模板消息方法参数
	 * @param openid
	 * @param templateId
	 * @param page
	 * @param formId
	 * @param data
	 * @return
	 */
	private Map<String, String> getTemplateMsgParam(String openid,String templateId,String page,String formId,Map data){
		 Map paramsMap = new HashMap();
		 paramsMap.put("touser", openid);
		 paramsMap.put("template_id", templateId);
		 if(StringUtils.isNotBlank(page)) {
			 paramsMap.put("page", page);
		 }
		 paramsMap.put("form_id", formId);
		 paramsMap.put("data", data);
		 return paramsMap;
	}
	
	

}
