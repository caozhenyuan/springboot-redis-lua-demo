-- 支付宝API限流 10TPS
local pay_api_key = KEYS[1] -- pay-1
local ttl = tonumber(ARGV[1]) -- 过期时间1秒钟
local max_value = tonumber(ARGV[2]) --最大访问量10
local current_value = redis.call('GET', pay_api_key)
-- 值不存在代表第一次访问，则set值然后放行
if not current_value then
    redis.call('SET', pay_api_key, 1, 'NX', 'EX', ttl)
    return true;
else
    -- 如果有值，则拿到值加1
    local current_value_add = tonumber(current_value) + 1
    -- 获取当前key的过期时间单位毫秒
    local current_value_number_pttl = redis.call("PTTL", pay_api_key)
    -- 如果当前key没有过期
    if current_value_number_pttl > 0 then
        -- 则把新值set到key中，并把过期的毫秒值也set回去，类似于修改
        redis.call('SET', pay_api_key, current_value_add, 'XX', 'PX', current_value_number_pttl)
    else
        -- key不存在或者过期时间为 0 ， -1 ，-2则删除
        redis.call('DEL', pay_api_key)
    end
    -- 判断值是否大于最大访问量
    if tonumber(current_value_add) >= max_value then
        return false
    else
        return true
    end
end