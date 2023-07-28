package org.liman.processor.meta;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.List;

public abstract class MetaProcessor {

    public abstract Class<? extends Annotation> getMetaAnnotationClass();

    public abstract void process(TypeElement annotationTypeElement);

    public static List<MetaProcessor> createMetaProcessors(RoundEnvironment roundEnv, ProcessingEnvironment processingEnv) {
        return List.of(new MetaProcessorOnce(roundEnv, processingEnv));
    }

}
