package org.liman.processor.meta;

import org.liman.MessageLevel;
import org.liman.annotation.ShouldBeStatic;
import org.liman.processor.context.Context;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Set;

public class MetaProcessorStatic extends MetaProcessor {
    @Override
    public Class<? extends Annotation> getMetaAnnotationClass() {
        return ShouldBeStatic.class;
    }

    public void process(Context context, TypeElement annotationTypeElement) {
        Set<? extends Element> annotatedElements = context.getRoundEnv().getElementsAnnotatedWith(annotationTypeElement);
        boolean isStatic = annotationTypeElement.getAnnotation(ShouldBeStatic.class).value();
        for (Element annotatedElement : annotatedElements) {
            if (annotatedElement.getModifiers().contains(Modifier.STATIC) && !isStatic) {
                context.printMessage(
                        MessageLevel.ERROR,
                        "It should not be static",
                        annotatedElement,
                        annotationTypeElement.toString()
                );
            }
            if (!annotatedElement.getModifiers().contains(Modifier.STATIC) && isStatic) {
                context.printMessage(
                        MessageLevel.ERROR,
                        "It should be static",
                        annotatedElement,
                        annotationTypeElement.toString()
                );
            }
        }
    }
}
