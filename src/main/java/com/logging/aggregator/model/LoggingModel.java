package com.logging.aggregator.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.logging.aggregator.enumerator.MascaraStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.logging.LogLevel;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({
        "data_inicio",
        "data_termino",
        "duracao (ms)",
        "thread_name",
        "request",
        "response",
        "logs_aplicacao",
        "route_steps"
})
public class LoggingModel {

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
    @JsonProperty("data_inicio")
    private LocalDateTime dataInicio;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
    @JsonProperty("data_termino")
    private LocalDateTime dataTermino;

    @JsonProperty("duracao (ms)")
    private Long duracao;

    @JsonProperty("thread_name")
    private String threadName;

    private RequestModel request;

    private ResponseModel response;

    @JsonProperty("logs_aplicacao")
    private List<InternalLogModel> internalLogs;

    @JsonProperty("route_steps")
    private List<RouteStepModel> routeSteps;

    public void setup() {
        this.dataInicio = LocalDateTime.now();
        this.threadName = Thread.currentThread().getName();
        this.internalLogs = new ArrayList<>();
        this.request = new RequestModel();
        this.response = new ResponseModel();
        this.routeSteps = new ArrayList<>();
    }

    public void finish() {
        this.dataTermino = LocalDateTime.now();
        this.duracao = Duration.between(this.dataInicio, this.dataTermino).toMillis();
    }

    public void addInternalLog(String log, LogLevel logLevel, Class<?> clazz) {
        this.internalLogs.add(new InternalLogModel(log, logLevel, clazz));
    }

    public void addRequestHeader(String key, String value, MascaraStrategy strategy) {
        //TODO MASCARAMENTO
        addRequestHeader(key, value);
    }

    public void addRequestHeader(String key, String value) {
        this.request.addHeader(key, value);
    }

    public void addRequestUrl(String url) {
        this.request.setUrl(url);
    }

    public void addRequestMethod(String httpMethod) {
        this.request.setHttpMethod(httpMethod);
    }

    @SuppressWarnings("unchecked")
    public void addRequestParametros(Object parametros) {
        Map<String, Object> requestParametros = new ObjectMapper().convertValue(parametros, Map.class);

        //TODO MASCARAMENTO

        request.setParametros(requestParametros);
    }

    @SuppressWarnings("unchecked")
    public void addRequestBody(Object body) {
        Map<String, Object> requestBody = new ObjectMapper().convertValue(body, Map.class);

        //TODO MASCARAMENTO

        this.request.setBody(requestBody);
    }

    @SuppressWarnings("unchecked")
    public void addResponseBody(Object body) {
        Map<String, Object> responseBody = new ObjectMapper().convertValue(body, Map.class);

        //TODO MASCARAMENTO

        this.response.setBody(responseBody);
    }

    public void addResponseHeader(String headerKey, String headerValue, MascaraStrategy strategy) {
        //TODO MASCARAMENTO
        addResponseHeader(headerKey, headerValue);
    }

    public void addResponseHeader(String key, String value) {
        this.response.addHeader(key, value);
    }

    public void addResponseStatus(Integer httpStatus) {
        this.response.setHttpStatus(httpStatus);
    }

    public void addRouteStep(RouteStepModel routeStepModel) {
        this.routeSteps.add(routeStepModel);
    }
}
