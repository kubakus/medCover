package com.mex_express.MedExpress.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mex_express.MedExpress.model.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class QuestionnaireService {

    private final ObjectMapper objectMapper;

    public QuestionnaireService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T extends QuestionnaireInterface> T get(String id, Class<T> classType) {
        return getQuestionnaire(id, classType);
    }

    private <T extends QuestionnaireInterface> T getQuestionnaire(String id, Class<T> classType) {
        try (InputStream inputStream = getClass().getResourceAsStream("/" + id + ".json")) {
            return objectMapper.readValue(inputStream, classType);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load questionnaire questions", e);
        }
    }
}


