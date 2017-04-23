package me.nbcss.quickGui.events;

import org.bukkit.entity.Player;

import me.nbcss.quickGui.elements.InventoryView;

public class TradePageChangedEvent {
	private final InventoryView view;
	private final Player player;
	private final int page;
	public TradePageChangedEvent(InventoryView view, Player player, int page){
		this.view = view;
		this.player = player;
		this.page = page;
	}
	public InventoryView getChangedView() {
		return view;
	}
	public Player getPlayer() {
		return player;
	}
	public int getPage() {
		return page;
	}
}
