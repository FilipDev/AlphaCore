/*
 *  Created by Filip P. on 2/21/15 12:59 PM.
 */

package me.pauzen.alphacore.utils.sql;

import java.util.HashMap;
import java.util.Map;

public class Table {

    private String name;
    private Map<String, String> types = new HashMap<>();

    public Table(String name) {
        this.name = name;
    }

    public Table type(String column, String type) {
        types.put(column, type);
        return this;
    }

    public void create(SQLConnection connection) {
        connection.createTable(this);
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getTypes() {
        return types;
    }
}
