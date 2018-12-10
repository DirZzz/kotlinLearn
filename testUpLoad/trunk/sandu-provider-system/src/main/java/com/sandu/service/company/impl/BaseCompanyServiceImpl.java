package com.sandu.service.company.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sandu.api.company.input.FranchiserListQuery;
import com.sandu.api.company.model.CompanyCategoryRel;
import com.sandu.api.company.output.FranchiserDetailsVO;
import com.sandu.api.company.output.FranchiserListVO;
import com.sandu.commons.LoginUser;
import com.sandu.commons.Utils;
import com.sandu.commons.constant.BusinessTypeConstant;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandu.api.company.model.BaseCompany;
import com.sandu.api.company.output.BaseCompanyDetailsVO;
import com.sandu.api.company.output.CompanyFranchiserVO;
import com.sandu.api.company.service.BaseCompanyService;
import com.sandu.service.company.dao.BaseCompanyMapper;

/**
 * @Author chenqiang
 * @Description 企业 业务层
 * @Date 2018/6/1 0001 10:15
 * @Modified By
 */
@Service("baseCompanyService")
public class BaseCompanyServiceImpl implements BaseCompanyService {

    @Autowired
    private BaseCompanyMapper baseCompanyMapper;


    public BaseCompany getCompanyById(long id) {
        return baseCompanyMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据主键id 物理删除企业信息
     * @author chenqiang
     * @param id 主键id
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    public int deleteByPrimaryKey(Long id){
        return baseCompanyMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据主键id 逻辑删除企业信息
     * @author chenqiang
     * @param id 主键id
     * @param loginName 删除人登录名
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    public int deleteLogicByPrimaryKey( Long id,String loginName){
        return baseCompanyMapper.deleteLogicByPrimaryKey(id,loginName);
    }

    /**
     * 根据企业基础实体类 选择性 新增数据
     * @author chenqiang
     * @param record 企业基础实体类 对象
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    public int insertSelective(BaseCompany record){
        int count = baseCompanyMapper.insertSelective(record);
        if(count > 0 )
            return record.getId().intValue();
        else
            return 0;
    }

    /**
     * 根据主键id 查询 企业基础信息
     * @author chenqiang
     * @param id 企业主键id
     * @return 企业基础实体类
     * @date 2018/5/31 0031 18:21
     */
    public BaseCompany selectByPrimaryKey(Long id){
        return baseCompanyMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据主键 选择性 修改数据
     * @author chenqiang
     * @param record 企业基础实体类 对象
     * @return 受影响的行数
     * @date 2018/5/31 0031 18:21
     */
    public int updateByPrimaryKeySelective(BaseCompany record){
        return baseCompanyMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据企业id 查询经销商列表
     * @author chenqiang
     * @param companyId 企业id
     * @param businessType 企业类型
     * @return CompanyFranchiserVO 列表
     * @date 2018/5/31 0031 18:21
     */
    public List<CompanyFranchiserVO> getFranchiserListByCompany(Integer companyId, Integer businessType){
        List<CompanyFranchiserVO> franchiserVOList = baseCompanyMapper.selectFranchiserByCompanyId(companyId, BusinessTypeConstant.BUSINESSTYPE_FRANCHISER);

        return franchiserVOList != null ? franchiserVOList : new ArrayList<>();
    }


    /**
     * 根据企业id 查询企业编辑信息
     * @author chenqiang
     * @param companyId 企业id
     * @return BaseCompanyDetailsVO 对象
     * @date 2018/5/31 0031 18:21
     */
    @Override
    public BaseCompanyDetailsVO getCompanyInfo(Integer companyId){
        return baseCompanyMapper.selectCompanyInfo(companyId);
    }

    /**
     * 根据企业与查询信息 查询经销商列表
     * @author chenqiang
     * @param query FranchiserListQuery 对象
     * @return FranchiserListVO 列表
     * @date 2018/5/31 0031 18:21
     */
    public List<FranchiserListVO> getFranchiserList(FranchiserListQuery query){
        List<FranchiserListVO> franchiserListVOList =  baseCompanyMapper.selectFranchiserList(query);

        return franchiserListVOList != null ? franchiserListVOList : new ArrayList<>();
    }
    public int getFranchiserListCount(FranchiserListQuery query){
        return baseCompanyMapper.selectFranchiserListCount(query);
    }


    /**
     * 根据经销商id 查询 经销商企业 编辑信息
     * @author chenqiang
     * @param companyId 企业id
     * @return FranchiserDetailsVO 对象
     * @date 2018/5/31 0031 18:21
     */
    public FranchiserDetailsVO getFranchiserInfo(Integer companyId){
        return baseCompanyMapper.selectFranchiserInfo(companyId);
    }

    /**
     * 删除企业loggo
     * @author chenqiang
     * @param baseCompany 对象
     * @return 影响行数
     * @date 2018/5/31 0031 18:21
     */
    public int deleteCompanyLogo(BaseCompany baseCompany){
        return baseCompanyMapper.deleteCompanyLogo(baseCompany);
    }

    /**
     * 自动存储系统字段
     * @param baseCompany 企业
     * @param loginUser 当前登录用户
     */
    public void saveSystemInfo(BaseCompany baseCompany, LoginUser loginUser) {
        if(baseCompany != null){
            //新增
            if(baseCompany.getId() == null){
                baseCompany.setGmtCreate(new Date());
                baseCompany.setCreator(loginUser.getLoginName());
                baseCompany.setIsDeleted(0);
                if(baseCompany.getSysCode()==null || "".equals(baseCompany.getSysCode())){
                    baseCompany.setSysCode(Utils.getCurrentDateTime(Utils.DATETIMESSS) +"_"+ Utils.generateRandomDigitString(6));
                }
            }
            //修改
            baseCompany.setGmtModified(new Date());
            baseCompany.setModifier(loginUser.getLoginName());
        }
    }

    /**
     * 根据企业id 获取经销商企业 列表
     * @author chenqiang
     * @param companyId 企业id
     * @return BaseCompany 列表
     * @date 2018/5/31 0031 18:21
     */
    public List<BaseCompany> getFranchiserListByCompanyId(Integer companyId){
        return baseCompanyMapper.selectFranchiserListByCompanyId(companyId);
    }

    /**
     * 查询当前 条件下 最大的code
     * @author chenqiang
     * @param commanyCodePrefix 前缀
     * @param businessTypeList in 集合
     * @param businessTypeNotList not in 集合
     * @return 返回 最大 code
     * @date 2018/6/11 0011 17:16
     */
    public String getMaxCompanyCode(String commanyCodePrefix, List<Integer> businessTypeList,List<Integer> businessTypeNotList){
        return baseCompanyMapper.selectMaxCompanyCode(commanyCodePrefix,businessTypeList,businessTypeNotList);
    }

    /**
     * 查询当前 条件下 最大的code
     * @author chenqiang
     * @param companyName 企业名称
     * @param companyId 企业id
     * @return 返回数量
     * @date 2018/6/25
     */
    public int checkCompanyName(String companyName,Long companyId){
        return baseCompanyMapper.selectCountByCompanyName(companyName,companyId);
    }


    /**
     * 修改企业所属行业
     * @param industrys 所属行业
     * @param IdList 企业Id
     * @return 操作影响的行数
     */
    @Override
    public Integer updateCompanyIndustryById( String industrys,List<Long> IdList,String loginUserName) {
        return baseCompanyMapper.updateCompanyIndustryById(industrys,IdList,loginUserName);
    }

}
