package org.liman.processor.meta;

import org.liman.MessageLevel;
import org.liman.annotation.ForceMutable;
import org.liman.processor.context.Context;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Set;

public class MetaProcessorMutable extends MetaProcessor {
    @Override
    public Class<? extends Annotation> getMetaAnnotationClass() {
        return ForceMutable.class;
    }

    public void process(Context context, TypeElement annotationTypeElement) {
        Set<? extends Element> annotatedElements = context.getRoundEnv().getElementsAnnotatedWith(annotationTypeElement);
        for (Element annotatedElement : annotatedElements) {
            if (annotatedElement.getModifiers().contains(Modifier.FINAL)) {
                context.printMessage(
                        MessageLevel.ERROR,
                        "It should not be final",
                        annotatedElement,
                        annotationTypeElement.toString()
                );
            }
        }
    }
}
