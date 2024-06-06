package com.mex_express.MedExpress.model;

public sealed interface QuestionnaireInterface permits Questionnaire, QuestionnaireAnswered, QuestionnaireExpected {
    String id();
}
