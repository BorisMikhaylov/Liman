package org.liman.test;

import org.junit.Test;

import javax.tools.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TestClassTest {
    @Test
    public void name() throws IOException {
        List<? extends JavaFileObject> compilationUnits
                = Collections.singletonList(new JavaSourceFromString(
                "TestClass",
                new String(Objects.requireNonNull(TestClassTest.class.getResourceAsStream("TestClass.java")).readAllBytes())));
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits).call();
        diagnostics.getDiagnostics()
                .forEach(d -> System.out.println(d.toString()));
        List<MyError> genetatedErrors = new ArrayList<>();
        for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
            genetatedErrors.add(new MyError(diagnostic.getSource().getName(), (int) diagnostic.getLineNumber()));
        }
        List<MyError> actualErrors = new ArrayList<>();
        actualErrors.add(new MyError("Id", 5));
        actualErrors.add(new MyError("Id", 7));
        assert genetatedErrors == actualErrors;
    }

    public static class MyError{
        String annotation;
        int line;

        public MyError(String annotation, int line) {
            this.annotation = annotation;
            this.line = line;
        }
    }

    public static class JavaSourceFromString extends SimpleJavaFileObject {
        final String code;

        JavaSourceFromString(String name, String code) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension),
                    Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }
    }

}