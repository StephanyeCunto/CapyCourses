package com.service;

import com.model.elements.Course.Question;
import java.util.Map;
import java.util.ArrayList;





public class QuestionService {
    @SuppressWarnings("unchecked")
    public static Question createFromMap(Map<String, Object> questionData) {
        Question question = new Question();
        
        question.setText((String) questionData.get("questionText0"));
        question.setScore((String) questionData.get("questionScore0"));
        question.setType((String) questionData.get("type0"));
        question.setNumber((String) questionData.get("questionNumber0"));
        
        // Tratando as respostas
        Map<String, Object> responses = (Map<String, Object>) questionData.get("response0");
        if (responses != null) {
            question.setAnswers(new ArrayList<>(responses.values()).toString());
        }
        
        return question;
    }
} 