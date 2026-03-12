package com.zorroe.cloud.excelexport.entity.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidateErrorTest {

    @Test
    void testConstructor() {
        ValidateError error = new ValidateError(1, "name", "名称不能为空");
        assertEquals(1, error.getRowNum());
        assertEquals("name", error.getField());
        assertEquals("名称不能为空", error.getMessage());
    }

    @Test
    void testNoArgsConstructor() {
        ValidateError error = new ValidateError();
        assertNull(error.getRowNum());
        assertNull(error.getField());
        assertNull(error.getMessage());
    }

    @Test
    void testSettersAndGetters() {
        ValidateError error = new ValidateError();
        error.setRowNum(2);
        error.setField("age");
        error.setMessage("年龄必须大于0");
        
        assertEquals(2, error.getRowNum());
        assertEquals("age", error.getField());
        assertEquals("年龄必须大于0", error.getMessage());
    }

    @Test
    void testToString() {
        ValidateError error = new ValidateError(1, "name", "名称不能为空");
        String str = error.toString();
        assertNotNull(str);
        assertTrue(str.contains("ValidateError"));
    }
}
