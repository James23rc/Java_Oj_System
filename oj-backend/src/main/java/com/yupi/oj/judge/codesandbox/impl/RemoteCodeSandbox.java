package com.yupi.oj.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yupi.oj.common.ErrorCode;
import com.yupi.oj.exception.BusinessException;
import com.yupi.oj.judge.codesandbox.CodeSandbox;
import com.yupi.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.oj.judge.codesandbox.model.ExecuteCodeResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author：rancheng
 * @name：RemoteCodeSandbox:远程代码沙箱(实际调用接口的沙箱),代码沙箱的接口暴露出来了
 * @Date：2024/6/13 20:13
 */
public class RemoteCodeSandbox implements CodeSandbox {
    //定义鉴权请求头和密钥
    public static final String AUTH_REQUEST_HEADER = "auth";
    public static final String AUTH_REQUEST_SECRET = "secretKey";
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        String url = "http://localhost:8080/executeCode";
        String jsonRequest = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER,AUTH_REQUEST_SECRET)
                .body(jsonRequest)
                .execute()
                .body();
        if(StringUtils.isBlank(responseStr)){
            throw  new BusinessException(ErrorCode.API_REQUEST_ERROR,"executeCode remoteSandBox error");
        }
        return JSONUtil.toBean(responseStr,ExecuteCodeResponse.class);
    }
}
