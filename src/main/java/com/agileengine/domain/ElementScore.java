package com.agileengine.domain;

public class ElementScore {

    private final ElementDescriptor elementDescriptor;
    private final Double score;

    public ElementScore(ElementDescriptor elementDescriptor, Double score) {
        this.elementDescriptor = elementDescriptor;
        this.score = score;
    }

    public ElementDescriptor getElementDescriptor() {
        return elementDescriptor;
    }

    public Double getScore() {
        return score;
    }
}
