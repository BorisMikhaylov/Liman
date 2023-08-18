package org.liman.processor.meta;

import org.liman.processor.context.Context;

import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.List;

public abstract class MetaProcessor {

    public abstract Class<? extends Annotation> getMetaAnnotationClass();

    public abstract void process(Context context, TypeElement annotationTypeElement);

    public static List<MetaProcessor> createMetaProcessors() {
        return List.of(new MetaProcessorOnce(), new MetaProcessorFinal(), new MetaProcessorStatic(), new MetaProcessorNonStatic(), new MetaProcessorMutable());
    }

}
