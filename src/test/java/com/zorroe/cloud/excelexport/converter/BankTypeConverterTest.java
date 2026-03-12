package com.zorroe.cloud.excelexport.converter;

import com.zorroe.cloud.excelexport.entity.enums.BankTypeEnum;
import org.apache.fesod.sheet.metadata.GlobalConfiguration;
import org.apache.fesod.sheet.metadata.data.WriteCellData;
import org.apache.fesod.sheet.metadata.property.ExcelContentProperty;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankTypeConverterTest {

    private final BankTypeConverter converter = new BankTypeConverter();

    @Test
    void testSupportJavaTypeKey() {
        assertEquals(String.class, converter.supportJavaTypeKey());
    }

    @Test
    void testConvertToExcelDataWithValidCode() throws Exception {
        GlobalConfiguration config = new GlobalConfiguration();
        WriteCellData<?> result = converter.convertToExcelData("BOC", null, config);
        assertEquals("中国银行", result.getStringValue());
    }

    @Test
    void testConvertToExcelDataWithAnotherValidCode() throws Exception {
        GlobalConfiguration config = new GlobalConfiguration();
        WriteCellData<?> result = converter.convertToExcelData("ICBC", null, config);
        assertEquals("中国工商银行", result.getStringValue());
    }

    @Test
    void testConvertToExcelDataWithInvalidCode() throws Exception {
        GlobalConfiguration config = new GlobalConfiguration();
        WriteCellData<?> result = converter.convertToExcelData("UNKNOWN", null, config);
        assertEquals("UNKNOWN", result.getStringValue());
    }

    @Test
    void testConvertToExcelDataWithEmptyString() throws Exception {
        GlobalConfiguration config = new GlobalConfiguration();
        WriteCellData<?> result = converter.convertToExcelData("", null, config);
        assertEquals("", result.getStringValue());
    }

    @Test
    void testConvertToExcelDataWithNull() throws Exception {
        GlobalConfiguration config = new GlobalConfiguration();
        // null 值会抛出 IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convertToExcelData(null, null, config);
        });
    }
}
