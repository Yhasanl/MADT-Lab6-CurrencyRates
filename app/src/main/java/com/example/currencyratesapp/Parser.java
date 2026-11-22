package com.example.currencyratesapp;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Parser {

    public static List<String> parseCurrencyXml(InputStream stream) throws Exception {
        List<String> currencyList = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(stream);
        document.getDocumentElement().normalize();

        NodeList itemNodes = document.getElementsByTagName("item");

        for (int i = 0; i < itemNodes.getLength(); i++) {
            Element itemElement = (Element) itemNodes.item(i);

            String currencyCode = getTagValue("targetCurrency", itemElement);
            String rateStr = getTagValue("exchangeRate", itemElement);

            if (currencyCode != null && rateStr != null) {
                double rate = Double.parseDouble(rateStr.replace(",", ""));
                String formattedEntry = String.format(Locale.US, "%s - %.3f", currencyCode, rate);
                currencyList.add(formattedEntry);
            }
        }
        return currencyList;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }
}