package org.liman.processor.autogeneration;

import freemarker.template.*;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class ProcessorAutoGeneration {

    public ProcessorAutoGeneration(
            ProcessingEnvironment processingEnv,
            Set<TypeElement> annotations,
            String packageName,
            String className) throws IOException {
        Configuration cfg = new Configuration();  // TODO resolve warning
        cfg.setClassForTemplateLoading(ProcessorAutoGeneration.class, "");

        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Template template = cfg.getTemplate("AnnotationProcessor.ftlj");

        ClassBean processorClassBean = new ClassBean(packageName, className);

        ProcessorBean bean = new ProcessorBean(processorClassBean,
                annotations.stream().map(a -> a.getQualifiedName().toString()).collect(Collectors.toList()));

        JavaFileObject builderFile = processingEnv
                .getFiler()
                .createSourceFile(processorClassBean.getPackageName() + "." + processorClassBean.getClassName());
        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
            template.process(bean, out);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
