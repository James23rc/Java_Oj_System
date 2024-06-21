package com.yupi.oj.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.yupi.oj.model.dto.question.JudgeCase;
import com.yupi.oj.model.dto.question.JudgeConfig;
import com.yupi.oj.model.dto.questionsubmit.JudgeInfo;
import com.yupi.oj.model.entity.Question;
import com.yupi.oj.model.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * @Author：rancheng
 * @name：DefaultJudgeStrategy
 * @Date：2024/6/15 16:42
 */
public class JavaLanguageJudgeStrategy implements JudgeStrategy {
    /**
     * 执行判题，
     * @param judgeContext 判题上下文，从代码沙箱中的executeResponse获得判题
     * @return
     */
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        List<String> inputList = judgeContext.getInputList();
        List<String> outputList = judgeContext.getOutputList();
        Question question = judgeContext.getQuestion();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        JudgeInfoMessageEnum judgeInfoMessageEnum;
        // 题目限制
        //从题目表中获得判题配置限制
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);

        //设置判题响应
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        Long runningMemory = judgeInfo.getMemory();
        Long runningTime = judgeInfo.getTime();
        judgeInfoResponse.setMemory(runningMemory);
        judgeInfoResponse.setTime(runningTime);
        //1)先判断输出用例的数量和输入用例的数量是否相同
        if (outputList.size() != inputList.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        //2)依次判断每一项输出和预期输出是否相等
        for (int i = 0; i < judgeCaseList.size(); i++) {
            if (outputList.get(i).equals(judgeCaseList.get(i).getInput())) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
                return judgeInfoResponse;
            }
        }
        //判断题目限制
        Long timeLimit = judgeConfig.getTimeLimit();
        Long memoryLimit = judgeConfig.getMemoryLimit();

        if (runningMemory > memoryLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;
        }
        //假设 JAVA程序需要额外执行10秒钟
        long JAVA_PROGRRAM_TIME_COST = 10000l;
        if ((runningTime - JAVA_PROGRRAM_TIME_COST) > timeLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
            return judgeInfoResponse;

        }
        //判题成功
        judgeInfoMessageEnum = JudgeInfoMessageEnum.ACCEPTED;
        judgeInfoResponse.setMessage(judgeInfoMessageEnum.getValue());
        return judgeInfoResponse;
    }
}
