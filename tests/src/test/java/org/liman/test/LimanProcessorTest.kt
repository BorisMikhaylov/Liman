package org.liman.test

import org.junit.Test

class LimanProcessorTest : LimanAnnotationsTestBase() {
    @Test
    internal fun name() {
        val sourceCode = """
            package org.liman.test;

import org.liman.annotation.Once;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
        """
        testClassFromString("OnceFields", sourceCode)
    }
}