package me.nbcss.quickGui.events;

import org.bukkit.entity.Player;

import me.nbcss.quickGui.elements.InventoryView;

public class AnvilTypeTextEvent {
	private final InventoryView view;
	private final Player player;
	private final String text;
	public AnvilTypeTextEvent(Player player, String text, InventoryView view){
		this.player = player;
		this.text = text;
		this.view = view;
	}
	public Player getPlayer() {
		return player;
	}
	public String getTypeText() {
		return text;
	}
	public InventoryView getClickedInventoryView() {
		return view;
	}
}
