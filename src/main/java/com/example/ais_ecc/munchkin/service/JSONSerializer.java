package com.example.ais_ecc.munchkin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JSONSerializer {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static String toJSON(Object object) throws Exception {
        return objectMapper.writeValueAsString(object);
    }
}
