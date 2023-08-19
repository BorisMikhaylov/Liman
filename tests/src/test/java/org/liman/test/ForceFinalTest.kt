package org.liman.test

import org.junit.Test

class ForceFinalTest : LimanAnnotationsTestBase() {
    @Test
    fun finalFieldsFunctions() {
        val sourceCode = """
        package org.liman.test;
        
        import org.liman.test.annotations.ShouldFinal;
        
        public abstract class OnceFields {
            /*ERROR*/@ShouldFinal
            public int f1;
        
            @ShouldFinal
            public final int f2 = 2;
            
            @ShouldFinal
            public final int func1(){
                @ShouldFinal
                final int i = 1;
                return i;
            }
            
            /*ERROR*/@ShouldFinal
            public int func2(){
                return 1;
            }
        }
        """

        testClassFromString("OnceFields", sourceCode)
    }

    @Test
    fun notFinalFields() {
        val sourceCode = """
        package org.liman.test;

        import org.liman.test.annotations.ShouldNotFinal;

        public abstract class OnceFields {
            @ShouldNotFinal
            public int f1;
        
            /*ERROR*/@ShouldNotFinal
            public final int f2 = 2;
        }
        """

        testClassFromString("OnceFields", sourceCode)
    }

    @Test
    fun classFinal() {
        val sourceCode = """
        package org.liman.test;
        
        import org.liman.test.annotations.ShouldFinal;
        
        public abstract class OnceFields {
            /*ERROR*/@ShouldFinal
            static class cl1{
        
            }
            
            @ShouldFinal
            static final class cl2{
                
            }
        }
        """
        testClassFromString("OnceFields", sourceCode)
    }
}