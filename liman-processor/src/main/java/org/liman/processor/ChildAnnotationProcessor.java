package org.liman.processor;

import org.liman.MessageLevel;
import org.liman.processor.context.Context;
import org.liman.processor.meta.MetaProcessor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ChildAnnotationProcessor extends AbstractProcessor {

    List<Class<? extends Annotation>> classes;
    List<MetaProcessor> metaProcessors = MetaProcessor.createMetaProcessors();


    @SafeVarargs
    public ChildAnnotationProcessor(Class<? extends Annotation>... classes) {
        this.classes = List.of(classes);
        System.out.println(this.getClass().getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        Context context = new Context(processingEnv, roundEnv, MessageLevel.ERROR);
        for (MetaProcessor metaProcessor : metaProcessors) {
            classes.stream()
                    .filter(cl -> cl.getAnnotation(metaProcessor.getMetaAnnotationClass()) != null)
                    .forEach(cl -> metaProcessor.process(context, processingEnv.getElementUtils().getTypeElement(cl.getCanonicalName())));
        }
            return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return classes.stream().map(Class::getCanonicalName).collect(Collectors.toSet());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
