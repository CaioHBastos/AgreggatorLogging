package com.logging.aggregator.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public abstract class ObjectModel {

    private Map<String, Object> headers = new HashMap<>();
    private Map<String, Object> body = new HashMap<>();

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }
}
