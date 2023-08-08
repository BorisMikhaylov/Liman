package org.liman;

import javax.tools.Diagnostic;

public enum MessageLevel {
    INFO(Diagnostic.Kind.NOTE),
    WARNING(Diagnostic.Kind.MANDATORY_WARNING),
    ERROR(Diagnostic.Kind.ERROR);

    private final Diagnostic.Kind kind;

    MessageLevel(Diagnostic.Kind kind) {
        this.kind = kind;
    }

    public Diagnostic.Kind getKind() {
        return kind;
    }

    public static MessageLevel fromKind(Diagnostic.Kind kind) {
        return switch (kind) {
            case ERROR -> MessageLevel.ERROR;
            case WARNING -> MessageLevel.WARNING;
            case MANDATORY_WARNING -> MessageLevel.WARNING;
            case NOTE -> MessageLevel.INFO;
            case OTHER -> MessageLevel.INFO;
        };
    }
}
