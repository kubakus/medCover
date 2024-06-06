package com.mex_express.MedExpress.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mex_express.MedExpress.handlers.ProcessQuestionnaireAnsweredHandler;
import com.mex_express.MedExpress.handlers.RetrieveQuestionnaireHandler;
import com.mex_express.MedExpress.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuestionnaireControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProcessQuestionnaireAnsweredHandler processQuestionnaireAnsweredHandler;

    @MockBean
    private RetrieveQuestionnaireHandler retrieveQuestionnaireHandler;

    @Autowired
    private ObjectMapper objectMapper;

    private Questionnaire questionnaire;
    private QuestionnaireAnswered questionnaireAnswered;
    private QuestionnaireResult questionnaireResult;

    @BeforeEach
    void setUp() throws Exception {
        String id = "test";
        Set<Question> questions = Set.of(
                new Question("q1", "Question 1"),
                new Question("q2", "Question 2")
        );
        questionnaire = new Questionnaire(id, questions);

        Set<QuestionAnswered> answeredQuestions = Set.of(
                new QuestionAnswered("q1", "true", true),
                new QuestionAnswered("q2", "false", false)
        );
        questionnaireAnswered = new QuestionnaireAnswered(id, answeredQuestions);

        Set<QuestionResult> resultQuestions = Set.of(
                new QuestionResult("q1", "Expected Answer: true")
        );
        questionnaireResult = new QuestionnaireResult(id, false, resultQuestions);

        when(retrieveQuestionnaireHandler.invoke(anyString())).thenReturn(questionnaire);
        when(processQuestionnaireAnsweredHandler.invoke(any(QuestionnaireAnswered.class))).thenReturn(questionnaireResult);
    }

    @Test
    void testGetQuestionnaire() {
        webTestClient.get().uri("/questionnaire/{id}", questionnaire.id())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(questionnaire.id())
                .jsonPath("$.questions[0].id").isEqualTo("q1")
                .jsonPath("$.questions[0].value").isEqualTo("Question 1")
                .jsonPath("$.questions[1].id").isEqualTo("q2")
                .jsonPath("$.questions[1].value").isEqualTo("Question 2");
    }

    @Test
    void testProcessQuestionnaire() throws JsonProcessingException {
        webTestClient.post().uri("/questionnaire")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(objectMapper.writeValueAsString(questionnaireAnswered))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(questionnaireResult.id())
                .jsonPath("$.valid").isEqualTo(false)
                .jsonPath("$.questions[0].id").isEqualTo("q1")
                .jsonPath("$.questions[0].reason").isEqualTo("Expected Answer: true");
    }
}
