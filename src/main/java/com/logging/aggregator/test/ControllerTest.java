package com.logging.aggregator.test;

import com.logging.aggregator.context.LoggingComponent;
import com.logging.aggregator.context.LoggingContext;
import com.logging.aggregator.context.LoggingContextConstants;
import com.logging.aggregator.context.LoggingContextMap;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ControllerTest {

    @Autowired
    private LoggingComponent loggingComponent;

    @GetMapping(value = "/test")
    public ResponseEntity<Map<String, Object>> getLog() throws InterruptedException {
        loggingComponent.addInternalLog("Iniciando a busca na aplicação", LogLevel.INFO, this.getClass());

        //Thread.sleep(1000);

        Map<String, Object> mapBody = new HashMap<>(){{
            put("body", "Sucesso");
        }};

        loggingComponent.addResponseBody(mapBody);

        String contextId = MDC.get(LoggingContextConstants.CONTEXT_ID);
        LoggingContext loggingContext = LoggingContextMap.recuperarContexto(contextId);
        loggingComponent.addInternalLog(loggingContext.getContextId(), LogLevel.INFO, this.getClass());

        return ResponseEntity.ok(mapBody);
    }
}
