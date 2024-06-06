package com.mex_express.MedExpress.model;

public record QuestionResult(
        String id,
        String reason
) implements QuestionInterface {
}
