package com.example.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.*;
import java.util.stream.Collectors;

@Slf4j(topic = "Error Caused By RestTemplate")
@Component
public class RestTemplateErrorHandler extends DefaultResponseErrorHandler {
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        String statusCode = response.getStatusCode().toString();
        String header = response.getHeaders().toString();
        String body = strFromInputStream(response.getBody());

        log.warn("""
                status code : {}
                header : {}
                body : {}
                """, statusCode, header, body);
    }

    public String strFromInputStream(InputStream inputStream) throws IOException {
        try (
                Reader reader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(reader)
        ) {
            return bufferedReader.lines()
                    .collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
