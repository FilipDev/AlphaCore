package me.pauzen.bukkitcommonpluginapi.scripting;

import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Script {

    private List<String> statements = new ArrayList<>();
    private String completeScript;

    public void readBook(BookMeta bookMeta) {
        StringBuilder scriptBuilder = new StringBuilder();
        for (String page : bookMeta.getPages()) {
            scriptBuilder.append(page);
            String[] statementsArray = page.split(";");
            Collections.addAll(statements, statementsArray);
        }
        completeScript = scriptBuilder.toString();
    }

    public List<String> getStatements() {
        return statements;
    }

    public String getCompleteScript() {
        return completeScript;
    }
}
