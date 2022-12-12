package com.logging.aggregator.context;

import java.util.HashMap;
import java.util.Map;

public class LoggingContextMap {

    private static final Map<String, LoggingContext> CONTEXTS = new HashMap<>();

    public static void adicionarConexto(String contextId, LoggingContext loggingContext) {
        CONTEXTS.put(contextId, loggingContext);
    }

    public static LoggingContext recuperarContexto(String contextId) {
        return CONTEXTS.get(contextId);
    }

    public static void removerContexto(String contextId) {
        CONTEXTS.remove(contextId);
    }
}
