package com.codingmates.msgcodec.meta.util;

import com.codingmates.msgcodec.meta.MessageMetadata;

import java.util.Map;
import java.util.Optional;

public interface MessageMetadataResolver {
    default Optional<MessageMetadata> fromClass(Map<String, MessageMetadata> metadataMap, Class<?> clazz) {
        return fromClassName(metadataMap, clazz.getCanonicalName());
    }

    Optional<MessageMetadata> fromClassName(Map<String, MessageMetadata> metadataMap, String className);
}
