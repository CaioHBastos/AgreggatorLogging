package com.logging.aggregator.context;

import com.logging.aggregator.model.LoggingModel;
import com.logging.aggregator.model.RouteStepModel;
import org.slf4j.MDC;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

@Component
public class LoggingComponent {


    public void addInternalLog(String mensagem, LogLevel logLevel, Class<?> classOrigem) {
        LoggingModel loggingModel = getLoggingModel();
        loggingModel.addInternalLog(mensagem, logLevel, classOrigem);
    }

    public void addRequestParametros(Object logModelParametros) {
        LoggingModel loggingModel = getLoggingModel();
        loggingModel.addRequestParametros(logModelParametros);
    }

    public void addRequestBody(Object logModelRequest) {
        LoggingModel loggingModel = getLoggingModel();
        loggingModel.addRequestBody(logModelRequest);
    }

    public void addResponseBody(Object logModelResponse) {
        LoggingModel loggingModel = getLoggingModel();
        loggingModel.addResponseBody(logModelResponse);
    }

    public void addRouteStep(RouteStepModel routeStepModel) {
        LoggingModel loggingModel = getLoggingModel();
        loggingModel.addRouteStep(routeStepModel);
    }

    private LoggingModel getLoggingModel() {
        String contextId = MDC.get(LoggingContextConstants.CONTEXT_ID);
        LoggingContext loggingContext = LoggingContextMap.recuperarContexto(contextId);

        return loggingContext.getLoggingModel();
    }
}
