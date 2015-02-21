/*
 *  Created by Filip P. on 2/17/15 4:22 PM.
 */

package me.pauzen.alphacore.utils.sql;

import java.sql.*;

public class SQLConnection {
    
    private String username, password, host, database;
    private int port;
    private Connection connection;
    
    public SQLConnection(String username, String password, String host, String database, int port) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.database = database;
        this.port = port;
    }
    
    public void tryConnecting() {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public ResultSet query(String query) {
        if (!hasConnected()) {
            return null;
        }
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
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
