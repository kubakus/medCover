package com.mex_express.MedExpress.handlers;

import com.mex_express.MedExpress.model.Questionnaire;
import com.mex_express.MedExpress.services.QuestionnaireService;
import org.springframework.stereotype.Component;

@FunctionalInterface
interface RetrieveQuestionnaireHandlerInterface {
    Questionnaire invoke(String id);
}

@Component
public class RetrieveQuestionnaireHandler implements RetrieveQuestionnaireHandlerInterface {
    private final QuestionnaireService questionnaireService;

    public RetrieveQuestionnaireHandler(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    @Override
    public Questionnaire invoke(String id) {
        return questionnaireService.get(id, Questionnaire.class);
    }
}
