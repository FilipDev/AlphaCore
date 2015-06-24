/*
 *  Created by Filip P. on 2/21/15 12:25 PM.
 */

package me.pauzen.alphacore.utils.sql;

import me.pauzen.alphacore.messages.ErrorMessage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

public class SQLConnection {

    private String username, password, host, database;
    private int        port;
    private Connection connection;
    private JavaPlugin plugin;

    public SQLConnection(JavaPlugin javaPlugin, String username, String password, String host, String database, int port) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.database = database;
        this.port = port;
        this.plugin = javaPlugin;
    }

    public static String toFormat(Map<String, String> dataTypes) {
        StringBuilder types = new StringBuilder();

        types.append("(");

        Iterator<Map.Entry<String, String>> iterator = dataTypes.entrySet().iterator();
        Map.Entry<String, String> currentElement;
        while ((currentElement = iterator.next()) != null) {
            types.append(currentElement.getKey() + " " + currentElement.getValue());
            if (iterator.hasNext()) {
                types.append(", ");
            }
        }
        types.append(")");

        return types.toString();
    }

    @Override
    public String toString() {
        return "SQLConnection{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", host='" + host + '\'' +
                ", database='" + database + '\'' +
                ", port=" + port +
                ", connection=" + connection +
                '}';
    }

    /**
     * Called to connect to the MySQL database.
     *
     * @param table The table to use.
     */
    public void tryConnecting(Table table) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String url = "jdbc:mysql://" + host + "/" + database + "?connectTimeout=5000";
            try {
                SQLConnection.this.connection = DriverManager.getConnection(url, "root", "");
                if (table != null) {
                    table.create(this);
                }
            } catch (SQLException e) {
                ErrorMessage.SQL.sendConsole();
                e.printStackTrace();
            }
        });
    }

    /**
     * USE FROM ASYNC CONTEXT
     */
    public ResultSet query(String query) {
        if (!hasConnected()) {
            return null;
        }

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet ret = preparedStatement.executeQuery();
            preparedStatement.close();
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void query(String query, Consumer<ResultSet> callback) {
        callback.accept(query(query));
    }

    public void execute(String executable) {
        if (!hasConnected()) {
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(executable);
                preparedStatement.execute();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void createTable(Table table) {
        if (!hasConnected()) {
            return;
        }

        execute("CREATE TABLE IF NOT EXISTS " + table.getName() + " " + toFormat(table.getTypes()) + ";");
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean hasConnected() {
        return connection != null;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHost() {
        return host;
    }

    public String getDatabase() {
        return database;
    }
}
