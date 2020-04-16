package fr.bastfx.mytopvote;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.bastfx.mytopvote.database.sqlStorage;

public class VotesCommand implements CommandExecutor {
	
	private MyTopVote main;

	public VotesCommand(MyTopVote myTopVote) {
		this.main = myTopVote;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command label, String cmd, String[] args) {
		if(cmd.equalsIgnoreCase("votes")) {
			if(sender instanceof Player) {
				Player player = (Player) sender;
				if(args.length == 0) {
					sqlStorage dao = new sqlStorage(main);
					ArrayList<TopVoteUser> topVoteurs = dao.getTopVotes();
					player.sendMessage("§e# =============================== #");
					player.sendMessage("§e# Meilleurs voteurs : ");
					switch(topVoteurs.size()) {
						case 1:
							player.sendMessage("§e# Top 1 : "+topVoteurs.get(0).getUsername()+" ("+topVoteurs.get(0).getVotes()+" votes)");
							player.sendMessage("§e# Top 2 : Aucun");
							player.sendMessage("§e# Top 3 : Aucun");
							this.main.setTop1(topVoteurs.get(0));
							this.main.setTop2(new TopVoteUser("Aucun", 0));
							this.main.setTop3(new TopVoteUser("Aucun", 0));
							break;
						case 2:
							player.sendMessage("§e# Top 1 : "+topVoteurs.get(0).getUsername()+" ("+topVoteurs.get(0).getVotes()+" votes)");
							player.sendMessage("§e# Top 2 : "+topVoteurs.get(1).getUsername()+" ("+topVoteurs.get(1).getVotes()+" votes)");
							player.sendMessage("§e# Top 3 : Aucun");
							this.main.setTop1(topVoteurs.get(0));
							this.main.setTop2(topVoteurs.get(1));
							this.main.setTop3(new TopVoteUser("Aucun", 0));
							break;
						case 3:
							player.sendMessage("§e# Top 1 : "+topVoteurs.get(0).getUsername()+" ("+topVoteurs.get(0).getVotes()+" votes)");
							player.sendMessage("§e# Top 2 : "+topVoteurs.get(1).getUsername()+" ("+topVoteurs.get(1).getVotes()+" votes)");
							player.sendMessage("§e# Top 3 : "+topVoteurs.get(2).getUsername()+" ("+topVoteurs.get(2).getVotes()+" votes)");
							this.main.setTop1(topVoteurs.get(0));
							this.main.setTop2(topVoteurs.get(1));
							this.main.setTop3(topVoteurs.get(2));
							break;
					}
					player.sendMessage("§e# =============================== #");
				}else if(args.length == 1) {
					if(args[0].equalsIgnoreCase("list")) {
						if(player.isOp()) {
							player.sendMessage("§e# =============================== #");
							player.sendMessage("§e# Liste des panneaux actifs : ");
							if(this.main.getConfig().get("signs.sign1") != null) {
								player.sendMessage("§e# Panneau top 1 : "+this.main.getConfig().get("signs.sign1").toString());
							}
							if(this.main.getConfig().get("signs.sign2") != null) {
								player.sendMessage("§e# Panneau top 2 : "+this.main.getConfig().get("signs.sign2").toString());
							}
							if(this.main.getConfig().get("signs.sign3") != null) {
								player.sendMessage("§e# Panneau top 3 : "+this.main.getConfig().get("signs.sign3").toString());
							}
							player.sendMessage("§e# =============================== #");
						}
					}else if(args[0].equalsIgnoreCase("removeall")) {
						if(player.isOp()) {
							this.convertStringToLoc(this.main.getConfig().get("signs.sign1").toString()).getBlock().breakNaturally();
							this.convertStringToLoc(this.main.getConfig().get("signs.sign2").toString()).getBlock().breakNaturally();
							this.convertStringToLoc(this.main.getConfig().get("signs.sign3").toString()).getBlock().breakNaturally();
							this.main.getConfig().set("signs.sign1", null);
							this.main.getConfig().set("signs.sign2", null);
							this.main.getConfig().set("signs.sign3", null);
							this.main.saveConfig();
						}
					}
				}

			}
		}
		return false;
	}
	
	public Location convertStringToLoc(String stringLocation) {
		String[] loc = stringLocation.split(",");
		double x = Double.valueOf(loc[0]);
		double y = Double.valueOf(loc[1]);
		double z = Double.valueOf(loc[2]);
		
		Location location = new Location(Bukkit.getWorld("world"), x, y, z);
		return location;
	}

}
