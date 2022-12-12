package com.logging.aggregator.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RequestModel extends ObjectModel {

    private String url;

    @JsonProperty("http_method")
    private String httpMethod;

    private Map<String, Object> parametros = new HashMap<>();

    public RequestModel() {
        super();
    }
}
