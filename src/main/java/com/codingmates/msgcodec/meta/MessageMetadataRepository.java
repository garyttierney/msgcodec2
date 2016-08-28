package com.codingmates.msgcodec.meta;

import com.codingmates.msgcodec.meta.util.MessageMetadataResolver;

import java.util.Collections;
import java.util.Map;

public final class MessageMetadataRepository {

    private MessageMetadataResolver identifierResolver;
    private Map<String, MessageMetadata> messageMetadataMap;

    public MessageMetadataRepository(MessageMetadataResolver identifierResolver,
                                     Map<String, MessageMetadata> messageMetadataMap) {
        this.identifierResolver = identifierResolver;
        this.messageMetadataMap = messageMetadataMap;
    }


    public MessageMetadata get(String identifier) throws MessageMetadataRepositoryException {
        if (!messageMetadataMap.containsKey(identifier)) {
            throw new MessageMetadataRepositoryException("No metadata for identifier " + identifier);
        }

        return messageMetadataMap.get(identifier);
    }

    public MessageMetadata get(Class<?> clazz) throws MessageMetadataRepositoryException {
        return identifierResolver
                .fromClass(Collections.unmodifiableMap(messageMetadataMap), clazz)
                .orElseThrow(() -> new MessageMetadataRepositoryException("No metadata for class "
                        + clazz.getCanonicalName()));
    }
}
