package com.agileengine.service;

import com.agileengine.domain.ElementDescriptor;
import com.agileengine.exception.AttributeNotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

public class InputProcessor {

    private static Logger LOGGER = LoggerFactory.getLogger(InputProcessor.class);
    private static String CHARSET_NAME = "utf8";


    public static ElementDescriptor findElementById(String targetElementId, String resourcePath) throws AttributeNotFoundException {
        return findElementById(new File(resourcePath), targetElementId)
            .map(Node::attributes)
            .map(attribute -> attribute.asList().stream().collect(toMap(Attribute::getKey, Attribute::getValue)))
            .map(ElementDescriptor::new)
            .orElseThrow(() -> new AttributeNotFoundException("No attribute with id" + targetElementId + " was found"));
    }

    private static Optional<Element> findElementById(File htmlFile, String targetElementId) {
        try {
            Document doc = Jsoup.parse(
                htmlFile,
                CHARSET_NAME,
                htmlFile.getAbsolutePath());

            return Optional.of(doc.getElementById(targetElementId));

        } catch (IOException e) {
            LOGGER.error("Error reading [{}] file", htmlFile.getAbsolutePath(), e);
            return Optional.empty();
        }
    }
}
