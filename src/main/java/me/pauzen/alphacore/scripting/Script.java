/*
 *  Created by Filip P. on 2/2/15 11:14 PM.
 */

package me.pauzen.alphacore.scripting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Script {

    private List<String> statements = new ArrayList<>();
    private String completeScript;

    public void readString(String script) {
        String[] statementsArray = script.split(";");
        Collections.addAll(statements, statementsArray);
    }

    public List<String> getStatements() {
        return statements;
    }

    public String getCompleteScript() {
        return completeScript;
    }
}
