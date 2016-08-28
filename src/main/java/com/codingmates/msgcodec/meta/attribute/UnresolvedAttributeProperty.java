package com.codingmates.msgcodec.meta.attribute;

/**
 * A property of an {@link UnresolvedAttribute}.
 *
 * @author Gary Tierney
 */
public final class UnresolvedAttributeProperty {
    private final String name;
    private final Type type;
    private final Object value;

    public UnresolvedAttributeProperty(String name, Type type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    /**
     * Gets the name of this property.
     *
     * @return The property name.
     */
    public String name() {
        return name;
    }

    /**
     * Gets the {@link Type} of this property.
     *
     * @return The property {@code Type}..
     */
    public Type type() {
        return type;
    }

    /**
     * Gets the value of this property, either a string or integer.
     *
     * @return The property value. Will never be {@code null}.
     */
    public Object value() {
        return value;
    }

    /**
     * A type of {@link UnresolvedAttributeProperty}.
     */
    public static enum Type {
        STRING, INTEGER
    }
}
