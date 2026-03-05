package com.zorroe.cloud.excelexport.strategy;

import com.zorroe.cloud.excelexport.entity.dto.ExcelDTO;
import com.zorroe.cloud.excelexport.listener.AbstractExcelListener;

import java.util.Collection;
import java.util.List;

public interface ExcelImportStrategy<T extends ExcelDTO> {

    /**
     * 获取支持的模板类型
     */
    String getSupportTemplateType();

    /**
     * 获取Excel监听器
     */
    AbstractExcelListener<T> getListener();

    /**
     * 数据入库（子类实现具体入库逻辑）
     */
    boolean saveData(List<? extends ExcelDTO> dataList);

    /**
     * 获取数据类型 Class
     */
    Class<T> getDataClass();

    /**
     * 生成空模板数据（用于下载）
     * @return 空数据列表，仅包含表头
     */
    default Collection<T> generateTemplateData() {
        return new java.util.ArrayList<>();
    }
}
