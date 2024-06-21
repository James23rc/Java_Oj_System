package com.yupi.oj.judge;

import cn.hutool.json.JSONUtil;
import com.yupi.oj.common.ErrorCode;
import com.yupi.oj.exception.BusinessException;
import com.yupi.oj.judge.codesandbox.CodeSandbox;
import com.yupi.oj.judge.codesandbox.CodeSandboxFactory;
import com.yupi.oj.judge.codesandbox.CodeSandboxProxy;
import com.yupi.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.oj.judge.codesandbox.model.ExecuteCodeResponse;
import com.yupi.oj.judge.strategy.JudgeContext;
import com.yupi.oj.model.dto.question.JudgeCase;
import com.yupi.oj.model.dto.questionsubmit.JudgeInfo;
import com.yupi.oj.model.entity.Question;
import com.yupi.oj.model.entity.QuestionSubmit;
import com.yupi.oj.model.enums.QuestionSubmitStatusEnum;
import com.yupi.oj.service.QuestionService;
import com.yupi.oj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author：rancheng
 * @name：JudgeServiceImpl
 * @Date：2024/6/14 20:25
 */
@Service
public class JudgeServiceImpl implements JudgeService {
    @Value("${codesandbox.type:example}")//读取配置文件codesandbox.type，如果无值，赋值为example
    private String type;
    @Resource
    private QuestionService questionService;
    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private JudgeManager judgeManager;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        //1.获得题目的提交id，提交信息（包含代码，编程语言等） questionSubmitService,获取到对应的题目（questionService）
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        //进行提交的检验
        if(questionSubmit == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"提交信息不存在");
        }
        Long id = questionSubmit.getQuestionId();
        Question question = questionService.getById(id);
        if(question == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"题目不存在");
        }
        //2。更改题目提交的状态，避免用户多次提交，沙箱多次判题，当题目提交状态状态不为等待中（即在判题中），则不用重复执行
        Integer status = questionSubmit.getStatus();
        if(!status.equals(QuestionSubmitStatusEnum.WAITTING.getValue())){
            //题目提交时 初始状态为 Waiting,如果不为waiting，说明有请求正在判题
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"题目正在等待状态中");
        }
        //更改数据库中，题目提交信息，更新状态
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if(!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目状态更新错误");
        }
        //3.调用代码沙箱，获取到执行结果
        CodeSandbox codeSandbox = CodeSandboxFactory.getCodeSandbox(type);
        CodeSandboxProxy codeSandboxProxy = new CodeSandboxProxy(codeSandbox);//从题目提交中获得
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        //4 测试用例从题目中获得
        String judgeCaseStr = question.getJudgeCase();
        //每个JudgeCase包含两个属性 一个输入用例String 一个输出用例String
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        //需要将输入用例传入ExecuteCodeRequest中交由代码沙箱执行
        //将judgeCase中的输入用例转为List
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandboxProxy.executeCode(executeCodeRequest);
        //4.根据沙箱的执行结果，设置题目的判题状态和信息
        //将判题策略，用策略模式封装。我们默认用输入输出用例的方式进行判题，并对内存和时间限制做判题
        //但是如果出现了额外的情况，例如特殊的判题的时候，用户可以自己定义输入输出的时候，我们就要切换判题策略
        //或者不同语言情况下，我们也需要根据不同语言 例如编译运行的时间不同 进行不同的判题策略，java的空间/时间判定，与c++的空间时间判定不同
        //因此为了减少大量的if else，我们可以采用策略模式
//        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
//        默认写法，如果选择某种判题策略的过程比较复杂，都写在调用判题服务的代码中，代码会有很多 if else
//        if("java".equals(language)){
//            judgeStrategy = new JavaLanguageJudgeStrategy();
//        }
        List<String> outputList = executeCodeResponse.getOutputList();
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setQuestion(question);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestionSubmit(questionSubmit);

        JudgeInfo judgeInfoResponse = judgeManager.doJudge(judgeContext);
        //6.修改数据库中的判题结果，设置判题信息，judgeInfo,设置判题状态
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmit.getId());
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCESSED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfoResponse));
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if(!update){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"题目状态更新错误");
        }
        //返回题目，从数据库中返回此次提交的结果
        QuestionSubmit questionSubmitResult = questionSubmitService.getById(questionSubmitId);

        return questionSubmitResult;
    }
}
