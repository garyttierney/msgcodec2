package com.codingmates.msgcodec.meta;

import com.codingmates.msgcodec.meta.attribute.Attribute;

import java.util.Map;

/**
 * Maps a {@link Codec} to a java {@code Class}, optionally using resolved attributes.
 */
public interface CodecClassMapper {
	Class toClass(Codec codec, Map<Class<? extends Attribute>, Attribute> attributeMap)
		throws ClassNotFoundException;
}
