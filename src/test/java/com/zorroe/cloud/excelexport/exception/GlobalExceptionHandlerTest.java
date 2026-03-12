package com.zorroe.cloud.excelexport.exception;

import com.zorroe.cloud.excelexport.entity.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GlobalExceptionHandlerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testHandleIllegalArgumentException() {
        // 测试通过访问一个不存在的模板类型来触发 IllegalArgumentException
        ResponseEntity<Result> response = restTemplate.getForEntity(
                "/excel/template?templateType=nonexistent", Result.class);
        
        assertEquals(200, response.getStatusCodeValue());
        Result result = response.getBody();
        assertNotNull(result);
        assertEquals(500, result.getCode());
        assertTrue(result.getMsg().contains("未找到模板类型"));
    }
}
