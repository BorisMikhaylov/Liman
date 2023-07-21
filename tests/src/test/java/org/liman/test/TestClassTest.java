package org.liman.test;

import org.junit.Test;

import javax.tools.*;
import java.io.IOException;
import java.net.URI;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class TestClassTest {
    @Test
    public void name() throws IOException {
        List<? extends JavaFileObject> compilationUnits
                = Collections.singletonList(new JavaSourceFromString(
                "TestClass",
                new String(Objects.requireNonNull(TestClassTest.class.getResourceAsStream("TestClass.java")).readAllBytes())));
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.getSourceVersions();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits).call();
        diagnostics.getDiagnostics()
                .forEach(d -> System.out.println(d.toString()));
        Set<MyError> genetatedErrors = new HashSet<>();
        for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
            genetatedErrors.add(new MyError(diagnostic.getSource().getName(), (int) diagnostic.getLineNumber()));
        }
        Set<MyError> actualErrors = new HashSet<>();
        actualErrors.add(new MyError("Id", 5));
        actualErrors.add(new MyError("Id", 7));
        assertEquals( genetatedErrors,  actualErrors);
    }

    public static class MyError {
        String annotation;
        int line;

        public MyError(String annotation, int line) {
            this.annotation = annotation;
            this.line = line;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MyError myError = (MyError) o;
            return line == myError.line && Objects.equals(annotation, myError.annotation);
        }

        @Override
        public int hashCode() {
            return Objects.hash(annotation, line);
        }

        @Override
        public String toString() {
            return "MyError{" +
                    "annotation='" + annotation + '\'' +
                    ", line=" + line +
                    '}';
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