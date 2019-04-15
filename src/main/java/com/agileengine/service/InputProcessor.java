package com.agileengine.service;

import com.agileengine.domain.ElementBuilder;
import com.agileengine.domain.ElementDescriptor;
import com.agileengine.exception.AttributeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Optional.ofNullable;

public class InputProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(InputProcessor.class);

    /**
     * Return the ElementDescriptor that belongs to the element id
     *
     * @param id
     * @param resourcePath
     * @return
     * @throws AttributeNotFoundException
     */
    public static ElementDescriptor findElementById(String id, String resourcePath) throws AttributeNotFoundException {
        LOGGER.info("Searching for element {} within file {}", id, resourcePath);

        return ofNullable(DocumentReader.getDocument(resourcePath).getElementById(id))
            .map(ElementBuilder::newElementDescriptor)
            .orElseThrow(() -> new AttributeNotFoundException("No attribute with id " + id + " was found"));
    }

}
