package com.codingmates.msgcodec.meta.util;

import com.codingmates.msgcodec.meta.MessageMetadata;

public interface MessageClassResolver {
    Class<?> fromMetadata(MessageMetadata metadata) throws ClassNotFoundException;
}
