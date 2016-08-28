package com.codingmates.msgcodec.meta.util;

import com.codingmates.msgcodec.meta.MessageMetadata;
import com.codingmates.msgcodec.meta.annotation.JavaClassAnnotation;

import java.util.Map;
import java.util.Optional;

public final class AnnotationMessageMetadataResolver implements MessageMetadataResolver {
    @Override
    public Optional<MessageMetadata> fromClassName(Map<String, MessageMetadata> metadataMap, String className) {
        return metadataMap.values().stream()
                .filter(meta -> meta.hasAnnotation(JavaClassAnnotation.class))
                .filter(meta -> meta.getAnnotation(JavaClassAnnotation.class).getClassName().equals(className))
                .findFirst();
    }
}
