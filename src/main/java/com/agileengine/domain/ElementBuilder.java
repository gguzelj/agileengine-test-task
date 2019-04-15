package com.agileengine.domain;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.Map;

import static java.util.Collections.reverse;
import static java.util.stream.Collectors.*;

public class ElementBuilder {

    public static ElementDescriptor newElementDescriptor(Element element) {
        return new ElementDescriptor(buildAttributes(element), buildLabel(element));
    }

    private static Map<String, String> buildAttributes(Element element) {
        return element.attributes().asList().stream().collect(toMap(Attribute::getKey, Attribute::getValue));
    }

    private static String buildLabel(Element element) {
        List<String> tags = element.parents().stream().map(ElementBuilder::getTagName).collect(toList());
        reverse(tags);
        tags.add(element.tagName());
        return tags.stream().collect(joining(" > "));
    }

    /**
     * Method that returns the label for a specific element.
     * If the element has siblings of the same type (i.e div), also returns it's position
     */
    private static String getTagName(Element element) {
        String tagName = element.tagName();

        List<Element> siblings = element.parent().children().stream()
            .filter(e -> e.tagName().equals(element.tagName()))
            .collect(toList());

        if (siblings.size() > 1) {
            tagName += "[" + siblings.indexOf(element) + "]";
        }

        return tagName;
    }
}
