package com.zorroe.cloud.excelexport.entity.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExcelImportConstantTest {

    @Test
    void testMaxImportRows() {
        assertEquals(1000, ExcelImportConstant.MAX_IMPORT_ROWS);
    }

    @Test
    void testHeadRowNumber() {
        assertEquals(1, ExcelImportConstant.HEAD_ROW_NUMBER);
    }
}
