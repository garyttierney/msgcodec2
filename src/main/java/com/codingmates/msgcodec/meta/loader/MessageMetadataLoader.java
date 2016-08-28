package com.codingmates.msgcodec.meta.loader;

import com.codingmates.msgcodec.meta.MessageMetadata;

import java.io.IOException;
import java.util.Map;

public interface MessageMetadataLoader {
    Map<String, MessageMetadata> load() throws IOException;
}
