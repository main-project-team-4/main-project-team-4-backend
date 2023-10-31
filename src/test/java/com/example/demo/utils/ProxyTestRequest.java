package com.example.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class ProxyTestRequest {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static MockHttpServletRequestBuilder postAsJson(String endPoint, Object request) throws JsonProcessingException {
        return post(endPoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf());
    }

    public static MockHttpServletRequestBuilder putAsJson(String endPoint, Object request) throws JsonProcessingException {
        return put(endPoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf());
    }

    public static MockHttpServletRequestBuilder patchAsJson(String endPoint, Object request) throws JsonProcessingException {
        return patch(endPoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf());
    }

    public static MockHttpServletRequestBuilder deleteWithCsrf(String endPoint) {
        return delete(endPoint).with(csrf());
    }
}
