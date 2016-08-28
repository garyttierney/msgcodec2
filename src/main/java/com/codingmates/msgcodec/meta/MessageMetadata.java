package com.codingmates.msgcodec.meta;

import com.codingmates.msgcodec.meta.annotation.UnknownAnnotation;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class MessageMetadata {
    private final Map<Class<? extends MessageAnnotation>, MessageAnnotation> resolvedAnnotationsMap;
    private final List<MessageAttribute> attributesList;
    private final String name;

    public MessageMetadata(String name, List<MessageAnnotation> annotationsList, List<MessageAttribute> attributesList) {
        this.name = name;
        this.resolvedAnnotationsMap = annotationsList.stream()
                .filter(annotation -> !annotation.getClass().equals(UnknownAnnotation.class))
                .collect(Collectors.toMap(MessageAnnotation::getClass, Function.identity()));
        this.attributesList = attributesList;
    }

    public boolean hasAnnotation(Class<? extends MessageAnnotation> type) {
        return resolvedAnnotationsMap.containsKey(type);
    }

    public <T extends MessageAnnotation> T getAnnotation(Class<T> type) {
        return (T) resolvedAnnotationsMap.get(type);
    }

    public List<MessageAttribute> getAttributesList() {
        return attributesList;
    }

    public String getName() {
        return name;
    }
}
