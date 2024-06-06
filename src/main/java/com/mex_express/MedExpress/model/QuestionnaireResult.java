package com.mex_express.MedExpress.model;

import java.util.Set;

public record QuestionnaireResult(
        String id,
        Boolean valid,
        Set<QuestionResult> questions
) implements QuestionInterface{

}
