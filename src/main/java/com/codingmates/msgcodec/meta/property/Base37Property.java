package com.codingmates.msgcodec.meta.property;

import com.codingmates.msgcodec.meta.property.Property;
import com.google.common.base.MoreObjects;

/**
 * A {@link Property} for a String encoded in base-37, meaning it must be deserialized from a {@code long}.
 *
 * @author Toby Scrace
 */
public final class Base37Property extends Property {

	/**
	 * Creates the Base37Property.
	 *
	 * @param name The name of the Base37Property. Must not be {@code null}.
	 */
	public Base37Property(String name) {
		super(name);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("name", name).toString();
	}

}
