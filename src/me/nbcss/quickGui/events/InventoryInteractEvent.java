package me.nbcss.quickGui.events;

import org.bukkit.entity.Player;

import me.nbcss.quickGui.elements.InventoryView;
import me.nbcss.quickGui.elements.inventories.AbstractInventory;

public class InventoryInteractEvent {
	private final boolean onCursor;
	private final Player player;
	private final InventoryView clickedView;
	private final AbstractInventory clickedInventory;
	private final InteractAction action;
	private boolean update;
	public InventoryInteractEvent(boolean cursor, Player player, InteractAction action, InventoryView view, AbstractInventory inv){
		onCursor = cursor;
		clickedView = view;
		clickedInventory = inv;
		this.action = action;
		this.player = player;
		update = true;
	}
	public boolean isOnCursor() {
		return onCursor;
	}
	public Player getPlayer(){
		return player;
	}
	public InventoryView getClickedView() {
		return clickedView;
	}
	public AbstractInventory getClickedInventory() {
		return clickedInventory;
	}
	public InteractAction getAction() {
		return action;
	}
	public boolean isUpdate() {
		return update;
	}
	public void setUpdate(boolean update) {
		this.update = update;
	}
}
