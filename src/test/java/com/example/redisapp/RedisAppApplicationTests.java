package com.example.redisapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
/* @TestPropertySource(properties = {
    "spring.redis.host=localhost",
    "spring.redis.port=6370" // Different port to avoid connection attempts
}) */
class RedisAppApplicationTests {

    @Test
    void contextLoads() {
        // This should work without external dependencies
    }
}
