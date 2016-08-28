package com.codingmates.msgcodec.meta.util;

import com.codingmates.msgcodec.meta.MessageMetadata;
import com.codingmates.msgcodec.meta.annotation.JavaClassAnnotation;

public final class AnnotationMessageClassResolver implements MessageClassResolver {
    @Override
    public Class fromMetadata(MessageMetadata metadata) throws ClassNotFoundException {
        if (!metadata.hasAnnotation(JavaClassAnnotation.class)) {
            throw new ClassNotFoundException();
        }

        JavaClassAnnotation annotation = metadata.getAnnotation(JavaClassAnnotation.class);
        return Class.forName(annotation.getClassName());
    }
}
