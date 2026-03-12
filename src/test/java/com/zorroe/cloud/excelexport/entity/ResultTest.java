package com.zorroe.cloud.excelexport.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultTest {

    @Test
    void testSuccess() {
        Result<String> result = Result.success("data");
        assertEquals(200, result.getCode());
        assertEquals("操作成功", result.getMsg());
        assertEquals("data", result.getData());
    }

    @Test
    void testFail() {
        Result<String> result = Result.fail("error message");
        assertEquals(500, result.getCode());
        assertEquals("error message", result.getMsg());
        assertNull(result.getData());
    }

    @Test
    void testConstructor() {
        Result<String> result = new Result<>(200, "success", "data");
        assertEquals(200, result.getCode());
        assertEquals("success", result.getMsg());
        assertEquals("data", result.getData());
    }

    @Test
    void testSettersAndGetters() {
        Result<String> result = new Result<>();
        result.setCode(200);
        result.setMsg("msg");
        result.setData("data");
        
        assertEquals(200, result.getCode());
        assertEquals("msg", result.getMsg());
        assertEquals("data", result.getData());
    }
}
