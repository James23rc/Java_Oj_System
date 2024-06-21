package com.yupi.oj.judge.strategy;

import com.yupi.oj.model.dto.question.JudgeCase;
import com.yupi.oj.model.dto.questionsubmit.JudgeInfo;
import com.yupi.oj.model.entity.Question;
import com.yupi.oj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * @Author：rancheng
 * @name：JudgeContext 上下文，用于策略模式中，用于定义在策略中传递的参数
 * @Date：2024/6/15 16:44
 */
@Data
public class JudgeContext {
    private JudgeInfo judgeInfo;
    private List<String> inputList;
    private List<String> outputList;
    private Question question;
    private QuestionSubmit questionSubmit;

    List<JudgeCase> judgeCaseList;
}
