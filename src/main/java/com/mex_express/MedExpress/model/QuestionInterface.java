package com.mex_express.MedExpress.model;

sealed interface QuestionInterface permits Question, QuestionAnswered, QuestionExpected, QuestionResult, QuestionnaireResult {
    String id();
}
