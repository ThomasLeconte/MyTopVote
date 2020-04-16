package fr.bastfx.mytopvote.database;

import java.sql.DriverManager;
import java.sql.SQLException;

import fr.bastfx.mytopvote.MyTopVote;

public class sqlConnection {
	private MyTopVote main;
	private String host;
	private int port;
	private String dbname;
	private String username;
	private String password;
	private java.sql.Connection connection;
	private boolean isConnected;
	private boolean isOpen;

	public sqlConnection(MyTopVote myTopVote) {
		this.main = myTopVote;
		this.host = main.getConfig().getString("database.host");
		this.port = main.getConfig().getInt("database.port");
		this.dbname = main.getConfig().getString("database.dbname");
		this.username = main.getConfig().getString("database.username");
		this.password = main.getConfig().getString("database.password");
		this.isConnected = false;
		this.isOpen = false;
	}
	
	public boolean loadConnection() {
        try
        {
    		if(main.getConfig().getString("database.password").equals("none")) {
            	this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.dbname, this.username, "");
    		}else {
            	this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.dbname, this.username, this.password);
    		}

            this.isConnected = true;
        }
        catch (SQLException e)
        {
        	this.isConnected = false;
            e.printStackTrace();
        }
        return this.isConnected;
	}
	
	public java.sql.Connection getConnection() {
		return connection;
	}
}
