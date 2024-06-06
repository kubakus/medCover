package com.mex_express.MedExpress.handlers;

import com.mex_express.MedExpress.model.*;
import com.mex_express.MedExpress.services.QuestionnaireService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class ProcessQuestionnaireAnsweredHandlerTest {

    @Mock
    private QuestionnaireService questionnaireService;

    @InjectMocks
    private ProcessQuestionnaireAnsweredHandler processQuestionnaireAnsweredHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInvoke_Success_AllAnswersCorrect() {
        String id = "test";
        Set<QuestionExpected> expectedQuestions = Set.of(
                new QuestionExpected("q1", true, "Expected Answer: true"),
                new QuestionExpected("q2", false, "Expected Answer: false")
        );
        QuestionnaireExpected expectedQuestionnaire = new QuestionnaireExpected(id, expectedQuestions);

        Set<QuestionAnswered> answeredQuestions = Set.of(
                new QuestionAnswered("q1", "true", true),
                new QuestionAnswered("q2", "false", false)
        );
        QuestionnaireAnswered questionnaireAnswered = new QuestionnaireAnswered(id, answeredQuestions);

        when(questionnaireService.get(anyString(), eq(QuestionnaireExpected.class))).thenReturn(expectedQuestionnaire);

        QuestionnaireResult result = processQuestionnaireAnsweredHandler.invoke(questionnaireAnswered);

        assertEquals(id, result.id());
        assertEquals(true, result.valid());
        assertEquals(Set.of(), result.questions());
    }

    @Test
    void testInvoke_Failure_SomeAnswersIncorrect() {
        String id = "test";
        Set<QuestionExpected> expectedQuestions = Set.of(
                new QuestionExpected("q1", true, "Expected Answer: true"),
                new QuestionExpected("q2", false, "Expected Answer: false")
        );
        QuestionnaireExpected expectedQuestionnaire = new QuestionnaireExpected(id, expectedQuestions);

        Set<QuestionAnswered> answeredQuestions = Set.of(
                new QuestionAnswered("q1", "false", false),
                new QuestionAnswered("q2", "false", false)
        );
        QuestionnaireAnswered questionnaireAnswered = new QuestionnaireAnswered(id, answeredQuestions);

        when(questionnaireService.get(anyString(), eq(QuestionnaireExpected.class))).thenReturn(expectedQuestionnaire);

        QuestionnaireResult result = processQuestionnaireAnsweredHandler.invoke(questionnaireAnswered);

        Set<QuestionResult> expectedResultQuestions = Set.of(
                new QuestionResult("q1", "Expected Answer: true")
        );

        assertEquals(id, result.id());
        assertEquals(false, result.valid());
        assertEquals(expectedResultQuestions, result.questions());
    }
}
