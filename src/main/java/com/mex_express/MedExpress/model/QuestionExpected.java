package com.mex_express.MedExpress.model;

public record QuestionExpected(
        String id,
        Boolean expected,
        String reason
) implements QuestionInterface {
}
