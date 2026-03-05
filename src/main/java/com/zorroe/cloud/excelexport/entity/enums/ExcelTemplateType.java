package com.zorroe.cloud.excelexport.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExcelTemplateType {

    /**
     * 商品模板
     */
    PRODUCT("product", "商品导入模板"),
    /**
     * 客户模板
     */
    CUSTOMER("customer", "客户导入模板"),

    ;

    private final String code;
    private final String desc;

    // 根据编码获取枚举
    public static ExcelTemplateType getByCode(String code) {
        for (ExcelTemplateType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return null;
    }
}
