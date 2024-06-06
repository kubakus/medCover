package com.mex_express.MedExpress.model;

import java.util.Set;

public record QuestionnaireAnswered(
        // TODO should be an enum
        String id,
        Set<QuestionAnswered> questions
) implements QuestionnaireInterface {
}
