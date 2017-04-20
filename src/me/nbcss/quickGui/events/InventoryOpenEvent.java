package me.nbcss.quickGui.events;

import org.bukkit.entity.Player;

import me.nbcss.quickGui.elements.InventoryView;

public class InventoryOpenEvent {
	private final Player player;
	private final InventoryView view;
	public InventoryOpenEvent(Player player, InventoryView view){
		this.player = player;
		this.view = view;
	}
	public Player getPlayer(){
		return player;
	}
	public InventoryView getOpenedInventoryView(){
		return view;
	}
}
