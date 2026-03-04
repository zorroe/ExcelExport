package com.zorroe.cloud.excelexport.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BankTypeEnum {

    BOC("BOC", "中国银行"),

    ICBC("ICBC", "中国工商银行"),

    ;

    private final String code;

    private final String name;

    public static String getNameByCode(String code) {
        for (BankTypeEnum value : BankTypeEnum.values()) {
            if (value.getCode().equals(code)) {
                return value.getName();
            }
        }
        return null;
    }
}
