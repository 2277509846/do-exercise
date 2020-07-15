package com.fjp.controller;

import com.fjp.entity.Question;
import com.fjp.service.QuestionService;
import com.fjp.view.QuestionFrame;

import java.util.List;

public class QuestionController extends QuestionFrame {

    private QuestionService questionService = new QuestionService();

    //获取问题列表
    @Override
    protected List<Question> questionList() {
        return questionService.questionList();
    }
}
