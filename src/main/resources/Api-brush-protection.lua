-- 要求同一个IP10秒内不能访问超过20次 超过则就加入黑名单三天时间
-- 计数功能
-- return false 黑名单
-- return ture 不在黑名单
local key_ip = KEYS[1] -- api-ip 访问计数KEY
local key_black_name = KEYS[2] --api-black_ip 黑名单的KEY
local argv_ttl = ARGV[1] --10S
local argv_max_count = ARGV[2] --20 最大访问次数
local argv_black_ttl = ARGV[3] --3天 24*3600*3=259200
local default_black_value = 1 --黑名单的值

--判断是不是在黑名单里
local black_value = redis.call('GET', key_black_name)
-- 不在黑名单
if not black_value then
    --局部变量 visitTime pay-1
    local visitTime = redis.call('INCR', key_ip)
    if visitTime == 1 then
        -- 如果是第一次访问 设置这个key的超时时间
        redis.call('EXPIRE', key_ip, tonumber(argv_ttl))
    end
    --如果接口访问超过最大值
    if visitTime > tonumber(argv_max_count) then
        --设置黑名单
        redis.call('SET', key_black_name, default_black_value, 'EX', tonumber(argv_black_ttl))
        -- 此处为保护机制
        local ttl = redis.call('TTL', KEYS[1])
        if tonumber(ttl) == -1 then
            redis.call('DEL', KEYS[1])
        end
        return false --返回false IP被拉黑
    else
        return true
    end
else
    return false
end