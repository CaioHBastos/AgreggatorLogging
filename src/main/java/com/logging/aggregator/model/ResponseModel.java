package com.logging.aggregator.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseModel extends ObjectModel {

    @JsonProperty("http_status")
    private Integer httpStatus;

    public ResponseModel() {
        super();
    }
}
