package com.zorroe.cloud.excelexport.strategy;

import cn.hutool.core.date.DatePattern;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.zorroe.cloud.excelexport.entity.param.ExportParam;
import com.zorroe.cloud.excelexport.entity.vo.ExportVo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

/**
 * Excel导出策略接口（泛型绑定查询条件类型）
 *
 * @param <Q> 查询条件类型
 * @param <T> 导出数据实体类型
 */
public interface ExcelExportStrategy<Q extends ExportParam, T extends ExportVo> {
    /**
     * 获取导出类型标识（如ACCOUNT、ACCOUNT_BALANCE）
     */
    String getExportType();

    /**
     * 获取查询条件的Class类型（用于参数转换）
     */
    Class<Q> getQueryClass();

    /**
     * 获取导出数据实体的Class类型（EasyExcel需要）
     */
    Class<T> getExportDataClass();

    /**
     * 根据查询条件获取导出数据
     *
     * @param query 查询条件（已转换为对应类型）
     */
    Collection<T> getExportData(Q query);

    /**
     * 生成导出文件名（默认带时间戳）
     */
    default String getFileName() {
        return getExportType() + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern(DatePattern.PURE_DATETIME_PATTERN)) + ".xlsx";
    }

    /**
     * 自定义Excel写入配置（可选，如表头样式、列宽等）
     */
    default void customizeExcelWriter(ExcelWriterBuilder writerBuilder) {
        // 默认空实现，子类可重写
    }
}
