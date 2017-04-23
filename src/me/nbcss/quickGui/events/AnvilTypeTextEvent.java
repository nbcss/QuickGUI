package me.nbcss.quickGui.events;

import org.bukkit.entity.Player;

import me.nbcss.quickGui.elements.InventoryView;

public class AnvilTypeTextEvent {
	private final InventoryView view;
	private final Player player;
	private final String name;
	public AnvilTypeTextEvent(Player player, String name, InventoryView view){
		this.player = player;
		this.name = name;
		this.view = view;
	}
	public Player getPlayer() {
		return player;
	}
	public String getTypeText() {
		return name;
	}
	public InventoryView getClickedInventoryView() {
		return view;
	}
}
