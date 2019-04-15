package com.agileengine;

import com.agileengine.domain.ElementDescriptor;
import com.agileengine.domain.ElementScore;

import java.util.Map;

public class ElementSimilarityComparator {

    /**
     * First dummy scorer, based in the distance of jaccard (sort of..).
     * The basic idea is to use the size of the intersection as a measure similarity
     *
     * @param targetId
     * @param element
     * @return
     */
    public static ElementScore calculate(ElementDescriptor targetId, ElementDescriptor element) {
        return new ElementScore(element, calculateScore(targetId.getAttributes(), element.getAttributes()));
    }

    private static Double calculateScore(Map<String, String> target, Map<String, String> attributes) {
        Integer matched = target.keySet().stream()
            .map(key -> attributes.containsKey(key) && attributes.get(key).equals(target.get(key)) ? 1 : 0)
            .reduce((one, other) -> one + other)
            .orElse(0);

        return ((double) matched / target.keySet().size());
    }
}
