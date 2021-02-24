package com.nlxr.server.control.limit;

import com.alibaba.fastjson.JSON;
import com.nlxr.server.control.ApiException;
import com.nlxr.server.control.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

/**
 * @Author labu
 * @Date 2020/10/26
 * @Description
 */
public class ExceptionHandler extends DefaultHandlerExceptionResolver implements HandlerExceptionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);
    private static final Logger INFO_LOGGER = LoggerFactory.getLogger("ApiLog");

    private static final String DEFAULT_API_CODE = "000001";
    private static final String DEFAULT_API_MESSAGE = "UNKNOWN";
    private static final String DEFAULT_ARGS_INVALID_CODE = "000002";
    private static final String DEFAULT_ARGS_INVALID_MESSAGE = "Param Invalid [%s]";

    private String apiCode = DEFAULT_API_CODE;
    private String apiMessage = DEFAULT_API_MESSAGE;
    private String argsInvalidCode = DEFAULT_ARGS_INVALID_CODE;
    private String argsInvalidMessage = DEFAULT_ARGS_INVALID_MESSAGE;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
        Result<Void> r = null;
        if (ex instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException missEx = (MissingServletRequestParameterException) ex;
            r = new Result<Void>(false).setApiCodeMessage(argsInvalidCode, format(argsInvalidMessage, missEx.getParameterName()));
        } else if (ex instanceof ApiException) {
            ApiException apiEx = (ApiException) ex;
            r = new Result<Void>(false).setApiCodeMessage(apiEx.getApiCode(), apiEx.getApiMessage());
        } else {
            r = new Result<Void>(false).setApiCodeMessage(apiCode, apiMessage);
        }
        Map<String, String> resp = new HashMap<String, String>();
        resp.put("requestId", r.getRequestId());
        resp.put("apiCode", r.getApiCode());
        resp.put("apiMessage", r.getApiMessage());
        resp.put("internalMessage", ex.getMessage());
        String msg = format("Api:%s Req:%s Resp:%s", request.getRequestURI(), getParams(request), JSON.toJSONString(resp));

        // API日志
        INFO_LOGGER.error(msg);
        // 系统日志
        LOGGER.error(msg, ex);

        try {
            response.getWriter().write(JSON.toJSONString(r));
        } catch (Exception e) {
            LOGGER.error("failed to write response body.", e);
        }
        return new ModelAndView();
    }

    @SuppressWarnings("unchecked")
    protected String getParams(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        for (Map.Entry<String, String[]> kv : ((Map<String, String[]>) request.getParameterMap()).entrySet()) {
            if (kv.getValue() != null && kv.getValue().length == 1) {
                params.put(kv.getKey(), kv.getValue()[0]);
            } else {
                params.put(kv.getKey(), kv.getValue());
            }
        }
        return JSON.toJSONString(params);
    }

    public void setApiCode(String apiCode) {
        this.apiCode = apiCode;
    }

    public void setApiMessage(String apiMessage) {
        this.apiMessage = apiMessage;
    }

    public void setArgsInvalidCode(String argsInvalidCode) {
        this.argsInvalidCode = argsInvalidCode;
    }

    public void setArgsInvalidMessage(String argsInvalidMessage) {
        this.argsInvalidMessage = argsInvalidMessage;
    }

}
