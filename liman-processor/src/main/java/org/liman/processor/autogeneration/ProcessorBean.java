package org.liman.processor.autogeneration;

import java.util.List;

public class ProcessorBean {
    private final ClassBean processorClass;
    private final List<ClassBean> annotations;

    public ProcessorBean(ClassBean processorClass, List<ClassBean> annotations) {
        this.processorClass = processorClass;
        this.annotations = annotations;
    }

    public ClassBean getProcessorClass() {
        return processorClass;
    }

    public List<ClassBean> getAnnotations() {
        return annotations;
    }

}
