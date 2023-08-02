package org.liman.processor;

import com.google.auto.service.AutoService;
import org.liman.MessageLevel;
import org.liman.annotation.LimanProcessor;
import org.liman.annotation.Once;
import org.liman.processor.autogeneration.ProcessorAutoGeneration;
import org.liman.processor.context.Context;
import org.liman.processor.meta.MetaProcessor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AutoService(Processor.class)
public class LimanAnnotationProcessor extends AbstractProcessor {

    List<MetaProcessor> metaProcessors = MetaProcessor.createMetaProcessors();

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        Context context = new Context(processingEnv, roundEnv, MessageLevel.ERROR);
        for (MetaProcessor metaProcessor : metaProcessors) {
            for (Element element : roundEnv.getElementsAnnotatedWith(metaProcessor.getMetaAnnotationClass())) {
                Optional.of(element)
                        .filter(TypeElement.class::isInstance)
                        .map(TypeElement.class::cast)
                        .ifPresent(e -> metaProcessor.process(context, e));
            }
        }
        Set<TypeElement> annotatedClasses = new HashSet<>();
        for (MetaProcessor metaProcessor : metaProcessors) {
            roundEnv.getElementsAnnotatedWith(metaProcessor.getMetaAnnotationClass())
                    .stream()
                    .filter(TypeElement.class::isInstance)
                    .map(TypeElement.class::cast)
                    .forEach(annotatedClasses::add);
        }
        Set<? extends Element> limanProcessorAnnotations = roundEnv.getElementsAnnotatedWith(LimanProcessor.class);
        if (limanProcessorAnnotations.size() > 1) {
            limanProcessorAnnotations.forEach(e -> context.printMessage(MessageLevel.ERROR, "You can only annotate one package by @LimanProcessor",
                    e, LimanProcessor.class.toString()));
        } else if (limanProcessorAnnotations.size() == 1) {
            PackageElement packageElement = (PackageElement) limanProcessorAnnotations.stream().findFirst().get();
            LimanProcessor limanProcessor = packageElement.getAnnotation(LimanProcessor.class);
            try {
                new ProcessorAutoGeneration(processingEnv, annotatedClasses, packageElement.getQualifiedName().toString(), limanProcessor.processorClassName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Once.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}