package org.liman.annotation;

import org.liman.MessageLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PACKAGE, ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.MODULE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LimanMessageMaxLevel {
    MessageLevel value() default MessageLevel.ERROR;
}
