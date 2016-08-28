package com.codingmates.msgcodec.meta.attribute.impl;

import com.codingmates.msgcodec.meta.attribute.Attribute;
import com.codingmates.msgcodec.meta.Codec;
import com.codingmates.msgcodec.meta.CodecClassMapper;

import java.util.Map;

/**
 * A {@link Attribute} which maps a codec to a Java {@link Class}.
 *
 * @author Gary Tierney
 */
public final class JavaClass implements Attribute {
	/**
	 * The canonical name of the {@link Class}.
	 */
	private String className;

	/**
	 * Gets the canonical name of the {@link Class} this attribute represents.
	 *
	 * @return The canonical class name.
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * Sets the canonical name of the {@link Class} this attribute represents.
	 *
	 * @param className The canonical class name.
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * A {@link CodecClassMapper} implementation which uses the {@link JavaClass} attribute
	 * to map a {@link Codec} to a {@code Class}.
	 */
	public static class ClassMapper implements CodecClassMapper {
		@Override
		public Class toClass(Codec codec, Map<Class<? extends Attribute>, Attribute> attributeMap) throws ClassNotFoundException {
			if (!attributeMap.containsKey(JavaClass.class)) {
				throw new ClassNotFoundException();
			}

			JavaClass attribute = (JavaClass) attributeMap.get(JavaClass.class);
			return Class.forName(attribute.getClassName());
		}
	}
}
