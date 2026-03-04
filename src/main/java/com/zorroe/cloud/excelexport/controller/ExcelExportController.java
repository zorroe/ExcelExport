package com.zorroe.cloud.excelexport.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zorroe.cloud.excelexport.entity.param.ExportParam;
import com.zorroe.cloud.excelexport.entity.vo.ExportVo;
import com.zorroe.cloud.excelexport.factory.ExcelExportFactory;
import com.zorroe.cloud.excelexport.strategy.ExcelExportStrategy;
import com.zorroe.cloud.excelexport.util.ExcelExportUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/excel")
public class ExcelExportController {

    @Resource
    private ExcelExportFactory excelExportFactory;

    @Resource
    private ObjectMapper objectMapper;

    @PostMapping("/export")
    public <Q extends ExportParam, T extends ExportVo> void export(@RequestParam String exportType,
                                                                   @RequestBody(required = false) Map<String, Object> params,
                                                                   HttpServletResponse response) {
        ExcelExportStrategy<Q, T> strategy = excelExportFactory.getStrategy(exportType);
        Q query = null;
        if (params != null && !params.isEmpty()) {
            query = objectMapper.convertValue(params, strategy.getQueryClass());
        }

        Collection<T> exportData = strategy.getExportData(query);
        ExcelExportUtil.exportExcel(response, strategy.getFileName(), strategy.getExportDataClass(), exportData, strategy::customizeExcelWriter);
    }
}
