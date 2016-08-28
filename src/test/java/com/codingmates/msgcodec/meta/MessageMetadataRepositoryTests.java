package com.codingmates.msgcodec.meta;

import com.codingmates.msgcodec.meta.util.MessageMetadataResolver;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class MessageMetadataRepositoryTests {
    private static class TestMessageClass {
    }

    private static MessageMetadataResolver stubResolver(Map<String, MessageMetadata> classToMetadataMap) {
        return (metadataMap, className) -> Optional.ofNullable(classToMetadataMap.get(className));
    }

    private static MessageMetadata stubMetadata(String name) {
        return new MessageMetadata(name, Collections.emptyList(), Collections.emptyList());
    }

    /**
     * Tests that {@link MessageMetadataRepository#get(Class)} throws an exception when no class mapping
     * is found.
     */
    @Test(expected = MessageMetadataRepositoryException.class)
    public void noClassMapping() throws Exception {
        MessageMetadataRepository repository = new MessageMetadataRepository(stubResolver(Collections.emptyMap()),
                Collections.emptyMap());

        repository.get(TestMessageClass.class);
    }

    /**
     * Tests that {@link MessageMetadataRepository#get(Class)} successfully gets a {@link MessageMetadata}
     * using a {@link MessageMetadataResolver}.
     */
    @Test
    public void hasClassMapping() throws Exception {
        Map<String, MessageMetadata> classToMetadataMap = new HashMap<>();
        classToMetadataMap.put(TestMessageClass.class.getCanonicalName(), stubMetadata("test"));

        MessageMetadataRepository repository = new MessageMetadataRepository(stubResolver(classToMetadataMap),
                Collections.emptyMap());
        MessageMetadata metadata = repository.get(TestMessageClass.class);

        assertEquals("test", metadata.getName());
    }
}