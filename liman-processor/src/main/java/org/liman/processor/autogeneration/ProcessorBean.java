package org.liman.processor.autogeneration;

import org.liman.MessageLevel;

import java.util.List;

public class ProcessorBean {
    private final ClassBean processorClass;
    private final List<String> annotations;
    private final MessageLevel maxMessageLevel;

    public ProcessorBean(ClassBean processorClass, List<String> annotations, MessageLevel maxMessageLevel) {
        this.processorClass = processorClass;
        this.annotations = annotations;
        this.maxMessageLevel = maxMessageLevel;
    }

    public ClassBean getProcessorClass() {
        return processorClass;
    }

    public List<String> getAnnotations() {
        return annotations;
    }

    public MessageLevel getMaxMessageLevel() {
        return maxMessageLevel;
    }

}
