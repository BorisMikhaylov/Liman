package org.liman.test;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class OnceTest extends LimanAnnotationsTestBase {

    @Test
    public void itShouldBeOneFieldAnnotatedByOnce() throws IOException {
        testClassFromResource("OnceFields", "OnceFields.java");
    }

    @Test
    public void itShouldBeOneFunctionAnnotatedByOnce() throws IOException {
        testClassFromResource("OnceFunctions", "OnceFunctions.java");
    }
}



