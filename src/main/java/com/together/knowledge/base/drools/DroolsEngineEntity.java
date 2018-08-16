package com.together.knowledge.base.drools;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.UUID;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
public @interface DroolsEngineEntity {

    public String name() default "EngineEntity";
    public String[] fileNames() default "";
}
