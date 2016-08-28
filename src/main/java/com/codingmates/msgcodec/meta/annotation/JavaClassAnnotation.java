package com.codingmates.msgcodec.meta.annotation;

import com.codingmates.msgcodec.meta.MessageAnnotation;

public final class JavaClassAnnotation implements MessageAnnotation {
    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
