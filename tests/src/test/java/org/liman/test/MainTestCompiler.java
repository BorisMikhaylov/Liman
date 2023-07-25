package org.liman.test;

import javax.tools.*;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class MainTestCompiler {

    private DiagnosticCollector<JavaFileObject> compileClassFromResource(String className, String resource) throws IOException {
        List<? extends JavaFileObject> compilationUnits
                = Collections.singletonList(new JavaSourceFromString(
                className,
                new String(Objects.requireNonNull(OnceTest.class.getResourceAsStream(resource)).readAllBytes())));
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.getSourceVersions();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits).call();
        return diagnostics;
    }

    private Collection<CompilerMessage> getExpectedMessages(String fileContent) {
        String flag = "/*ERROR*/";
        Collection<CompilerMessage> errors = new TreeSet<>();
        String[] lines = fileContent.split("\n");
        for (int i = 0; i < lines.length; ++i) {
            String line = lines[i];
            int index = line.indexOf(flag);
            while (index >= 0) {
                errors.add(new CompilerMessage(Diagnostic.Kind.ERROR, i + 1, index + flag.length() + 1));
                index = line.indexOf(flag, index + 1);
            }
        }
        return errors;
    }

    public void testClassFromResource(String className, String resource) throws IOException {
        DiagnosticCollector<JavaFileObject> diagnosticCollector = compileClassFromResource(className, resource);
        Collection<CompilerMessage> expectedErrors = getExpectedMessages(new String(Objects.requireNonNull(OnceTest.class.getResourceAsStream(resource)).readAllBytes()));
        Collection<CompilerMessage> actualErrors = diagnosticCollector.getDiagnostics().stream()
                .map(d -> new CompilerMessage(d.getKind(), d.getLineNumber(), d.getColumnNumber()))
                .collect(Collectors.toCollection(TreeSet::new));
        assertEquals(expectedErrors, actualErrors);
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
