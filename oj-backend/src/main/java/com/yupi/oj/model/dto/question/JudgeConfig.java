package com.yupi.oj.model.dto.question;

import lombok.Data;

/**
 * @Author：rancheng
 * @name：JudgeConfig
 * @Date：2024/6/11 15:56
 */
@Data
public class JudgeConfig  {
    /**
     * 时间限制ms
     */
    private Long timeLimit;
    /**
     * 内存限制kb
     */
    private Long memoryLimit;
    /**
     * 堆栈限制kb
     */
    private Long stackLimit;
}
