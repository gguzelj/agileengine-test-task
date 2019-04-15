package com.agileengine.service;

import com.agileengine.exception.HTMLFileNotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class DocumentReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentReader.class);
    private static final String CHARSET_NAME = "utf8";

    public static Document getDocument(String file) {
        File htmlFile = new File(file);
        Document document;

        try {
            document = Jsoup.parse(
                htmlFile,
                CHARSET_NAME,
                htmlFile.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.error("Error reading [{}] file", htmlFile.getAbsolutePath(), e);
            throw new HTMLFileNotFoundException("Unexpected error while reading file " + file);
        }
        return document;
    }
}
