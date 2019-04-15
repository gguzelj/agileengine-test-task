package com.agileengine;

import com.agileengine.domain.ElementDescriptor;
import com.agileengine.domain.ElementScore;
import com.agileengine.exception.AttributeNotFoundException;
import com.agileengine.service.ElementFetcher;
import com.agileengine.service.InputProcessor;

import java.util.List;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

public class Main {

    private static String TARGET_ID = "make-everything-ok-button";

    public static void main(String[] args) {
        try {
            doRun(args[0], args[1]);
        } catch (AttributeNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void doRun(String target, String resource) throws AttributeNotFoundException {
        ElementDescriptor targetId = InputProcessor.findElementById(TARGET_ID, target);
        List<ElementDescriptor> matched = ElementFetcher.findElementsByElementDescriptor(resource, targetId);

        matched.stream()
            .map(element -> ElementSimilarityComparator.calculate(targetId, element))
            .min(comparing(ElementScore::getScore, reverseOrder()))
            .ifPresent(Main::printElement);
    }

    private static void printElement(ElementScore elementScore) {
        ElementDescriptor elementDescriptor = elementScore.getElementDescriptor();
        System.out.println("The selected element was:");
        System.out.println(elementDescriptor.getLabel());
    }

}
