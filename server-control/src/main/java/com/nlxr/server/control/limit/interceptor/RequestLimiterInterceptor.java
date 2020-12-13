package com.nlxr.server.control.limit.interceptor;

import com.google.common.util.concurrent.RateLimiter;
import com.nlxr.server.control.limit.annotation.RequestLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RequestLimiterInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(RequestLimiterInterceptor.class);

    /**
     * 不同的方法存放不同的令牌桶
     */
    private final Map<String, RateLimiter> rateLimiterMap = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                RequestLimiter rateLimit = handlerMethod.getMethodAnnotation(RequestLimiter.class);
                //判断是否有注解
                if (rateLimit != null) {
                    // 获取请求url
                    String url = request.getRequestURI();
                    RateLimiter rateLimiter;
                    // 判断map集合中是否有创建好的令牌桶
                    if (!rateLimiterMap.containsKey(url)) {
                        // 创建令牌桶,以n r/s往桶中放入令牌
                        rateLimiter = RateLimiter.create(rateLimit.QPS());
                        rateLimiterMap.put(url, rateLimiter);
                    }
                    rateLimiter = rateLimiterMap.get(url);
                    // 获取令牌
                    boolean acquire = rateLimiter.tryAcquire(rateLimit.timeout(), rateLimit.timeunit());
                    if (acquire) {
                        //获取令牌成功
                        LOG.info("token in hand...");
                        return true;
                    } else {
                        LOG.warn("请求被限流,url:{}", request.getServletPath());
                        throw new Exception("服务器繁忙");
                    }
                }
            }
            return true;
        } catch (Exception e) {
            if ("服务器繁忙".equals(e.getMessage())){
                throw e;
            }
            LOG.error(e.getMessage(),e);
            return false;
        }
    }

}
