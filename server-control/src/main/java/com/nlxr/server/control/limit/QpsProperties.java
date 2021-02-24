package com.nlxr.server.control.limit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author labu
 * @Date 2020/10/26
 * @Description
 */
//@Component
public class QpsProperties {
    @Value("${qps}")
    private int qps;

    public int getQps() {
        return qps;
    }

    public void setQps(int qps) {
        this.qps = qps;
    }
}
