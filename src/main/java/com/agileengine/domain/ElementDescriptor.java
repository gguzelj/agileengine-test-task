package com.agileengine.domain;

import java.util.Map;

/***
 * A ElementDescriptor represents an HTML element.
 * Have the basic information needed for this problem, as the attributes, and the label to print
 */
public class ElementDescriptor {

    private final Map<String, String> attributes;
    private final String label;

    public ElementDescriptor(Map<String, String> attributes, String label) {
        this.attributes = attributes;
        this.label = label;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public String getLabel() {
        return label;
    }
}
