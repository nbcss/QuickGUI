package me.nbcss.quickGui.events;

import org.bukkit.entity.Player;

public class CloseInventoryEvent {
	private final Player player;
	public CloseInventoryEvent(Player player){
		this.player = player;
	}
	public Player getPlayer(){
		return player;
	}
}
