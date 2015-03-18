/*
 *  Created by Filip P. on 3/17/15 5:30 PM.
 */

package me.pauzen.alphacore.utils.json;

public class JSONBuilder {
    
    private StringBuilder json;
    
    public String getJson() {
        return json.toString();
    }
    
    public JSONBuilder() {
        json = new StringBuilder();
        json.append("{");
        json.append("\"text\": \"\",\"extra\": [%s]");
        json.append("}");
    }
    
    public JSONBuilder addText(Text text) {
        json = new StringBuilder(String.format(json.toString(), text.asJSON() + ",%s"));
        return this;
    }
    
}
