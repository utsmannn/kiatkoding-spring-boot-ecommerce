package com.kiatkoding.ecommerce.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kiatkoding.ecommerce.model.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class ExceptionUtils {

    public void sendExceptionEntryPoint(HttpServletRequest request, HttpServletResponse response, Exception exception) {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        if (response.getStatus() == 200) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("timestamp", new Date().toInstant().toString());
        data.put("error", exception.getMessage());
        data.put("path", request.getServletPath());

        BaseResponse errorResponse = new BaseResponse(false, "Error on " + request.getServletPath(), data);

        String json;
        try {
            json = new ObjectMapper().writeValueAsString(errorResponse);
        } catch (JsonProcessingException e) {
            json = errorResponse.toString();
        }

        try {
            response.getWriter().write(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
