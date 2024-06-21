package com.yupi.oj.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 题目
 * @TableName question
 */
@TableName(value ="question")
@Data
public class Question implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
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
    private String tags;

    /**
     * 题目答案
     */
    private String answer;

    /**
     * 题目提交数量
     */
    private Integer submitNum;

    /**
     * 题目通过数量
     */
    private Integer acceptNum;

    /**
     * 判题用例 (json数组，一个json对象存放一组测试用例 输入/输出)
     */
    private String judgeCase;

    /**
     * 判题用例 (json对象 时间空间限制)
     */
    private String judgeConfig;

    /**
     * 点赞数
     */
    private Integer thumbNum;

    /**
     * 收藏数
     */
    private Integer favourNum;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}