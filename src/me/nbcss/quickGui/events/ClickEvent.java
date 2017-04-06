package me.nbcss.quickGui.events;

import org.bukkit.entity.Player;

import me.nbcss.quickGui.elements.inventories.AbstractInventory;

public abstract class ClickEvent {
	private final ClickType type;
	private final AbstractInventory inv;
	private final Player player;
	private final ClickAction action;
	private final int slot;
	private boolean cancel;
	//private boolean pickup;
	protected ClickEvent(Player player, ClickAction action, ClickType type, int slot, AbstractInventory inventory){
		this.player = player;
		this.slot = slot;
		this.action = action;
		this.type = type;
		this.inv = inventory;
		this.cancel = false;
	}
	public ClickAction getAction(){
		return action;
	}
	public Player getPlayer(){
		return player;
	}
	public int getSlot() {
		return slot;
	}
	public ClickType getType() {
		return type;
	}
	public AbstractInventory getClickedInventory() {
		return inv;
	}
	public boolean isCancelled() {
		return cancel;
	}
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}
}
