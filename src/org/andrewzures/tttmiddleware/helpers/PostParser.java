package org.andrewzures.tttmiddleware.helpers;

import org.andrewzures.java_server.Request;

import java.util.HashMap;

public class PostParser {
    public String getFormBody(Request request) {
        String result = "";
        while (request.inputReader.charIsAvailable()) {
            result += (char) request.inputReader.readNextChar();
        }
        return result;
    }

    public HashMap<String, String> parsePostHash(String postBody) {
        HashMap<String, String> postMap = new HashMap<String, String>();
        String[] keyValuePairs = getKeyValuePairs(postBody);
        for (String pair : keyValuePairs) {
            pair = pair.replaceAll("\\+", " ");
            String[] splitElements = pair.split("=");
            if (splitElements.length > 1) {
                postMap.put(splitElements[0], splitElements[1]);
            }
        }
        return postMap;
    }

    public String[] getKeyValuePairs(String postHash) {
        return postHash.split("&");
    }
}
