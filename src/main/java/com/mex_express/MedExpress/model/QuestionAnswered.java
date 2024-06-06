package com.mex_express.MedExpress.model;

public record QuestionAnswered(
        String id,
        String value,
        Boolean answer
) implements QuestionInterface {
}
