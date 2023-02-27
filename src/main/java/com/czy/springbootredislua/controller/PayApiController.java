package com.czy.springbootredislua.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;

/**
 * @author 曹振远
 * @date 2023/02/24
 **/
@RestController
@RequestMapping("/payApi")
public class PayApiController {

    @Autowired
    @Qualifier("redisScriptPay")
    private DefaultRedisScript<Boolean> redisScriptPay;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * eval script number <key> <args>
     */
    @RequestMapping("/pay")
    public Boolean pay() {
        //key ="pay-1"   1 时间   5000 限流最大值
        Boolean result = stringRedisTemplate.execute(redisScriptPay, Collections.singletonList("pay-1"), "1", "2000");
        if (Boolean.FALSE.equals(result)) {
            System.out.println(new Date() + " " + false);
        }
        return result;
    }
}
