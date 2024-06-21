package com.yupi.oj.judge.codesandbox;

import com.yupi.oj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.yupi.oj.judge.codesandbox.impl.RemoteCodeSandbox;
import com.yupi.oj.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * @Author：rancheng
 * @name：CodeSandoxFactory： 静态代码沙箱工厂
 * @Date：2024/6/13 20:36
 */
public class CodeSandboxFactory {
    public static CodeSandbox getCodeSandbox(String type){
        switch (type){
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case  "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }
}
