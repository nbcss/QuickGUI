package me.nbcss.quickGui.events;

import org.bukkit.entity.Player;

import me.nbcss.quickGui.elements.inventories.AbstractInventory;

public class SwapHotbarClickEvent extends ClickEvent {

	public SwapHotbarClickEvent(Player player, ClickAction action, ClickType type, int slot,
			AbstractInventory inventory) {
		super(player, action, type, slot, inventory);
		// TODO Auto-generated constructor stub
	}

}
