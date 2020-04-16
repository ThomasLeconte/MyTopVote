package fr.bastfx.mytopvote;

import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.plugin.java.JavaPlugin;

import com.mysql.jdbc.Connection;

import fr.bastfx.mytopvote.database.sqlConnection;
import fr.bastfx.mytopvote.database.sqlStorage;

public class MyTopVote extends JavaPlugin {
	private java.sql.Connection database;
	private TopVoteUser top1 = new TopVoteUser();
	private TopVoteUser top2 = new TopVoteUser();
	private TopVoteUser top3 = new TopVoteUser();
	@Override
	public void onEnable() {
		System.out.println("");
		System.out.println("# ============================== #");
		System.out.println("# ====== MyTopVote STARTED ===== #");
		System.out.println("#                                #");
		saveDefaultConfig();
		sqlConnection sqlConnect = new sqlConnection(this);
		boolean checkConnection = sqlConnect.loadConnection();
		if(checkConnection) {
			System.out.println("#    ---> DB : Connection OK     #");
			System.out.println("# ============================== #");
			System.out.println("");
			this.database = sqlConnect.getConnection();
			this.updateTopVoteUsers();
		}else {
			System.out.println("#  ---> DB : Connection Error    #");
			System.out.println("# ============================== #");
			System.out.println("");
		}
		
		getCommand("votes").setExecutor(new VotesCommand(this));
		getServer().getPluginManager().registerEvents(new MyTopVoteListener(this), this);
	}
	
	@Override
	public void onDisable() {
		System.out.println("# ============================== #");
		System.out.println("# ====== MyTopVote STOPPED ===== #");
		System.out.println("# ============================== #");
	}
	
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        return (Connection) this.database;
	}
	
	public void openConnection() throws SQLException, ClassNotFoundException {
	    if (database != null && !database.isClosed()) {
	        return;
	    }
	}
	
	public TopVoteUser getTop1() {
		return top1;
	}
	
	public TopVoteUser getTop2() {
		return top2;
	}
	
	public TopVoteUser getTop3() {
		return top3;
	}
	
	public void setTop1(TopVoteUser top1) {
		this.top1 = top1;
	}
	
	public void setTop2(TopVoteUser top2) {
		this.top2 = top2;
	}
	
	public void setTop3(TopVoteUser top3) {
		this.top3 = top3;
	}
	
	public void updateTopVoteUsers() {
		sqlStorage dao = new sqlStorage(this);
		ArrayList<TopVoteUser> topVoteurs = dao.getTopVotes();
		switch(topVoteurs.size()) {
			case 1:
				this.setTop1(topVoteurs.get(0));
				this.setTop2(new TopVoteUser("Aucun", 0));
				this.setTop3(new TopVoteUser("Aucun", 0));
				break;
			case 2:
				this.setTop1(topVoteurs.get(0));
				this.setTop2(topVoteurs.get(1));
				this.setTop3(new TopVoteUser("Aucun", 0));
				break;
			case 3:
				this.setTop1(topVoteurs.get(0));
				this.setTop2(topVoteurs.get(1));
				this.setTop3(topVoteurs.get(2));
				break;
		}
	}

}
