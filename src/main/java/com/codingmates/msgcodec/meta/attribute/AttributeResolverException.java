package com.codingmates.msgcodec.meta.attribute;

/**
 * An exception thrown during {@link Attribute} resolution.
 *
 * @author Gary Tierney
 */
public class AttributeResolverException extends Exception {
	public AttributeResolverException(String message, Throwable cause) {
		super(message, cause);
	}

	public AttributeResolverException(String message) {
		super(message);
	}
}
