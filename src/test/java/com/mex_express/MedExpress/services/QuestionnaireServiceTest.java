package com.mex_express.MedExpress.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mex_express.MedExpress.model.QuestionnaireExpected;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class QuestionnaireServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private QuestionnaireService questionnaireService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetQuestionnaire_Success() throws Exception {
        String id = "test";
        Class<QuestionnaireExpected> classType = QuestionnaireExpected.class;
        QuestionnaireExpected expectedQuestionnaire = new QuestionnaireExpected(
                "id",
                new HashSet<>()
        );
        when(objectMapper.readValue(any(InputStream.class), eq(classType))).thenReturn(expectedQuestionnaire);

        QuestionnaireExpected result = questionnaireService.get(id, classType);

        assertEquals(expectedQuestionnaire, result);
    }

    @Test
    void testGetQuestionnaire_IOException() throws Exception {
        String id = "test";
        Class<QuestionnaireExpected> classType = QuestionnaireExpected.class;
        when(objectMapper.readValue(any(InputStream.class), eq(classType))).thenThrow(new IOException("Test Exception"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            questionnaireService.get(id, classType);
        });

        assertEquals("Failed to load questionnaire questions", exception.getMessage());
    }
}
