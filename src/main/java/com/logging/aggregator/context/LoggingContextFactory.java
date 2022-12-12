package com.logging.aggregator.context;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.UUID;

public class LoggingContextFactory {

    public static LoggingContext build(ServletRequest servletRequest, List<String> headersSensiveis) {
        String contextId = UUID.randomUUID().toString();

        LoggingContext loggingContext = new LoggingContext(contextId, headersSensiveis);
        loggingContext.start(servletRequest);

        LoggingContextMap.adicionarConexto(contextId, loggingContext);

        return loggingContext;
    }
}
