package org.liman.processor.autogeneration;

import java.util.List;

public class ProcessorBean {
    private final ClassBean processorClass;
    private final List<String> annotations;

    public ProcessorBean(ClassBean processorClass, List<String> annotations) {
        this.processorClass = processorClass;
        this.annotations = annotations;
    }

    public ClassBean getProcessorClass() {
        return processorClass;
    }

    public List<String> getAnnotations() {
        return annotations;
    }

}
