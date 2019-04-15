package com.agileengine.domain;

import java.util.Map;

public class ElementDescriptor {

    private final Map<String, String> attributes;

    public ElementDescriptor(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }
}
