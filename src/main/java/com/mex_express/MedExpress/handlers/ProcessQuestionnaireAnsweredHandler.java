package com.mex_express.MedExpress.handlers;

import com.mex_express.MedExpress.model.*;
import com.mex_express.MedExpress.services.QuestionnaireService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@FunctionalInterface
interface ProcessQuestionnaireAnsweredHandlerInterface {
    QuestionnaireResult invoke(QuestionnaireAnswered questionnaireAnswered);
}

@Component
public class ProcessQuestionnaireAnsweredHandler implements ProcessQuestionnaireAnsweredHandlerInterface {

    private final QuestionnaireService questionnaireService;

    public ProcessQuestionnaireAnsweredHandler(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    @Override
    public QuestionnaireResult invoke(QuestionnaireAnswered questionnaireAnswered) {
        QuestionnaireExpected expectedQuestionnaire = this.questionnaireService.get(questionnaireAnswered.id(), QuestionnaireExpected.class);//getQuestionnaireAnswered(questionnaireAnswered.id());

        HashMap<String, QuestionExpected> expectedHashMap = new HashMap<>();

        for (QuestionExpected entry : expectedQuestionnaire.questions()) {
            expectedHashMap.put(entry.id(), entry);
        }

        Set<QuestionResult> resultQuestions = new HashSet<>();

        for (QuestionAnswered entry : questionnaireAnswered.questions()) {
            QuestionExpected expected = expectedHashMap.get(entry.id());

            if (expected.expected() != entry.answer()) {
                resultQuestions.add(new QuestionResult(entry.id(), expected.reason()));
            }
        }

        return new QuestionnaireResult(
                questionnaireAnswered.id(),
                resultQuestions.isEmpty(),
                resultQuestions
        );
    }
}