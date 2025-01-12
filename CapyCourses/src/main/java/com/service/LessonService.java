package com.service;

import com.model.elements.Course.Lessons;
import java.util.Map;

public class LessonService {
    public static Lessons createFromMap(Map<String, Object> lessonData) {
        if (lessonData == null) return null;
        
        Lessons lesson = new Lessons();
        
        // Verifica e converte o número da lição
        String lessonNumberStr = (String) lessonData.get("lessonNumber");
        if (lessonNumberStr != null && !lessonNumberStr.isEmpty()) {
            lesson.setNumberOfLesson(Integer.parseInt(lessonNumberStr));
        }
        
        lesson.setTitle((String) lessonData.get("lessonTitle"));
        lesson.setVideoLink((String) lessonData.get("videoLink"));
        lesson.setDescription((String) lessonData.get("details"));
        lesson.setMaterials((String) lessonData.get("materials"));
        lesson.setDuration((String) lessonData.get("duration"));
        
        return lesson;
    }
} 