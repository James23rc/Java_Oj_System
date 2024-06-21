package com.yupi.oj.judge;

import com.yupi.oj.judge.strategy.DefaultJudgeStrategy;
import com.yupi.oj.judge.strategy.JavaLanguageJudgeStrategy;
import com.yupi.oj.judge.strategy.JudgeContext;
import com.yupi.oj.judge.strategy.JudgeStrategy;
import com.yupi.oj.model.dto.questionsubmit.JudgeInfo;
import com.yupi.oj.model.entity.QuestionSubmit;
import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * @Author：rancheng
 * @name：JudgeStrategyManage 判题策略管理，简化调用
 * @Date：2024/6/15 17:54
 */
@Data
@Service
public class JudgeManager {
    /**
     * 执行判题
     *
     * @param judgeContext 判题上下文，含有ExecuteCodeRequest和判题用例等信息
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
