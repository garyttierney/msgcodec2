package com.codingmates.msgcodec.meta;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MessageMetadataBuilder {
    private String name;
    private List<String> errors = new ArrayList<>();
    private List<MessageAnnotation> annotations = new ArrayList<>();
    private List<MessageAttribute> attributes = new ArrayList<>();

    public MessageMetadataBuilder error(String error) {
        errors.add(error);
        return this;
    }

    public MessageMetadataBuilder name(String name) {
        this.name = name;
        return this;
    }

    public MessageMetadataBuilder annotation(MessageAnnotation annotation) {
        annotations.add(annotation);
        return this;
    }

    public MessageMetadataBuilder attribute(MessageAttribute attribute) {
        attributes.add(attribute);
        return this;
    }

    public MessageMetadata build() {
        Preconditions.checkState(errors.size() == 0, "Invalid message declaration: "
                + Joiner.on("\n\t").join(errors));
        Preconditions.checkState(name.length() > 0, "Message name must be 1 or more " +
                "characters");
        Preconditions.checkState(attributes.size() > 0, "Messages must have 1 or more attributes");

        return new MessageMetadata(name, Collections.unmodifiableList(annotations),
                Collections.unmodifiableList(attributes));
    }
}
