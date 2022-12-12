package com.logging.aggregator.aspect;

import com.logging.aggregator.annotation.RouteStep;
import com.logging.aggregator.context.LoggingComponent;
import com.logging.aggregator.model.RouteStepModel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@Aspect
public class RouteStepAspect {

    @Autowired
    private LoggingComponent loggingComponent;

    @Around("@annotation (com.logging.aggregator.annotation.RouteStep)")
    public void processarRouteStep(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        RouteStep routeStepAnnotation = methodSignature.getMethod().getAnnotation(RouteStep.class);

        LocalDateTime dataInicio = LocalDateTime.now();

        proceedingJoinPoint.proceed();

        LocalDateTime dataTermino = LocalDateTime.now();

        Long duracao = Duration.between(dataInicio, dataTermino).toMillis();
        String endpoint = routeStepAnnotation.endpoint();

        RouteStepModel routeStepModel = RouteStepModel.builder()
                .dataInicio(dataInicio)
                .dataTermino(dataTermino)
                .duracao(duracao)
                .endpoint(endpoint)
                .request(null)
                .response(null)
                .build();

        loggingComponent.addRouteStep(routeStepModel);
    }
}
