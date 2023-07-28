package org.liman.processor.autogeneration;

import freemarker.template.*;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;

public class ProcessorAutoGeneration {

    public ProcessorAutoGeneration(ProcessingEnvironment processingEnv) throws IOException {
        Configuration cfg = new Configuration();  // TODO resolve warning
        cfg.setClassForTemplateLoading(ProcessorAutoGeneration.class, "");

        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Template template = cfg.getTemplate("AnnotationProcessor.ftlj");

        ClassBean processorClassBean = new ClassBean("org.liman.test.tryy", "TestAnnotationProcessor");

        ProcessorBean bean = new ProcessorBean(processorClassBean, List.of(
                new ClassBean("org.liman.test", "Id")
        ));

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
