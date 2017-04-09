package me.nbcss.quickGui.elements.inventories;

import me.nbcss.quickGui.elements.Icon;

public class CustomInventory extends AbstractInventory {
	private static final String TYPE = "minecraft:container";
	private static final String NAME = "Container";
	private CustomInventory(int slot) {
		super(TYPE, slot, slot, NAME);
	}
	public static CustomInventory constractCustomInventory(int row){
		if(row < 0 || row > 6)
			return null;
		return new CustomInventory(row * 9);
	}
	public void setIcon(int slot, Icon item){
		if(slot < 0 || slot >= getSlot())
			return;
		super.setIconElement(slot, item);
	}
	public Icon getIcon(int slot){
		if(slot < 0 || slot >= getSlot())
			return null;
		return super.getIconElement(slot);
	}
}
