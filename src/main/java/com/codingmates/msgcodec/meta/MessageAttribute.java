package com.codingmates.msgcodec.meta;

public final class MessageAttribute {
    private final String name;
    private final String type;

    public MessageAttribute(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
