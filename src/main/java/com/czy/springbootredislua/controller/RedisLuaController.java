package com.czy.springbootredislua.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/**
 * @author 曹振远
 * @date 2023/02/24
 **/
@RestController
@RequestMapping("/redisLua")
public class RedisLuaController{

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private DefaultRedisScript<String> redisScript;


    /**
     * eval script number <key> <args>
     * 整合中没有number会根据key的集合获取，多个参数用逗号隔开
     *
     * @return result
     */
    @GetMapping("/doLua")
    public String doLua() {
        return (String) stringRedisTemplate.execute(
                redisScript, Collections.singletonList("uuid"), "wo ai yi tiao chai"
        );
    }
}
