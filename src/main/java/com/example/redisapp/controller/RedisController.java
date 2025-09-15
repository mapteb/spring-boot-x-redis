package com.example.redisapp.controller;

import com.example.redisapp.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @PostMapping("/set")
    public ResponseEntity<Map<String, String>> setValue(
            @RequestParam String key,
            @RequestParam String value,
            @RequestParam(required = false) Long ttl) {
        
        if (ttl != null && ttl > 0) {
            redisService.setValue(key, value, ttl, TimeUnit.SECONDS);
        } else {
            redisService.setValue(key, value);
        }
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Key-value pair stored successfully");
        response.put("key", key);
        response.put("value", value);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{key}")
    public ResponseEntity<Map<String, Object>> getValue(@PathVariable String key) {
        Object value = redisService.getValue(key);
        Map<String, Object> response = new HashMap<>();
        
        if (value != null) {
            response.put("key", key);
            response.put("value", value);
            response.put("exists", true);
            return ResponseEntity.ok(response);
        } else {
            response.put("key", key);
            response.put("message", "Key not found");
            response.put("exists", false);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{key}")
    public ResponseEntity<Map<String, String>> deleteKey(@PathVariable String key) {
        Boolean deleted = redisService.deleteKey(key);
        Map<String, String> response = new HashMap<>();
        
        if (deleted) {
            response.put("message", "Key deleted successfully");
            response.put("key", key);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Key not found");
            response.put("key", key);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/exists/{key}")
    public ResponseEntity<Map<String, Object>> checkKeyExists(@PathVariable String key) {
        Boolean exists = redisService.hasKey(key);
        Map<String, Object> response = new HashMap<>();
        response.put("key", key);
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/expire/{key}")
    public ResponseEntity<Map<String, String>> setExpire(
            @PathVariable String key,
            @RequestParam long seconds) {
        
        Boolean result = redisService.expire(key, seconds, TimeUnit.SECONDS);
        Map<String, String> response = new HashMap<>();
        
        if (result) {
            response.put("message", "Expiration set successfully");
            response.put("key", key);
            response.put("seconds", String.valueOf(seconds));
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Key not found or expiration failed");
            response.put("key", key);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/ttl/{key}")
    public ResponseEntity<Map<String, Object>> getTimeToLive(@PathVariable String key) {
        Long ttl = redisService.getExpire(key);
        Map<String, Object> response = new HashMap<>();
        response.put("key", key);
        response.put("ttl", ttl);
        
        if (ttl == -2) {
            response.put("message", "Key does not exist");
        } else if (ttl == -1) {
            response.put("message", "Key exists but has no expiration");
        } else {
            response.put("message", "Key will expire in " + ttl + " seconds");
        }
        
        return ResponseEntity.ok(response);
    }
}
