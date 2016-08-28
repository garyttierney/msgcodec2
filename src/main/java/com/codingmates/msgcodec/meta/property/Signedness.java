package com.codingmates.msgcodec.meta.property;

/**
 * The signedness of a piece of data being decoded.
 *
 * @author Toby Scrace
 */
public enum Signedness {

	/**
	 * Indicates the data is signed (i.e. can be negative).
	 */
	SIGNED,

	/**
	 * Indicates the data is unsigned (i.e. can only be positive).
	 */
	UNSIGNED,

}