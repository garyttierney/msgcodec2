package com.codingmates.msgcodec.data;

/**
 * Represents the different ways data properties can be transformed.
 *
 * @author Graham Edgecombe
 */
public enum DataTransformation {

	/**
	 * Adds 128 to the value when it is written, takes 128 from the value when it is read (also known as type-A).
	 */
	ADD,

	/**
	 * Negates the value (also known as type-C).
	 */
	NEGATE,

	/**
	 * No transformation is done.
	 */
	NONE,

	/**
	 * Subtracts the value from 128 (also known as type-S).
	 */
	SUBTRACT;

}