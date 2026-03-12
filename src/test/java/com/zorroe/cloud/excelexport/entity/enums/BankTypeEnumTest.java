package com.zorroe.cloud.excelexport.entity.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankTypeEnumTest {

    @Test
    void testGetNameByCode() {
        assertEquals("中国银行", BankTypeEnum.getNameByCode("BOC"));
        assertEquals("中国工商银行", BankTypeEnum.getNameByCode("ICBC"));
        assertNull(BankTypeEnum.getNameByCode("UNKNOWN"));
        assertNull(BankTypeEnum.getNameByCode(null));
    }

    @Test
    void testGetCode() {
        assertEquals("BOC", BankTypeEnum.BOC.getCode());
        assertEquals("ICBC", BankTypeEnum.ICBC.getCode());
    }

    @Test
    void testGetName() {
        assertEquals("中国银行", BankTypeEnum.BOC.getName());
        assertEquals("中国工商银行", BankTypeEnum.ICBC.getName());
    }

    @Test
    void testValues() {
        BankTypeEnum[] values = BankTypeEnum.values();
        assertEquals(2, values.length);
        assertEquals(BankTypeEnum.BOC, values[0]);
        assertEquals(BankTypeEnum.ICBC, values[1]);
    }
}
