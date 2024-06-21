package com.yupi.oj.model.dto.question;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 更新请求
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class QuestionUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 标题:
     */
    private String title;

    /**
     * 内容：题目介绍，输入输出提示，描述，具体详情
     */
    private String content;

    /**
     * 标签列表（json 数组/难易度/题目类型[栈队列树]）
     */
    private List<String> tags;

    /**
     * 题目答案
     */
    private String answer;

    /**
     * 判题用例 (json数组，一个json对象存放一组测试用例 输入/输出)
     */
    private List<JudgeCase> judgeCase;

    /**
     * 判题配置 (json对象 时间空间限制)
     */
    private JudgeConfig judgeConfig;


    private static final long serialVersionUID = 1L;
}