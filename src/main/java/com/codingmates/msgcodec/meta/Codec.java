package com.codingmates.msgcodec.meta;

import com.codingmates.msgcodec.meta.attribute.UnresolvedAttribute;
import com.codingmates.msgcodec.meta.property.Property;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * @author Toby Scrace
 */
public final class Codec {
	private final String name;
	private final List<UnresolvedAttribute> attributes;
	private final List<Property> properties;

	public Codec(String name, List<UnresolvedAttribute> attributes, List<? extends Property> properties) {
		this.name = name;
		this.attributes = ImmutableList.copyOf(attributes);
		this.properties = ImmutableList.copyOf(properties);
	}

	public List<UnresolvedAttribute> attributes() {
		return attributes;
	}

	public String name() {
		return name;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("name", name).add("properties", properties).toString();
	}

	public List<Property> properties() {
		return properties;
	}

}
