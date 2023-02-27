package com.czy.springbootredislua.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * @author 曹振远
 * @date 2023/02/24
 **/
@Configuration
public class LuaConfig {


    /**
     * 前面测试的配置
     */
    @Bean
    public DefaultRedisScript<String> redisScript() {
        DefaultRedisScript<String> defaultRedisScript = new DefaultRedisScript<>();
        //设置返回类型
        defaultRedisScript.setResultType(String.class);
        //defaultRedisScript.setScriptText();
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("Demo.lua")));
        return defaultRedisScript;
    }

    /**
     * 限流的Lua脚本的配置
     */
    @Bean
    public DefaultRedisScript<Boolean> redisScriptPay() {
        DefaultRedisScript<Boolean> defaultRedisScript = new DefaultRedisScript<>();
        //设置返回类型
        defaultRedisScript.setResultType(Boolean.class);
        //defaultRedisScript.setScriptText();
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("Pay-right-1.lua")));
        return defaultRedisScript;
    }

    /**
     * 黑客接口防刷Lua配置
     */
    @Bean
    public DefaultRedisScript<Boolean> apiBrushProtection() {
        DefaultRedisScript<Boolean> defaultRedisScript = new DefaultRedisScript<>();
        //设置返回类型
        defaultRedisScript.setResultType(Boolean.class);
        //defaultRedisScript.setScriptText();
        defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("Api-brush-protection.lua")));
        return defaultRedisScript;
    }
}
