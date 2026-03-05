package com.zorroe.cloud.excelexport.factory;

import cn.hutool.core.collection.CollUtil;
import com.zorroe.cloud.excelexport.entity.Result;
import com.zorroe.cloud.excelexport.entity.dto.ExcelDTO;
import com.zorroe.cloud.excelexport.entity.enums.ExcelImportConstant;
import com.zorroe.cloud.excelexport.entity.enums.ExcelTemplateType;
import com.zorroe.cloud.excelexport.entity.param.ImportParam;
import com.zorroe.cloud.excelexport.entity.validator.ValidateError;
import com.zorroe.cloud.excelexport.listener.AbstractExcelListener;
import com.zorroe.cloud.excelexport.strategy.ExcelImportStrategy;
import org.apache.fesod.sheet.FesodSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class ExcelImportFactory {

    private final Map<String, ExcelImportStrategy<? extends ExcelDTO>> strategyMap = new HashMap<>();

    @Autowired
    private ExcelImportFactory(Set<ExcelImportStrategy<?>> excelImportStrategies) {
        for (ExcelImportStrategy<?> importStrategy : excelImportStrategies) {
            strategyMap.put(importStrategy.getSupportTemplateType(), importStrategy);
        }
    }

    public Result<String> importExcel(ImportParam importParam) {
        // 1. 参数校验
        if (importParam.getFile() == null || importParam.getFile().isEmpty()) {
            return Result.fail("请上传Excel文件");
        }
        ExcelTemplateType templateType = ExcelTemplateType.getByCode(importParam.getTemplateType());
        if (templateType == null) {
            return Result.fail("不支持的模板类型：" + importParam.getTemplateType());
        }
        ExcelImportStrategy<? extends ExcelDTO> strategy = strategyMap.get(templateType.getCode());
        if (strategy == null) {
            return Result.fail("未找到导入类型[" + templateType.getCode() + "]对应的策略");
        }
        AbstractExcelListener<?> listener = strategy.getListener();
        try {
            FesodSheet.read(importParam.getFile().getInputStream(), strategy.getDataClass(), listener)
                    .headRowNumber(ExcelImportConstant.HEAD_ROW_NUMBER)
                    .sheet()
                    .doRead();
        } catch (Exception e) {
            return Result.fail("Excel导入失败：" + e.getMessage());
        }

        List<ValidateError> errorList = listener.getErrorList();
        if (CollUtil.isNotEmpty(errorList)) {
            return Result.fail("数据校验失败，错误行数：" + errorList.size() + "，详情：" + errorList);
        }
        List<? extends ExcelDTO> dataList = listener.getDataList();
        if (CollUtil.isEmpty(dataList)) {
            return Result.fail("导入数据为空");
        }
        boolean saveResult = strategy.saveData(dataList);
        if (!saveResult) {
            return Result.fail("数据入库失败");
        }
        return Result.success("导入成功, 共导入: " + dataList.size() + "条数据");
    }

}
