package com.logging.aggregator.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({
        "data_inicio",
        "data_termino",
        "duracao",
        "endpoint",
        "httpStatus",
        "request",
        "response"
})
public class RouteStepModel {

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
    @JsonProperty("data_inicio")
    private LocalDateTime dataInicio;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
    @JsonProperty("data_termino")
    private LocalDateTime dataTermino;

    @JsonProperty("duracao (ms)")
    private Long duracao;

    private String endpoint;

    @JsonProperty("http_status")
    private Integer httpStatus;

    private RequestModel request;

    private ResponseModel response;
}
