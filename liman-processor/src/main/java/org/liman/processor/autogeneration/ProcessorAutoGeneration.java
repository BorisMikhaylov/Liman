package org.liman.processor.autogeneration;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.liman.MessageLevel;

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
            String className, MessageLevel maxMessageLevel) throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setClassForTemplateLoading(ProcessorAutoGeneration.class, "");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Template template = cfg.getTemplate("AnnotationProcessor.ftlj");

        ClassBean processorClassBean = new ClassBean(packageName, className);

        ProcessorBean bean = new ProcessorBean(processorClassBean,
                annotations.stream().map(a -> a.getQualifiedName().toString()).collect(Collectors.toList()), maxMessageLevel);

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
