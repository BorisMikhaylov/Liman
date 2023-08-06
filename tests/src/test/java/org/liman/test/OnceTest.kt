package org.liman.test

import org.junit.Test
import java.io.IOException

class OnceTest : LimanAnnotationsTestBase() {
    @Test
    @Throws(IOException::class)
    fun itShouldBeOneFieldAnnotatedByOnce() {
        val sourceCode = """
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
        """

        testClassFromString("OnceFields", sourceCode)
    }

    @Test
    @Throws(IOException::class)
    fun itShouldBeOneFunctionAnnotatedByOnce() {
        val sourseCode = """
            package org.liman.test;

            import org.liman.MessageLevel;
            import org.liman.annotation.LimanMessageLevel;
            import org.liman.test.annotations.Id;

            public abstract class OnceFunctions {
                /*ERROR*/@Id
                public int f1(){
                    return 1;
                }

                /*ERROR*/@Id
                public int f2(){
                    return 2;
                };
            }
        """
        testClassFromString("OnceFunctions", sourseCode)
    }
}