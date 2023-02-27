package com.czy.springbootredislua.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author 曹振远
 * @date 2023/02/27
 **/
@RestController
@RequestMapping("/apiBrushProtection")
public class ApiBrushProtectionController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    @Qualifier("apiBrushProtection")
    private DefaultRedisScript<Boolean> apiBrushProtection;

    @RequestMapping("/api")
    public Boolean lua() {
        Boolean result = stringRedisTemplate.execute(apiBrushProtection, Arrays.asList("api-127.0.0.1", "api-black-127.0.0.1"),
                "10", "20", "259200");
        if (Boolean.FALSE.equals(result)) {
            System.out.println(false);
        }
        return result;
    }
}
