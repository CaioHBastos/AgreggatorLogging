package com.logging.aggregator.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.logging.LogLevel;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InternalLogModel {

    private String log;
    private LogLevel logLevel;
    private String classe;

    public InternalLogModel(String log, LogLevel logLevel, Class<?> clazz) {
        this.log = log;
        this.logLevel = logLevel;
        this.classe = clazz.getCanonicalName();
    }
}
