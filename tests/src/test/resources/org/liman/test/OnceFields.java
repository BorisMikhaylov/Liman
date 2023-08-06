package org.liman.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.liman.annotation.Once;

@Once
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
@interface Id {
}

public abstract class OnceFields {
    /*ERROR*/@Id
    public int f1;

    /*ERROR*/@Id
    public int f2;

}
