package org.liman.test;

import javax.tools.*;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class LimanAnnotationsTestBase {

    private Collection<CompilerMessage> compileClassFromString(String name, String sourceCode, List<String> compilerOptions) {
        List<? extends JavaFileObject> compilationUnits
                = Collections.singletonList(new JavaSourceFromString(name, sourceCode));
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        compiler.getTask(null, fileManager, diagnostics, compilerOptions, null, compilationUnits).call();
        diagnostics.getDiagnostics().forEach(System.out::println);
        return diagnostics.getDiagnostics().stream()
                .map(d -> new CompilerMessage(d.getKind(), d.getLineNumber(), d.getColumnNumber()))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    private Collection<CompilerMessage> getExpectedMessages(String fileContent) {

        Collection<CompilerMessage> errors = new TreeSet<>();
        String[] lines = fileContent.split("\n");
        for (Diagnostic.Kind kind : Diagnostic.Kind.values()) {
            String flag = "/*" + kind.toString() + "*/";
            for (int lineNumber = 0; lineNumber < lines.length; ++lineNumber) {
                String line = lines[lineNumber];
                int index = line.indexOf(flag);
                while (index >= 0) {
                    errors.add(new CompilerMessage(kind, lineNumber + 1, index + flag.length() + 1));
                    index = line.indexOf(flag, index + 1);
                }
            }
        }
        return errors;
    }

    public void testClassFromString(String className, String sourceCode) {

        Collection<CompilerMessage> actualErrors = compileClassFromString(className, sourceCode, List.of("-proc:none"));
        assertEquals(new TreeSet<>(), actualErrors);

        actualErrors = compileClassFromString(className, sourceCode, null);
        Collection<CompilerMessage> expectedErrors = getExpectedMessages(sourceCode);
        assertEquals(expectedErrors, actualErrors);
    }

    public static class JavaSourceFromString extends SimpleJavaFileObject {
        final String sourceCode;

        JavaSourceFromString(String name, String sourceCode) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension),
                    Kind.SOURCE);
            this.sourceCode = sourceCode;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return sourceCode;
        }
    }
}
