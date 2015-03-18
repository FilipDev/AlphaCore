/*
 *  Created by Filip P. on 3/17/15 5:49 PM.
 */

package me.pauzen.alphacore.utils.json;

import java.util.HashMap;
import java.util.Map;

public class Text {

    private Map<Property, Value> properties = new HashMap<>();

    public void applyProperty(Property property, Value value) {
        properties.put(property, value);
    }

    public String asJSON() {
        StringBuilder stringBuilder = new StringBuilder("{");
        boolean appendComma = false;
        for (Map.Entry<Property, Value> propertyValueEntry : properties.entrySet()) {
            if (appendComma) {
                stringBuilder.append(",");
            }
            stringBuilder.append(propertyValueEntry.getKey().asJSON(propertyValueEntry.getValue().getValue()));
            appendComma = true;
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

}
