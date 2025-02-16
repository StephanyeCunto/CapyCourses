package com.service;

import com.model.elements.Course.Questionaire;
import java.util.Map;

public class QuestionaireService {
  public static Questionaire createFromMap(Map<String, Object> questionaireData) {
    Questionaire questionaire = new Questionaire();
    questionaire.setTitle((String) questionaireData.get("questionaireTitle0"));
    questionaire.setNumber((String) questionaireData.get("questionaireNumber0"));
    questionaire.setScore((String) questionaireData.get("questionaireScore0"));
    questionaire.setDescription((String) questionaireData.get("questionaireDescription0"));

    return questionaire;
  }
}
