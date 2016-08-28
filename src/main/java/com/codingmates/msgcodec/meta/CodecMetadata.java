package com.codingmates.msgcodec.meta;

import com.codingmates.msgcodec.meta.attribute.Attribute;
import com.codingmates.msgcodec.meta.property.Property;

import java.util.List;
import java.util.Map;

public final class CodecMetadata {
    private final Map<Class<? extends Attribute>, Attribute> attributeMap;
    private final List<Property> properties;
    private final String name;
    private final Class<?> objectClass;

    public CodecMetadata(String name, Class<?> objectClass,
                         Map<Class<? extends Attribute>, Attribute> attributeMap,
                         List<Property> properties) {
        this.name = name;
        this.objectClass = objectClass;
        this.attributeMap = attributeMap;
        this.properties = properties;
    }

    public boolean hasAnnotation(Class<? extends Attribute> type) {
        return attributeMap.containsKey(type);
    }

    public <T extends Attribute> T annotation(Class<T> type) {
        return (T) attributeMap.get(type);
    }

    public Class<?> objectClass() {
        return objectClass;
    }

    public List<Property> properties() {
        return properties;
    }

    public String name() {
        return name;
    }
}
