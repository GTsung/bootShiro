package com.home.bootShiro.builder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author guxc
 * @date 2020/3/1
 */
public class ConditionBuilder extends BaseConditionBuilder<ConditionBuilder>{

    public ConditionBuilder applyCode(String applyCode) {
        if(!StringUtils.isEmpty(applyCode)){
            equalMap.put("applyCode", applyCode);
        }
        return this;
    }

    public ConditionBuilder approvalCodes(List<String> applyId) {
        if(!CollectionUtils.isEmpty(applyId)){
            inMap.put("approvalCode", applyId);
        }
        return this;
    }

    public ConditionBuilder khxxIdList(List<String> khxxIdList) {
        if(!CollectionUtils.isEmpty(khxxIdList)) {
            inMap.put("khxxId", khxxIdList);
        }
        return this;
    }

    public ConditionBuilder companyProvince(String companyProvince) {
        if(!StringUtils.isEmpty(companyProvince)) {
            equalMap.put("companyProvince", companyProvince);
        }
        return this;
    }

    public ConditionBuilder companyCity(String companyCity) {
        if(!StringUtils.isEmpty(companyCity)) {
            equalMap.put("companyCity", companyCity);
        }
        return this;
    }

    public ConditionBuilder prodId(Long prodId) {
        if(null != prodId) {
            equalMap.put("prodId", prodId);
        }
        return this;
    }

    public ConditionBuilder approvalCode(String approvalCode) {
        if(StringUtils.isNotBlank(approvalCode)) {
            equalMap.put("approvalCode", approvalCode);
        }
        return this;
    }

    public ConditionBuilder fundId(Long fundId) {
        if(null != fundId) {
            equalMap.put("fundId", fundId);
        }
        return this;
    }

    public ConditionBuilder stage(Integer stage) {
        if(null != stage) {
            equalMap.put("stage", stage);
        }
        return this;
    }
}
