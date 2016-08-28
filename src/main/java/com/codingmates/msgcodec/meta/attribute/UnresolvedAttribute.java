package com.codingmates.msgcodec.meta.attribute;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * An attribute which is unresolved, and created during the parsing stage.
 *
 * @author Gary Tierney
 */
public final class UnresolvedAttribute {

    /**
     * The name of this attribute.
     */
    private final String name;

    /**
     * A list of this attributes properties.
     */
    private final List<UnresolvedAttributeProperty> properties;

    public UnresolvedAttribute(String name, List<UnresolvedAttributeProperty> properties) {
        this.name = name;
        this.properties = properties;
    }

    /**
     * Gets the name of this attribute.
     *
     * @return The attribute name.
     */
    public String name() {
        return name;
    }

    /**
     * Gets this attributes properties.
     *
     * @return The attribute properties.
     */
    public List<UnresolvedAttributeProperty> properties() {
        return ImmutableList.copyOf(properties);
    }
}
