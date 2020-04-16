package fr.bastfx.mytopvote.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import com.mysql.jdbc.Connection;

import fr.bastfx.mytopvote.MyTopVote;
import fr.bastfx.mytopvote.TopVoteUser;

public class sqlStorage {
	
    java.sql.Connection connection=null;
    static java.sql.PreparedStatement requete=null;
    static ResultSet rs=null;
	private MyTopVote main;
	
	public sqlStorage(MyTopVote myTopVote) {
		this.main = myTopVote;
		try {
			this.connection = myTopVote.getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<TopVoteUser> getTopVotes() {
		ArrayList<TopVoteUser> topVoteurs = new ArrayList<TopVoteUser>();
		try
        {
			try {
				main.getConnection();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            requete=connection.prepareStatement("SELECT username, COUNT(username) "
            		+ "FROM vote__votes GROUP BY username ORDER BY COUNT(username) DESC "
            		+ "LIMIT 3");
            
            rs=requete.executeQuery();
            
            while ( rs.next() ) {
            	TopVoteUser user = new TopVoteUser();
            	user.setUsername(rs.getString("username"));
            	user.setVotes(rs.getInt("COUNT(username)"));
            	topVoteurs.add(user);
            }
        }   
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
		
		return topVoteurs;
	}
}
