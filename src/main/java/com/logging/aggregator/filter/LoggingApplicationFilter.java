package com.logging.aggregator.filter;

import com.logging.aggregator.context.LoggingContext;
import com.logging.aggregator.context.LoggingContextConstants;
import com.logging.aggregator.context.LoggingContextFactory;
import com.logging.aggregator.context.LoggingContextMap;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class LoggingApplicationFilter implements Filter {

    @Value("#{'${app.logging.headers-sensiveis}'.split(',')}")
    private List<String> headersSensiveis;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        final LoggingContext loggingContext = LoggingContextFactory.build(servletRequest, this.headersSensiveis);

        MDC.put(LoggingContextConstants.CONTEXT_ID, loggingContext.getContextId());

        filterChain.doFilter(servletRequest, servletResponse);

        loggingContext.close(servletResponse);

        LoggingContextMap.removerContexto(loggingContext.getContextId());
    }
}
