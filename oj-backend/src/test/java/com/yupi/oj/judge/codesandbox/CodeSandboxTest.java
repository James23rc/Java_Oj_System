package com.yupi.oj.judge.codesandbox;

import com.yupi.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.oj.judge.codesandbox.model.ExecuteCodeResponse;
import com.yupi.oj.model.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * @Author：rancheng
 * @name：CodeSandboxTest
 * @Date：2024/6/13 20:44
 */
@SpringBootTest
class CodeSandboxTest {
    @Value("${codesandbox.type}")
    private String type;

    @Test
    void test(){
//        System.out.println(type);
        //测试代码沙箱工厂
        CodeSandbox codeSandbox = CodeSandboxFactory.getCodeSandbox(type);
        String code = "class Main{\n" +
                "    public static void main(String[] args) {\n" +
                "        Integer a = Integer.parseInt(args[0]);\n" +
                "        Integer b = Integer.parseInt(args[1]);\n" +
                "        System.out.println(\"两数之和结果:\"+(a+b));\n" +
                "    }\n" +
                "}";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputs = Arrays.asList("1 2","3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputs)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        System.out.println(executeCodeResponse);
        Assertions.assertNotNull(executeCodeResponse);
    }

    @Test
    void executeCodeSandboxByProxy(){
//        System.out.println(type);
        //测试代码沙箱工厂
        CodeSandbox codeSandbox = CodeSandboxFactory.getCodeSandbox(type);
        String code = "int main() {return 0;}";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputs = Arrays.asList("1 2","3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputs)
                .build();
        CodeSandboxProxy codeSandboxProxy = new CodeSandboxProxy(codeSandbox);
        ExecuteCodeResponse executeCodeResponse = codeSandboxProxy.executeCode(executeCodeRequest);
    }


}