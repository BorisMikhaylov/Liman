package org.liman.processor.context;

import org.liman.MessageLevel;
import org.liman.annotation.LimanMessageLevel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import java.util.Objects;
import java.util.Optional;

public class Context {
    private final ProcessingEnvironment processingEnv;
    private final RoundEnvironment roundEnv;
    private MessageLevel messageLevel;

    public Context(ProcessingEnvironment processingEnv, RoundEnvironment roundEnv, MessageLevel messageLevel) {
        this.processingEnv = processingEnv;
        this.roundEnv = roundEnv;
        this.messageLevel = messageLevel;
    }

    public ProcessingEnvironment getProcessingEnv() {
        return processingEnv;
    }

    public RoundEnvironment getRoundEnv() {
        return roundEnv;
    }

    public MessageLevel getMessageLevel() {
        return messageLevel;
    }

    public void setMessageLevel(MessageLevel messageLevel) {
        this.messageLevel = messageLevel;
    }

    private static Diagnostic.Kind getKind(MessageLevel level) {
        return switch (level) {
            case ERROR -> Diagnostic.Kind.ERROR;
            case WARNING -> Diagnostic.Kind.MANDATORY_WARNING;
            case INFO -> Diagnostic.Kind.NOTE;
        };
    }

    private Optional<MessageLevel> getOverloadingLevel(Element element) {
        if (element == null) {
            return Optional.empty();
        }
        LimanMessageLevel limanMessageLevel = element.getAnnotation(LimanMessageLevel.class);
        if (limanMessageLevel != null) {
            return Optional.of(limanMessageLevel.value());
        }
        return getOverloadingLevel(element.getEnclosingElement());
    }

    public void printMessage(MessageLevel level, String message, Element element, String annotationType) {
        MessageLevel maxLevel = getOverloadingLevel(element).orElse(this.messageLevel);
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
