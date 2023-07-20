package org.liman.test;

import org.example.annotation.OnceInClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@OnceInClass()
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.CLASS)
public @interface Id2 {

}

class TestClass {
    public int f1;
}


