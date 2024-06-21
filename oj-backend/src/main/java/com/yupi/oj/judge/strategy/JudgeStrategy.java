package com.yupi.oj.judge.strategy;

import com.yupi.oj.model.dto.question.JudgeConfig;
import com.yupi.oj.model.dto.questionsubmit.JudgeInfo;

/**
 * @Author：rancheng
 * @name：JudgeStrategy
 * @Date：2024/6/14 21:44
 * 判题策略
 */
public interface JudgeStrategy {
    /**
     * 执行判题
     * @param judgeContext 判题上下文，含有ExecuteCodeRequest和判题用例等信息
     * @return
     */
    public JudgeInfo doJudge(JudgeContext judgeContext);
}
