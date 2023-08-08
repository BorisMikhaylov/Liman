package org.liman.test

import org.junit.Test

class LimanProcessorTest : LimanAnnotationsTestBase() {
    @Test
    internal fun OnceFieldsTest() {
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

    @Test
    fun itShouldBeOneFunctionAnnotatedByOnce() {
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

public abstract class OnceFunctions {
    /*ERROR*/@Id
    public int f1(){
        return 1;
    }

    /*ERROR*/@Id
    public int f2(){
        return 2;
    }
}
        """
        testClassFromString("OnceFunctions", sourceCode)
    }

    @Test
    fun itShouldBeOneMemberAnnotatedByOnce() {
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

public abstract class OnceMember {
    /*ERROR*/@Id
    public int m1;

    /*ERROR*/@Id
    public int f2(){
        return 2;
    }
}
        """
        testClassFromString("OnceMember", sourceCode)
    }

    @Test
    fun itShouldBeOneParamenterAnnotatedByOnce() {
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

public abstract class OnceParameter {
    public int m1(/*ERROR*/@Id int p1, int p2, /*ERROR*/@Id int p3){
        return p1 + p2 + p3;
    }
}
        """
        testClassFromString("OnceParameter", sourceCode)
    }

    @Test
    fun noErrors() {
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

public abstract class OnceParameter {
    @Id
    public int m1(@Id int p1, int p2, int p3){
        return p1 + p2 + p3;
    }
    
    String name = "Vasiliy";
}
        """
        testClassFromString("OnceParameter", sourceCode)
    }
}