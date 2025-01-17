package com.service;

import com.model.elements.Course.Question;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;





public class QuestionService {
    @SuppressWarnings("unchecked")
    public static Question createFromMap(Map<String, Object> questionData) {
        Question question = new Question();
        
        question.setText((String) questionData.get("questionText"));
        question.setScore((String) questionData.get("questionScore"));
        question.setType((String) questionData.get("type"));
        question.setNumber(String.valueOf(questionData.get("questionNumber")));
        
        // Tratando as respostas de forma mais robusta
        Object responsesObj = questionData.get("responses");
        if (responsesObj instanceof List) {
            List<Map<String, Object>> responsesList = (List<Map<String, Object>>) responsesObj;
            StringBuilder answers = new StringBuilder();
            StringBuilder correctAnswers = new StringBuilder();
            
            for (Map<String, Object> response : responsesList) {
                String text = (String) response.get("text");
                Boolean isCorrect = (Boolean) response.get("isCorrect");
                
                if (text != null) {
                    if (answers.length() > 0) answers.append("|");
                    answers.append(text);
                    
                    if (Boolean.TRUE.equals(isCorrect)) {
                        if (correctAnswers.length() > 0) correctAnswers.append("|");
                        correctAnswers.append(text);
                    }
                }
            }
            
            question.setAnswers(answers.toString());
            question.setCorrectAnswers(correctAnswers.toString());
        }
        
        return question;
    }
} 