package com.agileengine.domain;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;

import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

public class ElementDescriptor {

    private final Map<String, String> attributes;
    private final String label;

    public ElementDescriptor(Element element) {
        this.attributes = element.attributes().asList().stream().collect(toMap(Attribute::getKey, Attribute::getValue));
        this.label = element.parents().stream().map(Element::tagName).collect(Collectors.joining(" > "));
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public String getLabel() {
        return label;
    }
}
