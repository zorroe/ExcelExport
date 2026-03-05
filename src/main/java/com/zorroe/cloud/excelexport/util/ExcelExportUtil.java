package com.zorroe.cloud.excelexport.util;

import org.apache.fesod.sheet.FesodSheet;
import org.apache.fesod.sheet.write.builder.ExcelWriterBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class ExcelExportUtil {

    /**
     * 通用Excel下载方法
     * @param response 响应对象
     * @param fileName 文件名
     * @param dataClass 导出数据实体类
     * @param data 导出数据
     */
    public static <T> void exportExcel(HttpServletResponse response,
                                       String fileName,
                                       Class<T> dataClass,
                                       Collection<T> data,
                                       ExcelWriterCustomizer customize) {
        try {
            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 防止文件名中文乱码
            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()).replaceAll("\\+", "%20");
            response.setHeader("Content-Disposition", "attachment;filename*=utf-8''" + encodedFileName);

            // 构建Excel写入器
            ExcelWriterBuilder writerBuilder = FesodSheet.write(response.getOutputStream(), dataClass);
            // 自定义配置（如样式、列宽等）
            if (customize != null) {
                customize.customize(writerBuilder);
            }
            writerBuilder.sheet("Sheet1").doWrite(data);
        } catch (Exception e) {
            throw new RuntimeException("Excel导出失败：" + e.getMessage(), e);
        }
    }

    /**
     * 自定义Excel写入配置接口
     */
    @FunctionalInterface
    public interface ExcelWriterCustomizer {
        void customize(ExcelWriterBuilder writerBuilder);
    }
}
