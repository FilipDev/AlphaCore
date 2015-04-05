/*
 *  Created by Filip P. on 4/5/15 1:40 PM.
 */

package me.pauzen.alphacore.utils;

import java.util.HashMap;
import java.util.Map;

public final class Properties {

    private Properties() {
    }

    public static Map<String, String> getProperties(String compiledProperties) {
        Map<String, String> propertyMap = new HashMap<>();
        for (String property : compiledProperties.split("&")) {
            String[] propertyStringArray = property.split(":");
            String key = propertyStringArray[0], value = propertyStringArray[1];
            propertyMap.put(key, value);
        }
        return propertyMap;
    }

    public static String compileProperties(Map<String, String> propertyMap) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> propertyEntry : propertyMap.entrySet()) {
            if (!builder.toString().isEmpty()) {
                builder.append("&");
            }
            builder.append(propertyEntry.getKey());
            builder.append(":");
            builder.append(propertyEntry.getValue());
        }
        return builder.toString();
    }
}
