package org.liman.processor.meta;

import org.liman.annotation.Once;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;
import java.util.*;

public class MetaProcessorOnce extends MetaProcessor {

    RoundEnvironment roundEnv;
    ProcessingEnvironment processingEnv;

    public MetaProcessorOnce(RoundEnvironment roundEnv, ProcessingEnvironment processingEnv) {
        this.roundEnv = roundEnv;
        this.processingEnv = processingEnv;
    }

    @Override
    public Class<? extends Annotation> getMetaAnnotationClass() {
        return Once.class;
    }

    public void process(TypeElement annotationTypeElement) {
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotationTypeElement);
        Map<Element, List<Element>> groups = new HashMap<>();
        for (Element annotatedElement : annotatedElements) {
            Element enclosingElement = annotatedElement.getEnclosingElement();
            if (enclosingElement == null) {
                // TODO handle case
                continue;
            }
            groups.computeIfAbsent(enclosingElement, e -> new ArrayList<>()).add(annotatedElement);
        }
        groups.values().stream()
                .filter(c -> c.size() > 1)
                .flatMap(List::stream)
                .forEach(e -> processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.ERROR,
                        "Only one element could be annotated with @" + annotationTypeElement.getSimpleName(),
                        e,
                        e.getAnnotationMirrors()
                                .stream()
                                .filter(ee -> Objects.equals(ee.getAnnotationType().asElement(), annotationTypeElement))
                                .findFirst()
                                .orElse(null)));
    }
}
