package com.whb.demo.web;

import com.whb.demo.validator.dto.RspDTO;
import com.whb.demo.validator.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author labu
 * @Date 2020/5/14
 * @Description
 */
@RestController
public class DemoController {
    private static final Logger LOG = LoggerFactory.getLogger(DemoController.class);

    @RequestMapping("/user")
    public RspDTO<Boolean> saveUser(@RequestBody @Validated UserDTO userDTO){
        RspDTO<Boolean> rsp = new RspDTO();
        throw new IllegalArgumentException();
//        LOG.info("saved user success.");
//        rsp.setT(true);
//        return rsp;
    }
}
