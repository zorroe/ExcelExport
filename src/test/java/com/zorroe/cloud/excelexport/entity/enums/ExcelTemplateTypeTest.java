package com.zorroe.cloud.excelexport.entity.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExcelTemplateTypeTest {

    @Test
    void testGetByCode() {
        assertEquals(ExcelTemplateType.PRODUCT, ExcelTemplateType.getByCode("product"));
        assertEquals(ExcelTemplateType.CUSTOMER, ExcelTemplateType.getByCode("customer"));
        assertNull(ExcelTemplateType.getByCode("unknown"));
        assertNull(ExcelTemplateType.getByCode(null));
    }

    @Test
    void testGetCode() {
        assertEquals("product", ExcelTemplateType.PRODUCT.getCode());
        assertEquals("customer", ExcelTemplateType.CUSTOMER.getCode());
    }

    @Test
    void testGetDesc() {
        assertEquals("商品导入模板", ExcelTemplateType.PRODUCT.getDesc());
        assertEquals("客户导入模板", ExcelTemplateType.CUSTOMER.getDesc());
    }
}
