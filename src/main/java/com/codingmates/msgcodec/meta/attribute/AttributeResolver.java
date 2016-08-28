package com.codingmates.msgcodec.meta.attribute;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A resolver for {@link UnresolvedAttribute}s which can map an unresolved attribute
 * to an instantiated attribute type.
 *
 * @author Gary Tierney
 */
public final class AttributeResolver {
    private Map<String, Class<? extends Attribute>> attributeMap;
    private LoadingCache<Class<? extends Attribute>, Map<String, PropertyDescriptor>> propertyDescriptorCache;

    /**
     * Creates a new {@link AttributeResolver}.
     *
     * @param attributeMap A mapping of attribute names to their respective {@link Attribute}
     * implementation.
     */
    public AttributeResolver(Map<String, Class<? extends Attribute>> attributeMap) {
        this.attributeMap = attributeMap;
        this.propertyDescriptorCache = CacheBuilder.newBuilder()
            .build(new CacheLoader<Class<? extends Attribute>, Map<String, PropertyDescriptor>>() {
                @Override
                public Map<String, PropertyDescriptor> load(Class<? extends Attribute> key) throws Exception {
                    return Arrays.stream(Introspector.getBeanInfo(key).getPropertyDescriptors())
                            .collect(Collectors.toMap(FeatureDescriptor::getName, Function.identity()));
                }
            });
    }

    /**
     * Resolves an {@link UnresolvedAttribute} to the type mapped by its name in
     * {@link #attributeMap}.
     *
     * @param attribute An {@link UnresolvedAttribute} to resolve.
     * @return A resolved {@link Attribute} instance.
     * @throws AttributeResolverException If there was no way to resolve the given {@code attribute}
     * to an {@link Attribute} instance..
     */
    public Attribute resolve(UnresolvedAttribute attribute) throws AttributeResolverException {
        if (!attributeMap.containsKey(attribute.name())) {
            throw new AttributeResolverException("No attribute found named " + attribute.name());
        }

        try {
            Class<? extends Attribute> attributeClass = attributeMap.get(attribute.name());
            Attribute resolvedAttribute = attributeClass.newInstance();

            Map<String, PropertyDescriptor> propertyDescriptors = propertyDescriptorCache.get(attributeClass);

            for (UnresolvedAttributeProperty property : attribute.properties()) {
                PropertyDescriptor descriptor = propertyDescriptors.get(property.name());
                Method writeMethod = descriptor.getWriteMethod();
                writeMethod.invoke(resolvedAttribute, property.value());
            }

            return resolvedAttribute;
        } catch (ExecutionException e) {
            throw new AttributeResolverException("Unable to lookup bean info for class", e.getCause());
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new AttributeResolverException("Unable to instantiate attribute class", e.getCause());
        }
    }
}
