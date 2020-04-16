package fr.bastfx.mytopvote;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class MyTopVoteListener implements Listener {
	
	private MyTopVote main;

	public MyTopVoteListener(MyTopVote myTopVote) {
		this.main = myTopVote;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(event.getClickedBlock() != null && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			BlockState bs = event.getClickedBlock().getState();
			if(bs instanceof Sign) {
				Sign sign = (Sign) bs;
				if(sign.getLine(0).equalsIgnoreCase("[votes]")) {
					switch(sign.getLine(1)) {
						case "top1":
							sign.setLine(0, ChatColor.GOLD+"============");
							sign.setLine(1, ChatColor.GOLD+"Top 1 : ");
							sign.setLine(2, ChatColor.GOLD+" "+main.getTop1().getUsername());
							sign.setLine(3, ChatColor.GOLD+" "+main.getTop1().getVotes()+" votes");
							sign.update();
							if(this.main.getConfig().get("signs.sign1") == null) {
								this.main.getConfig().set("signs.sign1", sign.getLocation().getX()+","+sign.getLocation().getY()+","+sign.getLocation().getZ());
								this.main.saveConfig();
							}

							break;
						case "top2":
							sign.setLine(0, ChatColor.GOLD+"============");
							sign.setLine(1, ChatColor.GOLD+"Top 2 : ");
							sign.setLine(2, ChatColor.GOLD+" "+main.getTop2().getUsername());
							sign.setLine(3, ChatColor.GOLD+" "+main.getTop2().getVotes()+" votes");
							sign.update();
							if(this.main.getConfig().get("signs.sign2") == null) {
								this.main.getConfig().set("signs.sign2", sign.getLocation().getX()+","+sign.getLocation().getY()+","+sign.getLocation().getZ());
								this.main.saveConfig();
							}
							break;
						case "top3":
							sign.setLine(0, ChatColor.GOLD+"============");
							sign.setLine(1, ChatColor.GOLD+"Top 3 : ");
							sign.setLine(2, ChatColor.GOLD+" "+main.getTop3().getUsername());
							sign.setLine(3, ChatColor.GOLD+" "+main.getTop3().getVotes()+" votes");
							sign.update();
							if(this.main.getConfig().get("signs.sign3") == null) {
								this.main.getConfig().set("signs.sign3", sign.getLocation().getX()+","+sign.getLocation().getY()+","+sign.getLocation().getZ());
								this.main.saveConfig();
							}
							break;
					}

				}
			}
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		if(this.main.getConfig().get("signs.sign1") != null) {
			Location locSign1 = this.convertStringToLoc(this.main.getConfig().get("signs.sign1").toString());
			BlockState sign1 = locSign1.getBlock().getState();
			if(sign1.getType() == Material.AIR) {
				this.main.getConfig().set("signs.sign1", null);
				this.main.saveConfig();
			}
			if(sign1 instanceof Sign) {
				((Sign) sign1).setLine(2, ChatColor.GOLD+" "+main.getTop1().getUsername());
				((Sign) sign1).setLine(3, ChatColor.GOLD+" "+main.getTop1().getVotes()+" votes");
				((Sign) sign1).update();
			}
		}
		
		if(this.main.getConfig().get("signs.sign2") != null) {
			Location locSign2 = this.convertStringToLoc(this.main.getConfig().get("signs.sign2").toString());
			BlockState sign2 = locSign2.getBlock().getState();
			if(sign2.getType() == Material.AIR) {
				this.main.getConfig().set("signs.sign2", null);
				this.main.saveConfig();
			}
			if(sign2 instanceof Sign) {
				((Sign) sign2).setLine(2, ChatColor.GOLD+" "+main.getTop2().getUsername());
				((Sign) sign2).setLine(3, ChatColor.GOLD+" "+main.getTop2().getVotes()+" votes");
				((Sign) sign2).update();
			}
		}
		
		if(this.main.getConfig().get("signs.sign3") != null) {
			Location locSign3 = this.convertStringToLoc(this.main.getConfig().get("signs.sign3").toString());
			BlockState sign3 = locSign3.getBlock().getState();
			if(sign3.getType() == Material.AIR) {
				this.main.getConfig().set("signs.sign3", null);
				this.main.saveConfig();
			}
			if(sign3 instanceof Sign) {
				((Sign) sign3).setLine(2, ChatColor.GOLD+" "+main.getTop3().getUsername());
				((Sign) sign3).setLine(3, ChatColor.GOLD+" "+main.getTop3().getVotes()+" votes");
				((Sign) sign3).update();
			}
		}
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
