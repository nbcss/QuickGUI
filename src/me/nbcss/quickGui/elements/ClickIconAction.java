package me.nbcss.quickGui.elements;

import org.bukkit.event.inventory.InventoryAction;

public enum ClickIconAction {
	LEFT_CLICK, RIGHT_CLICK, MID_CLICK, SHIFT_CLICK;
	public static ClickIconAction fromInventoryAction(InventoryAction action){
		if(action == null)
			return null;
		switch(action){
		case PICKUP_ALL:
			return LEFT_CLICK;
		case PICKUP_HALF:
			return RIGHT_CLICK;
		default:
			return null;
		}
	}
}
