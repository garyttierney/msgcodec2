package com.codingmates.msgcodec.meta;

import com.codingmates.msgcodec.meta.attribute.Attribute;
import com.codingmates.msgcodec.meta.property.Property;

import java.util.List;
import java.util.Map;

/**
 * A representation of a {@link Codec}s metadata.
 *
 * @author Gary Tierney
 */
public final class CodecMetadata {
	/**
	 * A mapping of {@link Attribute} classes to instances.
	 */
	private final Map<Class<? extends Attribute>, Attribute> attributeMap;

	/**
	 * A list of {@link Property}s in this codec.
	 */
	private final List<Property> properties;

	/**
	 * The name of this codec.
	 */
	private final String name;

	/**
	 * The object class of this codec.
	 */
	private final Class<?> objectClass;

	public CodecMetadata(String name, Class<?> objectClass,
						 Map<Class<? extends Attribute>, Attribute> attributeMap,
						 List<Property> properties) {
		this.name = name;
		this.objectClass = objectClass;
		this.attributeMap = attributeMap;
		this.properties = properties;
	}

	/**
	 * Checks if the given {@link Attribute} {@code type} is present.
	 *
	 * @param type The {@code Attribute} type.
	 * @return {@code true} if the attribute exists.
	 */
	public boolean hasAttribute(Class<? extends Attribute> type) {
		return attributeMap.containsKey(type);
	}

	/**
	 * Gets the attribute with the given {@code type}.
	 *
	 * @param type The {@code type} of attribute}.
	 * @return An instance of an {@link Attribute} with the class {@code type}.
	 */
	public <T extends Attribute> T attribute(Class<T> type) {
		return (T) attributeMap.get(type);
	}

	/**
	 * Gets the {@link Codec}s object {@code Class}.
	 *
	 * @return The object {@code Class}.
	 */
	public Class<?> objectClass() {
		return objectClass;
	}

	/**
	 * Gets the {@link Codec}s properties.
	 *
	 * @return The {@code Codec}s properties.
	 */
	public List<Property> properties() {
		return properties;
	}

	/**
	 * Gets the {@link Codec}s name.
	 *
	 * @return The {@code Codec}s name.
	 */
	public String name() {
		return name;
	}
}
