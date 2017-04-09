package me.nbcss.quickGui.elements;

import me.nbcss.quickGui.events.InventoryInteractEvent;

public interface Interactable {
	public void onInteract(InventoryInteractEvent event);
}
