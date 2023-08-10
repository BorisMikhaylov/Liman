package org.liman.processor.meta;

import org.liman.MessageLevel;
import org.liman.annotation.ShouldBeFinal;
import org.liman.processor.context.Context;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Set;

public class MetaProcessorFinal extends MetaProcessor {
    @Override
    public Class<? extends Annotation> getMetaAnnotationClass() {
        return ShouldBeFinal.class;
    }

    public void process(Context context, TypeElement annotationTypeElement) {
        Set<? extends Element> annotatedElements = context.getRoundEnv().getElementsAnnotatedWith(annotationTypeElement);
        boolean isFinal = annotationTypeElement.getAnnotation(ShouldBeFinal.class).value();
        for (Element annotatedElement : annotatedElements) {
            if (annotatedElement.getModifiers().contains(Modifier.FINAL) && !isFinal) {
                context.printMessage(
                        MessageLevel.ERROR,
                        "It should not be final",
                        annotatedElement,
                        annotationTypeElement.toString()
                );
            }
            if (!annotatedElement.getModifiers().contains(Modifier.FINAL) && isFinal) {
                context.printMessage(
                        MessageLevel.ERROR,
                        "It should be final",
                        annotatedElement,
                        annotationTypeElement.toString()
                );
            }
        }
    }
}
