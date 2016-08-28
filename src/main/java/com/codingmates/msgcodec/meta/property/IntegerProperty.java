package com.codingmates.msgcodec.meta.property;

import com.google.common.base.MoreObjects;

/**
 * A {@link Property} that is a simple integer literal.
 *
 * @author Toby Scrace
 */
public final class IntegerProperty extends Property {

	/**
	 * The value of this IntegerProperty.
	 */
	private final long value;

	/**
	 * Creates the IntegerProperty.
	 *
	 * @param name The name of the IntegerProperty. Must not be {@code null}.
	 * @param value The value of the IntegerProperty.
	 */
	public IntegerProperty(String name, long value) {
		super(name);
		this.value = value;
	}

	/**
	 * Gets the value of this IntegerProperty.
	 *
	 * @return The value.
	 */
	public long getValue() {
		return value;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("name", name).add("value", value).toString();
	}

}
