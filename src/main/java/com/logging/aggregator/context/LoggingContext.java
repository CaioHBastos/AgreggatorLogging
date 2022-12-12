package com.logging.aggregator.context;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logging.aggregator.enumerator.MascaraStrategy;
import com.logging.aggregator.model.LoggingModel;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class LoggingContext {

    private static final Logger LOGGER = LoggerFactory.getLogger("logger");

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<String> headersSensiveis;

    @Getter
    private String contextId;

    @Getter
    private LoggingModel loggingModel;

    private Boolean contextIniciado = false;
    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;

    public LoggingContext(String contextId) {
        this(contextId, new ArrayList<>());
    }

    public LoggingContext(String contextId, List<String> headersSensiveis) {
        this.contextId = contextId;
        this.headersSensiveis = headersSensiveis;
    }

    public void start(ServletRequest servletRequest) {
        this.contextIniciado = true;
        this.loggingModel = new LoggingModel();

        this.loggingModel.setup();
        this.httpServletRequest = (HttpServletRequest) servletRequest;

        addRequestHeaders();

        this.loggingModel.addRequestUrl(httpServletRequest.getRequestURI());
        this.loggingModel.addRequestMethod(httpServletRequest.getMethod());
    }

    public void close(ServletResponse servletResponse) throws JsonProcessingException {
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        if (this.contextIniciado) {
            this.httpServletResponse = (HttpServletResponse) servletResponse;

            addResponseHeaders();

            this.loggingModel.addResponseStatus(httpServletResponse.getStatus());
            this.loggingModel.finish();
            LOGGER.info(this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.loggingModel));
        }
    }

    private void addRequestHeaders() {
        httpServletRequest.getHeaderNames().asIterator().forEachRemaining(headerKey -> {
            String headerValue = httpServletRequest.getHeader(headerKey);

            if (headersSensiveis.contains(headerKey)) {
                loggingModel.addRequestHeader(headerKey, headerValue, MascaraStrategy.FULL);

            } else {
                loggingModel.addRequestHeader(headerKey, headerValue);
            }
        });
    }

    private void addResponseHeaders() {
        httpServletResponse.getHeaderNames().forEach(headerKey -> {
            String headerValue = httpServletRequest.getHeader(headerKey);

            if (headersSensiveis.contains(headerKey)) {
                loggingModel.addResponseHeader(headerKey, headerValue, MascaraStrategy.FULL);

            } else {
                loggingModel.addResponseHeader(headerKey, headerValue);
            }
        });
    }
}
