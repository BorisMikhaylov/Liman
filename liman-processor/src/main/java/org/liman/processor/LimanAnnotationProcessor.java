package org.liman.processor;

import com.google.auto.service.AutoService;
import org.liman.annotation.LimanProcessor;
import org.liman.annotation.Once;
import org.liman.processor.autogeneration.ProcessorAutoGeneration;
import org.liman.processor.meta.MetaProcessor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.*;

@AutoService(Processor.class)
public class LimanAnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        List<MetaProcessor> metaProcessors = MetaProcessor.createMetaProcessors(roundEnv, processingEnv);
        for (MetaProcessor metaProcessor : metaProcessors) {
            for (Element element : roundEnv.getElementsAnnotatedWith(metaProcessor.getMetaAnnotationClass())) {
                Optional.of(element)
                        .filter(TypeElement.class::isInstance)
                        .map(TypeElement.class::cast)
                        .ifPresent(metaProcessor::process);
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
            limanProcessorAnnotations.forEach(e ->
                    processingEnv.getMessager().printMessage(
                            Diagnostic.Kind.ERROR,
                            "You can only annotate one package by @LimanProcessor",
                            e,
                            e.getAnnotationMirrors()
                                    .stream()
                                    .filter(ee -> Objects.equals(ee.getAnnotationType().toString(), LimanProcessor.class.getCanonicalName()))
                                    .findFirst()
                                    .orElse(null)));
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