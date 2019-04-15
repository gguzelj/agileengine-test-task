package com.agileengine;

import com.agileengine.domain.ElementDescriptor;
import com.agileengine.domain.ElementScore;
import com.agileengine.exception.AttributeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.agileengine.service.ElementFetcher.findSimilarElements;
import static com.agileengine.service.InputProcessor.findElementById;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private static final String TARGET_ID = "make-everything-ok-button";

    public static void main(String[] args) {
        try {
            doRun(args[0], args[1]);
        } catch (AttributeNotFoundException e) {
            LOGGER.error("Unexpected error while processing files", e);
            System.exit(1);
        }
    }

    private static void doRun(String originFile, String otherSampleFile) throws AttributeNotFoundException {
        ElementDescriptor targetElement = findElementById(TARGET_ID, originFile);
        List<ElementDescriptor> matched = findSimilarElements(otherSampleFile, targetElement);

        matched.stream()
            .map(element -> ElementSimilarityComparator.calculate(targetElement, element))
            .min(comparing(ElementScore::getScore, reverseOrder()))
            .ifPresent(Main::printElement);
    }

    private static void printElement(ElementScore elementScore) {
        ElementDescriptor elementDescriptor = elementScore.getElementDescriptor();
        LOGGER.info("The selected element was: {}", elementDescriptor.getLabel());
    }

}
