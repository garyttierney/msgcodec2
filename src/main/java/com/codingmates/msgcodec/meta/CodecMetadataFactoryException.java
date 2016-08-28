package com.codingmates.msgcodec.meta;

/**
 * An {@code Exception} thrown during creation of {@link CodecMetadata}.
 *
 * @author Gary Tierney
 */
public final class CodecMetadataFactoryException extends Exception {
	public CodecMetadataFactoryException(String message) {
		super(message);
	}

	public CodecMetadataFactoryException(String message, Throwable cause) {
		super(message, cause);
	}
}
