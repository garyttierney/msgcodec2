package com.codingmates.msgcodec.meta;

import com.codingmates.msgcodec.meta.attribute.Attribute;
import com.codingmates.msgcodec.meta.attribute.AttributeResolver;
import com.codingmates.msgcodec.meta.attribute.AttributeResolverException;
import com.codingmates.msgcodec.meta.attribute.UnresolvedAttribute;
import com.codingmates.msgcodec.meta.property.Property;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/**
 * A factory for resolving attributes and mapping parsed {@link Codec}s
 * to java classes.
 *
 * @author Gary Tierney
 */
public final class CodecMetadataFactory {
    private final AttributeResolver attributeResolver;
    private final CodecClassMapper classMapper;

    public CodecMetadataFactory(CodecClassMapper classMapper,
                                AttributeResolver attributeResolver) {
        this.classMapper = classMapper;
        this.attributeResolver = attributeResolver;
    }

    public CodecMetadata create(Codec codec) throws CodecMetadataFactoryException {
        ImmutableMap.Builder<Class<? extends Attribute>, Attribute> attributes =
                ImmutableMap.builder();

        for (UnresolvedAttribute attribute : codec.attributes()) {
            try {
                Attribute resolvedAttribute = attributeResolver.resolve(attribute);
                attributes.put(resolvedAttribute.getClass(), resolvedAttribute);
            } catch (AttributeResolverException e) {
                throw new CodecMetadataFactoryException(e);
            }
        }

        Map<Class<? extends Attribute>, Attribute> attributeMap = attributes.build();
        List<Property> properties = ImmutableList.copyOf(codec.values());

        try {
            Class objectClass = classMapper.toClass(codec, attributeMap);
            return new CodecMetadata(codec.name(), objectClass, attributeMap, properties);
        } catch (ClassNotFoundException e) {
            throw new CodecMetadataFactoryException(e);
        }
    }
}
