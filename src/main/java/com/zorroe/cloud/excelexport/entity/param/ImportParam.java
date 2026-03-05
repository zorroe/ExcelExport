package com.zorroe.cloud.excelexport.entity.param;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Excel导入请求DTO
 */
@Data
public class ImportParam {

    /** 模板类型（对应ExcelTemplateType的code） */
    private String templateType;

    /** 上传的Excel文件 */
    private MultipartFile file;
}
