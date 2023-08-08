package org.liman.test;

import org.liman.MessageLevel;

import java.util.Objects;

public class CompilerMessage implements Comparable<CompilerMessage> {
    MessageLevel messageLevel;
    long line;
    long column;

    public CompilerMessage(MessageLevel messageLevel, long line, long column) {
        this.messageLevel = messageLevel;
        this.line = line;
        this.column = column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompilerMessage that = (CompilerMessage) o;
        return line == that.line && column == that.column && messageLevel == that.messageLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageLevel, line, column);
    }

    @Override
    public String toString() {
        return "CompilerMessage{" +
                "messageLevel=" + messageLevel +
                ", line=" + line +
                ", column=" + column +
                '}';
    }

    @Override
    public int compareTo(CompilerMessage o) {
        return (int) (line == o.line
                ? column == o.column
                ? messageLevel.ordinal() - o.messageLevel.ordinal()
                : column - o.column
                : line - o.line);
    }
}
