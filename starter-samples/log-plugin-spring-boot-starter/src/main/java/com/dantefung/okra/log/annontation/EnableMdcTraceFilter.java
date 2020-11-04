package com.dantefung.okra.log.annontation;

import com.dantefung.okra.log.trace.MDCTraceFilterConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(MDCTraceFilterConfiguration.class)
public @interface EnableMdcTraceFilter {
}
