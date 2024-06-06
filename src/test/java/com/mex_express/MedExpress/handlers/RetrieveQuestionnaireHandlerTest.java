package com.mex_express.MedExpress.handlers;

import com.mex_express.MedExpress.model.Questionnaire;
import com.mex_express.MedExpress.services.QuestionnaireService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class RetrieveQuestionnaireHandlerTest {

    @Mock
    private QuestionnaireService questionnaireService;

    @InjectMocks
    private RetrieveQuestionnaireHandler retrieveQuestionnaireHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInvoke_Success() {
        // Given
        String id = "test";
        Questionnaire expectedQuestionnaire = new Questionnaire("id", new HashSet<>());
        when(questionnaireService.get(anyString(), eq(Questionnaire.class))).thenReturn(expectedQuestionnaire);

        // When
        Questionnaire result = retrieveQuestionnaireHandler.invoke(id);

        // Then
        assertEquals(expectedQuestionnaire, result);
    }
}
