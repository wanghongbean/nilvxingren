package com.nlxr.server.control.limit.aop;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.RateLimiter;
import com.nlxr.server.control.ApiException;
import com.nlxr.server.control.limit.annotation.RequestLimiter;
import jdk.nashorn.internal.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author labu
 * @Date 2020/10/26
 * @Description
 */
public class RequestLimiterAdvice implements MethodBeforeAdvice {
    private static final Logger LOG = LoggerFactory.getLogger(RequestLimiterAdvice.class);
    /**
     * 不同的方法存放不同的令牌桶
     */
    private final Map<String, RateLimiter> rateLimiterMap = new ConcurrentHashMap<>();
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        if (method != null && method.isAnnotationPresent(RequestLimiter.class)) {
            RequestLimiter rateLimit = method.getAnnotation(RequestLimiter.class);
            // 获取请求url
            String name = method.getName();
            String className = method.getDeclaringClass().toString();
            String key= className+name;
            RateLimiter rateLimiter;
            // 判断map集合中是否有创建好的令牌桶
            if (!rateLimiterMap.containsKey(key)) {
                // 创建令牌桶,以n r/s往桶中放入令牌
                rateLimiter = RateLimiter.create(rateLimit.QPS());
                rateLimiterMap.put(key, rateLimiter);
            }
            rateLimiter = rateLimiterMap.get(key);
            // 获取令牌
            boolean acquire = rateLimiter.tryAcquire(rateLimit.timeout(), rateLimit.timeunit());
            if (acquire) {
                //获取令牌成功
                LOG.info("token in hand...");
            } else {
//                LOG.warn("请求被限流,methodName:{},params:{}", method.getName(), args);
                throw new ApiException(rateLimit.msg());
            }
        }
    }
}
