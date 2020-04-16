package fr.bastfx.mytopvote;

public class TopVoteUser {
	
	private String username;
	private int votes;
	
	public TopVoteUser() {
		// TODO Auto-generated constructor stub
	}
	
	public TopVoteUser(String pUsername, int pVotes) {
		this.username = pUsername;
		this.votes = pVotes;
	}
	
	public String getUsername() {
		return username;
	}
	
	public int getVotes() {
		return votes;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setVotes(int votes) {
		this.votes = votes;
	}

}
