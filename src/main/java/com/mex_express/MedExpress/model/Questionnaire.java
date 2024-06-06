package com.mex_express.MedExpress.model;

import java.util.Set;

public record Questionnaire(
        String id,
        Set<Question> questions
) implements QuestionnaireInterface {
}

