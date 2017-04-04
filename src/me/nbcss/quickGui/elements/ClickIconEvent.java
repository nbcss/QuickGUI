package me.nbcss.quickGui.elements;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ClickIconEvent {
	private final Player player;
	private final ClickIconAction action;
	private final int slot;
	private ItemStack item;
	private boolean pickup;
	public ClickIconEvent(Player player, ClickIconAction action, int slot, ItemStack item){
		this.player = player;
		this.slot = slot;
		this.action = action;
		this.item = item;
		pickup = false;
	}
	public boolean isAllowPickup(){
		return pickup;
	}
	public void setAllowPickup(boolean allow){
		pickup = allow;
	}
	public ClickIconAction getAction(){
		return action;
	}
	public Player getPlayer(){
		return player;
	}
	public int getSlot() {
		return slot;
	}
	public ItemStack getItem() {
		return item;
	}
}
