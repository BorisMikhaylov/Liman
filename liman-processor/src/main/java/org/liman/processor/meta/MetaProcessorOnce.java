package org.liman.processor.meta;

import org.liman.MessageLevel;
import org.liman.annotation.Once;
import org.liman.processor.context.Context;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.*;

public class MetaProcessorOnce extends MetaProcessor {

    @Override
    public Class<? extends Annotation> getMetaAnnotationClass() {
        return Once.class;
    }

    public void process(Context context, TypeElement annotationTypeElement) {
        Set<? extends Element> annotatedElements = context.getRoundEnv().getElementsAnnotatedWith(annotationTypeElement);
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
                .forEach(e -> context.printMessage(
                        MessageLevel.ERROR,
                        "Only one element could be annotated with @" + annotationTypeElement.getSimpleName(),
                        e,
                        annotationTypeElement.toString()));
    }
}
