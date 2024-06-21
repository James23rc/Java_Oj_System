package com.yupi.oj.judge;

import com.yupi.oj.model.entity.QuestionSubmit;
import com.yupi.oj.model.vo.QuestionSubmitVO;

/**
 * @Author：rancheng
 * @name：JudgeService 叛徒服务
 * @Date：2024/6/14 15:20
 */
public interface JudgeService {

    /**
     * 执行判题
     * @param questionSubmitId
     * @return
     */
    public QuestionSubmit doJudge(long questionSubmitId);
}
