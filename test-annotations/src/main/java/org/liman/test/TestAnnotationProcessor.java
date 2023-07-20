package org.liman.test;

import com.google.auto.service.AutoService;
import org.liman.processor.ChildAnnotationProcessor;

import javax.annotation.processing.Processor;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class TestAnnotationProcessor extends ChildAnnotationProcessor {
    public TestAnnotationProcessor() {
        super(Id.class);
    }
}