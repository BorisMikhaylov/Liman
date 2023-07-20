package org.liman.processor;

import com.google.auto.service.AutoService;
import org.liman.annotation.Once;
import org.liman.processor.meta.MetaProcessor;
import org.liman.processor.meta.MetaProcessorOnce;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
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
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Once.class.getCanonicalName());
    }
}