package org.liman.test;

import javax.tools.Diagnostic;
import java.util.Objects;

public class CompilerMessage implements Comparable<CompilerMessage> {
    Diagnostic.Kind kind;
    long line;
    long column;

    public CompilerMessage(Diagnostic.Kind kind, long line, long column) {
        this.kind = kind;
        this.line = line;
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompilerMessage that = (CompilerMessage) o;
        return line == that.line && column == that.column && kind == that.kind;
    }

    @Override
    public int hashCode() {
        return Objects.hash(kind, line, column);
    }

    @Override
    public String toString() {
        return "CompilerMessage{" +
                "kind=" + kind +
                ", line=" + line +
                ", column=" + column +
                '}';
    }

    @Override
    public int compareTo(CompilerMessage o) {
        return (int) (line == o.line
                ? column - o.column
                : line - o.line);
    }
}
