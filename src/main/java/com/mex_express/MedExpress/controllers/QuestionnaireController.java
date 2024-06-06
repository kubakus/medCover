package com.mex_express.MedExpress.controllers;

import com.mex_express.MedExpress.handlers.ProcessQuestionnaireAnsweredHandler;
import com.mex_express.MedExpress.handlers.RetrieveQuestionnaireHandler;
import com.mex_express.MedExpress.model.Questionnaire;
import com.mex_express.MedExpress.model.QuestionnaireAnswered;
import com.mex_express.MedExpress.model.QuestionnaireResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {
    private final ProcessQuestionnaireAnsweredHandler processQuestionnaireAnsweredHandler;
    private final RetrieveQuestionnaireHandler retrieveQuestionnaireHandler;

    public QuestionnaireController(
            ProcessQuestionnaireAnsweredHandler processQuestionnaireAnsweredHandler,
            RetrieveQuestionnaireHandler retrieveQuestionnaireHandler
    ) {
        this.processQuestionnaireAnsweredHandler = processQuestionnaireAnsweredHandler;
        this.retrieveQuestionnaireHandler = retrieveQuestionnaireHandler;
    }

    @Operation(summary = "Get a questionnaire by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Questionnaire not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Questionnaire> getQuestionnaire(
            @Parameter(description = "ID of the questionnaire to retrieve") @PathVariable String id) {
        Questionnaire questionnaire = retrieveQuestionnaireHandler.invoke(id);
        return ResponseEntity.ok(questionnaire);
    }

    @Operation(summary = "Process a questionnaire")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Questionnaire processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<QuestionnaireResult> processQuestionnaire(
            @Parameter(description = "Questionnaire answered by the user") @RequestBody QuestionnaireAnswered questionnaireAnswered) {
        QuestionnaireResult questionnaireResponse = processQuestionnaireAnsweredHandler.invoke(questionnaireAnswered);
        return ResponseEntity.ok(questionnaireResponse);
    }
}
