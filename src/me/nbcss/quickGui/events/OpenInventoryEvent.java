package me.nbcss.quickGui.events;

import org.bukkit.entity.Player;

public class OpenInventoryEvent {
	private final Player player;
	public OpenInventoryEvent(Player player){
		this.player = player;
	}
	public Player getPlayer(){
		return player;
	}
}
