package com.mex_express.MedExpress.controllers;

import com.mex_express.MedExpress.handlers.ProcessQuestionnaireAnsweredHandler;
import com.mex_express.MedExpress.handlers.RetrieveQuestionnaireHandler;
import com.mex_express.MedExpress.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class QuestionnaireControllerTest {

    @Mock
    private ProcessQuestionnaireAnsweredHandler processQuestionnaireAnsweredHandler;

    @Mock
    private RetrieveQuestionnaireHandler retrieveQuestionnaireHandler;

    @InjectMocks
    private QuestionnaireController questionnaireController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetQuestionnaire() {
        // Given
        String id = "test";
        Questionnaire expectedQuestionnaire = new Questionnaire(id, Set.of());

        when(retrieveQuestionnaireHandler.invoke(anyString())).thenReturn(expectedQuestionnaire);

        // When
        ResponseEntity<Questionnaire> response = questionnaireController.getQuestionnaire(id);

        // Then
        assertEquals(ResponseEntity.ok(expectedQuestionnaire), response);
    }

    @Test
    void testProcessQuestionnaire() {
        // Given
        String id = "test";
        Set<QuestionAnswered> answeredQuestions = Set.of(
                new QuestionAnswered("q1", "true", true),
                new QuestionAnswered("q2", "false", false)
        );
        QuestionnaireAnswered questionnaireAnswered = new QuestionnaireAnswered(id, answeredQuestions);

        Set<QuestionResult> resultQuestions = Set.of(
                new QuestionResult("q1", "Expected Answer: true")
        );
        QuestionnaireResult expectedQuestionnaireResult = new QuestionnaireResult(id, false, resultQuestions);

        when(processQuestionnaireAnsweredHandler.invoke(any(QuestionnaireAnswered.class))).thenReturn(expectedQuestionnaireResult);

        // When
        ResponseEntity<QuestionnaireResult> response = questionnaireController.processQuestionnaire(questionnaireAnswered);

        // Then
        assertEquals(ResponseEntity.ok(expectedQuestionnaireResult), response);
    }
}
