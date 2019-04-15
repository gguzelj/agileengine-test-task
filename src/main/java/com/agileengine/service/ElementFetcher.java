package com.agileengine.service;

import com.agileengine.domain.ElementBuilder;
import com.agileengine.domain.ElementDescriptor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ElementFetcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElementFetcher.class);

    /**
     * Takes an ElementDescriptor as a criteria and fetch all elements that match any attribute value
     *
     * @param otherSampleFile
     * @param elementDescriptor
     * @return
     */
    public static List<ElementDescriptor> findSimilarElements(String otherSampleFile, ElementDescriptor elementDescriptor) {
        LOGGER.info("Searching for similar elements within file {}", otherSampleFile);

        final Document document = DocumentReader.getDocument(otherSampleFile);
        final Map<String, String> attributes = elementDescriptor.getAttributes();

        return attributes.keySet().stream()
            .map(key -> findElementsByAttribute(document, key, attributes.get(key)))
            .flatMap(Set::stream)
            .map(ElementBuilder::newElementDescriptor)
            .collect(toList());
    }

    private static Set<Element> findElementsByAttribute(Document doc, String key, String value) {
        return new HashSet<>(doc.getElementsByAttributeValue(key, value));
    }
}
