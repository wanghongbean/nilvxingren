package com.nlxr.spring;

import com.nlxr.spring.annotation.CallLog;
import org.springframework.stereotype.Service;

/**
 * @Author labu
 * @Date 2020/9/15
 * @Description
 */
@Service
public class BizService {

    @CallLog("bizService.query")
    public String query(String seq,int type,boolean flag){
        System.out.println("---- biz service start ---");
        return "biz data";
    }
}
