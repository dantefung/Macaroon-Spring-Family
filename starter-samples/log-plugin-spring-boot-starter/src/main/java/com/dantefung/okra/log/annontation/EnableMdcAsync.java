package com.dantefung.okra.log.annontation;

import com.dantefung.okra.log.trace.MDCAsyncConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(MDCAsyncConfiguration.class)
public @interface EnableMdcAsync {

}
