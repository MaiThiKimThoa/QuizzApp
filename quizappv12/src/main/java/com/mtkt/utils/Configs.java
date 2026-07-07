/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mtkt.utils;

import com.mtkt.services.CategoryServices;
import com.mtkt.services.LevelServices;
import com.mtkt.services.question.QuestionServices;
import com.mtkt.services.question.UpdateQuestionServices;

/**
 *
 * @author mai thoa
 */
public class Configs {
    public static final CategoryServices cateService = new CategoryServices();
    public static final QuestionServices questionService = new QuestionServices();
    public static final LevelServices lvlService = new LevelServices();
    public static final UpdateQuestionServices uQuestionService = new UpdateQuestionServices();
}
