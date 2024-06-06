package com.mex_express.MedExpress.model;

import java.util.Set;

public record QuestionnaireExpected(
        String id,
        Set<QuestionExpected> questions
) implements QuestionnaireInterface {
}
