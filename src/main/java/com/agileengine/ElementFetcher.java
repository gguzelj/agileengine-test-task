package com.agileengine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

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

            return attributes.keySet().stream()
                .map(key -> findElementsByAttribute(doc, key, attributes.get(key)))
                .flatMap(Collection::stream)
                .collect(toList());

        } catch (IOException e) {
            LOGGER.error("Error reading [{}] file", htmlFile.getAbsolutePath(), e);
            return new ArrayList<>();
        }
    }

    private static List<ElementDescriptor> findElementsByAttribute(Document doc, String key, String value) {
        return doc.getElementsByAttributeValue(key, value).stream()
            .map(Node::attributes)
            .map(elementAttribute -> elementAttribute.asList().stream().collect(toMap(Attribute::getKey, Attribute::getValue)))
            .map(ElementDescriptor::new)
            .collect(toList());
    }


}
