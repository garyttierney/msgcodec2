package com.codingmates.msgcodec.meta.property;

import com.codingmates.msgcodec.meta.CodecMetadata;

/**
 * A property belonging to a {@link CodecMetadata} instance. This is analogous to a name-value pair in JSON.
 * <p>
 * <code>
 * codec Type {
 * value_name: value // this line is a Property
 * }
 * </code>
 *
 * @author Toby Scrace
 */
public abstract class Property {

	/**
	 * The name of this Property.
	 */
	protected final String name;

	/**
	 * Creates the Property.
	 *
	 * @param name The name of the Property. Must not be {@code null}.
	 */
	protected Property(String name) {
		this.name = name;
	}

	/**
	 * Gets the name of this Property.
	 *
	 * @return The name. Will never be {@code null}.
	 */
	public String getName() {
		return name;
	}

}
