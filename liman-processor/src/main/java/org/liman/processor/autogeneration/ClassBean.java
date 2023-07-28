package org.liman.processor.autogeneration;

public class ClassBean {
    private final String packageName;
    private final String className;

    public ClassBean(String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }
}
