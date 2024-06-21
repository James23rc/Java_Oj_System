package com.yupi.oj.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.oj.model.entity.Post;
import com.yupi.oj.model.entity.PostFavour;
import com.yupi.oj.model.entity.Question;
import com.yupi.oj.model.entity.QuestionSubmit;
import org.apache.ibatis.annotations.Param;

/**
 * 帖子收藏数据库操作
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
public interface PostFavourMapper extends BaseMapper<PostFavour> {

    /**
     * 分页查询收藏帖子列表
     *
     * @param page
     * @param queryWrapper
     * @param favourUserId
     * @return
     */
    Page<Post> listFavourPostByPage(IPage<Post> page, @Param(Constants.WRAPPER) Wrapper<Post> queryWrapper,
            long favourUserId);

    /**
    * @author Rancheng
    * @description 针对表【question_submit(题目提交)】的数据库操作Mapper
    * @createDate 2024-06-11 00:12:17
    * @Entity com.yupi.oj.model.entity.QuestionSubmit
    */
    interface QuestionSubmitMapper extends BaseMapper<QuestionSubmit> {

    }

    /**
    * @author Rancheng
    * @description 针对表【question(题目)】的数据库操作Mapper
    * @createDate 2024-06-11 00:12:17
    * @Entity com.yupi.oj.model.entity.Question
    */
    interface QuestionMapper extends BaseMapper<Question> {

    }
}



