package com.zorroe.cloud.excelexport.listener;

import com.zorroe.cloud.excelexport.entity.dto.ExcelDTO;
import com.zorroe.cloud.excelexport.entity.enums.ExcelImportConstant;
import com.zorroe.cloud.excelexport.entity.validator.ValidateError;
import lombok.extern.slf4j.Slf4j;
import org.apache.fesod.sheet.context.AnalysisContext;
import org.apache.fesod.sheet.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Excel解析监听器抽象类（模板方法模式）
 *
 * @param <T> 解析后的数据类型
 */
@Slf4j
public abstract class AbstractExcelListener<T extends ExcelDTO> extends AnalysisEventListener<T> {

    /**
     * 解析后的数据集
     */
    protected List<T> dataList = new ArrayList<>();
    /**
     * 校验错误信息
     */
    protected List<ValidateError> errorList = new ArrayList<>();
    /**
     * 当前行号（EasyExcel行号从1开始，表头占1行，数据行从2开始）
     */
    protected int currentRowNum;

    @Override
    public void invoke(T t, AnalysisContext context) {
        currentRowNum = context.readRowHolder().getRowIndex();
        if (currentRowNum == ExcelImportConstant.HEAD_ROW_NUMBER) {
            return;
        }
        if (dataList.size() >= ExcelImportConstant.MAX_IMPORT_ROWS) {
            errorList.add(new ValidateError(currentRowNum, null, "导入数据超出最大限制"));
        }
        validate(t, currentRowNum);
        if (errorList.stream().noneMatch(e -> e.getRowNum() == currentRowNum)) {
            dataList.add(t);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("解析完成，数据行数：{}, 校验错误{}条", dataList.size(), errorList.size());
    }

    protected abstract void validate(T t, int rowNum);

    public List<T> getDataList() {
        return dataList;
    }

    public List<ValidateError> getErrorList() {
        return errorList;
    }
}
