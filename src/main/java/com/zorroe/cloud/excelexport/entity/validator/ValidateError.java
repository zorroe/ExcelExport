package com.zorroe.cloud.excelexport.entity.validator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 导入校验错误信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateError {

    /**
     * 行号（从1开始）
     */
    private Integer rowNum;
    /**
     * 错误字段
     */
    private String field;
    /**
     * 错误信息
     */
    private String message;
}
