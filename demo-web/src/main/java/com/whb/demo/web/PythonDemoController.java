package com.whb.demo.web;

import com.whb.demo.validator.dto.RspDTO;
import com.whb.demo.validator.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author labu
 * @Date 2020/5/14
 * @Description
 */
@RestController
public class PythonDemoController {
    private static final Logger LOG = LoggerFactory.getLogger(PythonDemoController.class);

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public RspDTO<String> saveUser(String firstname,String lastname){
        LOG.info("post param firstname:{},lastname:{}",firstname,lastname);
        RspDTO<String> dto = new RspDTO<>();
        dto.setT("hello python request post : "+firstname+" - "+lastname);
        return dto;
    }
}
