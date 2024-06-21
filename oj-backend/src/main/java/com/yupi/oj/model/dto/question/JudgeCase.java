package com.yupi.oj.model.dto.question;

import lombok.Data;

/**
 * 题⽬⽤例
 */
@Data
public class JudgeCase {
    /**
     * 输⼊⽤例
     */
    private String input;
    /**
     * 输出⽤例
     */
    private String output;
}