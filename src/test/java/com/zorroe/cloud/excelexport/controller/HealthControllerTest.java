package com.zorroe.cloud.excelexport.controller;

import com.zorroe.cloud.excelexport.entity.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HealthControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testHealth() {
        ResponseEntity<Result> response = restTemplate.getForEntity("/health", Result.class);
        
        assertEquals(200, response.getStatusCodeValue());
        Result<Map<String, Object>> result = response.getBody();
        assertNotNull(result);
        assertEquals(200, result.getCode());
        assertEquals("操作成功", result.getMsg());
        assertNotNull(result.getData());
        assertEquals("UP", result.getData().get("status"));
        assertNotNull(result.getData().get("timestamp"));
    }
}
