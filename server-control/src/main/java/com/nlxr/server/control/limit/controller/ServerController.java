package com.nlxr.server.control.limit.controller;

import com.nlxr.server.control.limit.annotation.RequestLimiter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

/**
 * @Author labu
 * @Date 2020/10/18
 * @Description
 */
@Controller
public class ServerController {
    @RequestLimiter(QPS = 1D, timeout = 200, timeunit = TimeUnit.MILLISECONDS,msg = "服务器繁忙,请稍后再试")
    @GetMapping("/test")
    @ResponseBody
    public String test(){
        return "hello";
    }
}
