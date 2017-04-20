package me.nbcss.quickGui.events;

import me.nbcss.quickGui.elements.InventoryView;

public class InventoryChangeEvent {
	private final InventoryView view;
	private boolean replace;
	public InventoryChangeEvent(InventoryView view, boolean replace){
		this.view = view;
		this.replace = replace;
	}
	public InventoryView getChangedInventoryView(){
		return view;
	}
	public boolean isReplaced(){
		return replace;
	}
}
