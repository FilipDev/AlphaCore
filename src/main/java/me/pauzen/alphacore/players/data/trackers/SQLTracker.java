/*
 *  Created by Filip P. on 3/24/15 10:41 PM.
 */

package me.pauzen.alphacore.players.data.trackers;

import me.pauzen.alphacore.players.data.Tracker;
import me.pauzen.alphacore.utils.sql.SQLConnection;
import me.pauzen.alphacore.utils.sql.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLTracker extends Tracker {

    private final Table         table;
    private final SQLConnection connection;
    private final UUID          uuid;
    private final String        value;

    public SQLTracker(SQLConnection connection, Table table, UUID uuid, String value, String id, int initialValue) {
        super(id, initialValue);
        this.connection = connection;
        this.table = table;
        this.uuid = uuid;
        this.value = value;
    }

    @Override
    public int getValue() {
        ResultSet query = connection.query("SELECT '" + value + "' FROM '" + table.getName() + "' WHERE uuid = " + uuid.toString());
        try {
            if (query.next()) {
                return query.getInt(value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void setValue(int value) {
        connection.execute("INSERT INTO '" + table.getName() + "' (UUID, " + value + ") VALUES (" + uuid.toString() + ", " + value + ")");
    }
}
