package com.sandu.api.restexture.service;

import com.sandu.api.resmodel.model.ModelAreaTextureRel;

import java.util.List;

/**
 * @author Sandu
 */
public interface ModelAreaTextureRelService {
    /**
     * 新增模型区域-材质中间表记录
     * @param list
     * @return
     */
    int insertList(List<ModelAreaTextureRel> list);

    /**
     * 根据模型区域ID删除记录
     * @param areaIds
     * @return
     */
    int deleteByAreaIds(List<Integer> areaIds);

    List<Integer> getIdsByAreaIds(List<Integer> ids);

    int deleteByIds(List<Integer> matrIds);
}
