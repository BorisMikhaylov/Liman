package org.liman.processor.meta;

import org.liman.MessageLevel;
import org.liman.annotation.ForceNonStatic;
import org.liman.processor.context.Context;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Set;

public class MetaProcessorNonStatic extends MetaProcessor {
    @Override
    public Class<? extends Annotation> getMetaAnnotationClass() {
        return ForceNonStatic.class;
    }

    public void process(Context context, TypeElement annotationTypeElement) {
        Set<? extends Element> annotatedElements = context.getRoundEnv().getElementsAnnotatedWith(annotationTypeElement);
        for (Element annotatedElement : annotatedElements) {
            if (annotatedElement.getModifiers().contains(Modifier.STATIC)) {
                context.printMessage(
                        MessageLevel.ERROR,
                        "It should not be static",
                        annotatedElement,
                        annotationTypeElement.toString()
                );
            }
        }
    }
}
