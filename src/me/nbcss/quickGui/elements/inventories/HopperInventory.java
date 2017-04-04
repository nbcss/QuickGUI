<<<<<<< HEAD
package me.nbcss.quickGui.elements.inventories;

import me.nbcss.quickGui.elements.Icon;

public class HopperInventory extends AbstractInventory {
	private static final String TYPE = "minecraft:hopper";
	public HopperInventory() {
		super(TYPE, 5, 5);
	}
	public void setIcon(int slot, Icon item){
		if(slot < 0 || slot >= 5)
			return;
		super.setIconElement(slot, item);
	}
	public Icon getIcon(int slot){
		if(slot < 0 || slot >= 5)
			return null;
		return super.getIconElement(slot);
	}
}
=======
package me.nbcss.quickGui.elements.inventories;

import me.nbcss.quickGui.elements.Icon;

public class HopperInventory extends AbstractInventory {
	private static final String TYPE = "minecraft:hopper";
	public HopperInventory() {
		super(TYPE, 5, 5);
	}
	public void setIcon(int slot, Icon item){
		if(slot < 0 || slot >= 5)
			return;
		super.setIconElement(slot, item);
	}
	public Icon getIcon(int slot){
		if(slot < 0 || slot >= 5)
			return null;
		return super.getIconElement(slot);
	}
}
>>>>>>> origin/master
