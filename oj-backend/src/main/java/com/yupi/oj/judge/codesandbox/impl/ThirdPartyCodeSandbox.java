package com.yupi.oj.judge.codesandbox.impl;

import com.yupi.oj.judge.codesandbox.CodeSandbox;
import com.yupi.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.oj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * @Author：rancheng
 * @name：ExampleCodeSandbox:第三方代码沙箱：调用第三方的代码沙箱
 * @Date：2024/6/13 20:13
 */
public class ThirdPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("ThirdParty sandbox");
        return null;
    }
}
