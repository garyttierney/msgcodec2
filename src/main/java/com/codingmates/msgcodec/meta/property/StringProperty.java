package com.codingmates.msgcodec.meta.property;

import com.google.common.base.MoreObjects;

/**
 * A {@link Property} that is a simple String literal.
 *
 * @author Toby Scrace
 */
public final class StringProperty extends Property {

	/**
	 * The value of this StringProperty.
	 */
	private final String value;

	/**
	 * Creates the StringProperty.
	 *
	 * @param name The name of the StringProperty. Must not be {@code null}.
	 * @param value The value of the StringProperty. Must not be {@code null}.
	 */
	public StringProperty(String name, String value) {
		super(name);
		this.value = value;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("name", name).add("value", value).toString();
	}

	/**
	 * Gets the value of this StringProperty.
	 *
	 * @return The value. Will never be {@code null}.
	 */
	public String getValue() {
		return value;
	}

}
