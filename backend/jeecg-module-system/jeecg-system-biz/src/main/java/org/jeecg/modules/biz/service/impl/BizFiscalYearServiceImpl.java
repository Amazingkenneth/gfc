package org.jeecg.modules.biz.service.impl;

import java.util.List;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.jeecg.modules.biz.entity.BizFiscalYear;
import org.jeecg.modules.biz.mapper.BizFiscalYearMapper;
import org.jeecg.modules.biz.service.IBizFiscalYearService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 财年信息
 * @Author: jeecg-boot
 * @Date: 2023-09-23
 * @Version: V1.0
 */
@Service
public class BizFiscalYearServiceImpl extends ServiceImpl<BizFiscalYearMapper, BizFiscalYear> implements IBizFiscalYearService {
    public Integer getMaxYearCode() {
        Integer maxYearCode = baseMapper.getMaxYearCode();
        return maxYearCode == null ? 0 : maxYearCode;
    }
    
    public BizFiscalYear getByYearCode(Integer yearCode){
        BizFiscalYear year = baseMapper.getByYearCode(yearCode);
        return year;
    }   

    public Integer getProcessCount() {
        Integer processCount = baseMapper.getProcessCount();
        return processCount == null ? 0 : processCount;
    }

    public Integer getActiveYearCode() {
        Integer activeYearCode = baseMapper.getActiveYearCode();
        return activeYearCode == null ? 1 : activeYearCode;
    }

    @Override
    public void updateResourceStatus(Integer yearCode) {
        baseMapper.updateResourceStatus(yearCode);
    }

    public List<BizFiscalYear> getAllFiscalYears(){
        List<BizFiscalYear> years = baseMapper.getAllFiscalYears();
        return years;
    }
}
