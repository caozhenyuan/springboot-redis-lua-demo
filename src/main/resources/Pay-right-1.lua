-- 局部变量 visitTime pay-1
local visitTime = redis.call('INCR', KEYS[1])
if visitTime == 1 then
    -- 如果是第一次访问 设置这个key的超时时间
    redis.call('EXPIRE', KEYS[1], tonumber(ARGV[1]))
end
-- 如果接口访问超过最大值
if visitTime > tonumber(ARGV[2]) then
    -- 增加保护机制,防止有客户端把key变为永久
    local key_ttl = redis.call('TTL', KEYS[1])
    if tonumber(key_ttl) == -1 then
        redis.call('DEL', KEYS[1])
    end

    return false -- 返回false 接口被限流
else
    return true -- 返回true 接口放行
end