package org.liman.processor.context;

import org.liman.MessageLevel;
import org.liman.annotation.LimanMessageMaxLevel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import java.util.Objects;
import java.util.Optional;

public class Context {
    private final ProcessingEnvironment processingEnv;
    private final RoundEnvironment roundEnv;
    private final MessageLevel messageMaxLevel;

    public Context(ProcessingEnvironment processingEnv, RoundEnvironment roundEnv, MessageLevel messageMaxLevel) {
        this.processingEnv = processingEnv;
        this.roundEnv = roundEnv;
        this.messageMaxLevel = messageMaxLevel;
    }

    public ProcessingEnvironment getProcessingEnv() {
        return processingEnv;
    }

    public RoundEnvironment getRoundEnv() {
        return roundEnv;
    }

    private static Diagnostic.Kind getKind(MessageLevel level) {
        return switch (level) {
            case ERROR -> Diagnostic.Kind.ERROR;
            case WARNING -> Diagnostic.Kind.MANDATORY_WARNING;
            case INFO -> Diagnostic.Kind.NOTE;
        };
    }

    private Optional<MessageLevel> getOverloadingMaxLevel(Element element) {
        if (element == null) {
            return Optional.empty();
        }
        LimanMessageMaxLevel limanMessageMaxLevel = element.getAnnotation(LimanMessageMaxLevel.class);
        if (limanMessageMaxLevel != null) {
            return Optional.of(limanMessageMaxLevel.value());
        }
        return getOverloadingMaxLevel(element.getEnclosingElement());
    }

    public void printMessage(MessageLevel level, String message, Element element, String annotationType) {
        MessageLevel maxLevel = getOverloadingMaxLevel(element).orElse(this.messageMaxLevel);
        if (maxLevel.ordinal() < level.ordinal()) {
            level = maxLevel;
        }
        getProcessingEnv().getMessager().printMessage(
                getKind(level),
                message,
                element,
                element.getAnnotationMirrors()
                        .stream()
                        .filter(ee -> Objects.equals(ee.getAnnotationType().toString(), annotationType))
                        .findFirst()
                        .orElse(null));
    }
}
