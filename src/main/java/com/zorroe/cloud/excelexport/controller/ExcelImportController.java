package com.zorroe.cloud.excelexport.controller;

import com.zorroe.cloud.excelexport.entity.Result;
import com.zorroe.cloud.excelexport.entity.param.ImportParam;
import com.zorroe.cloud.excelexport.factory.ExcelImportFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/excel")
public class ExcelImportController {

    @Resource
    private ExcelImportFactory excelImportFactory;

    @PostMapping("/import")
    public Result<String> importExcel(ImportParam importParam) {
        return excelImportFactory.importExcel(importParam);
    }
}
