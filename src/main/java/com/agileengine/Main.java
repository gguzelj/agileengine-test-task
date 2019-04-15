package com.agileengine;

import com.agileengine.exception.AttributeNotFoundException;

public class Main {



    public static void main(String[] args) {

        try {
            ElementDescriptor elementDescriptor = InputProcessor.findAttributes("make-everything-ok-button",
                "/resources/sample-0-origin.html");


            elementDescriptor.getAttributes().forEach((key, value) ->
            {
                System.out.println(key + " " + value);
            });
        } catch (AttributeNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }


        System.out.println("aa");
    }
}
