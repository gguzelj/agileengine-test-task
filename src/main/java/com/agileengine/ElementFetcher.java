package com.agileengine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

public class ElementFetcher {

    private static Logger LOGGER = LoggerFactory.getLogger(ElementFetcher.class);
    private static String CHARSET_NAME = "utf8";

    /**
     * Takes an ElementDescriptor as input criteria and fetch all elements that match any attribute value
     * @param targetFile
     * @param elementDescriptor
     * @return
     */
    public static List<ElementDescriptor> findElementsByElementDescriptor(String targetFile, ElementDescriptor elementDescriptor) {
        final File htmlFile = new File(targetFile);
        final Map<String, String> attributes = elementDescriptor.getAttributes();

        try {
            final Document doc = Jsoup.parse(
                htmlFile,
                CHARSET_NAME,
                htmlFile.getAbsolutePath());

            HashSet<Element> fetchedElements = new HashSet<>();

            attributes.forEach((key, value) -> {
                fetchedElements.addAll(findElementsByAttribute2(doc, key, attributes.get(key)));
            });

            return fetchedElements.stream()
                .map(Node::attributes)
                .map(elementAttribute -> elementAttribute.asList().stream().collect(toMap(Attribute::getKey, Attribute::getValue)))
                .map(ElementDescriptor::new)
                .collect(toList());
        } catch (IOException e) {
            LOGGER.error("Error reading [{}] file", htmlFile.getAbsolutePath(), e);
            return new ArrayList<>();
        }
    }

    private static Set<Element> findElementsByAttribute2(Document doc, String key, String value) {
        return new HashSet<>(doc.getElementsByAttributeValue(key, value));
    }
}
