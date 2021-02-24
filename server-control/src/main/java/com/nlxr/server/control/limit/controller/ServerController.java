package com.nlxr.server.control.limit.controller;

import com.nlxr.server.control.limit.QpsProperties;
import com.nlxr.server.control.limit.annotation.RequestLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author labu
 * @Date 2020/10/18
 * @Description
 */
@RestController
public class ServerController {

    @RequestLimiter(QPS = 1, timeout = 200, timeunit = TimeUnit.MILLISECONDS, msg = "服务器繁忙,请稍后再试")
    @GetMapping("/test")
    public String test(String id) {
        try {
            return "hello";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
