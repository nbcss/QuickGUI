package me.nbcss.quickGui.elements.inventories;

import me.nbcss.quickGui.elements.Icon;

public class DropperInventory extends AbstractInventory {
	private static final String TYPE = "minecraft:dropper";
	public DropperInventory() {
		super(TYPE, 9, 9);
		
	}
	public void setIcon(int slot, Icon item){
		if(slot < 0 || slot >= 9)
			return;
		super.setIconElement(slot, item);
	}
	public Icon getIcon(int slot){
		if(slot < 0 || slot >= 9)
			return null;
		return super.getIconElement(slot);
	}
}
