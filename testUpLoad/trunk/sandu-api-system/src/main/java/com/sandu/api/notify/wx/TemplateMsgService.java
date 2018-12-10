package com.sandu.api.notify.wx;

import org.springframework.stereotype.Component;

@Component
public interface TemplateMsgService{

	/**
	 * redis存储用户渲染方案对应的formId
	 * @param userId
	 * @param designPlanId
	 * @param formId
	 * @param forwardPage
	 * @param renderType 1为普通渲染 2替换渲染
	 * @param renderLevel 渲染级别 1:普通照片级 4:720单点 6.视频 8:720多点
	 */
	public boolean saveUserRenderFormId(Long userId,Long designPlanId,String formId,String forwardPage,Integer renderType,Integer renderLevel);
	
	/**
	 * 发送渲染模板消息
	 * @param userId
	 * @param designPlanId
	 * @param designPlanName
	 * @param renderResult
	 * @param renderType 1为普通渲染 2替换渲染
	 * @param renderLevel 渲染级别 1:普通照片级 4:720单点 6.视频 8:720多点
	 */
	void sendRenderTemplateMsg(Long userId, Long designPlanId,String designPlanName,String renderResult,Integer renderType,Integer renderLevel);
}
