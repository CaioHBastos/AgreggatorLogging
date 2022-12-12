package com.logging.aggregator.annotation;

import com.logging.aggregator.enumerator.MascaraStrategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Mascara {

    MascaraStrategy strategy() default MascaraStrategy.FULL;
}
