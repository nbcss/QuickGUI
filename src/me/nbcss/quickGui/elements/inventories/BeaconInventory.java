package me.nbcss.quickGui.elements.inventories;

import me.nbcss.quickGui.elements.Icon;

public class BeaconInventory extends AbstractInventory {
	private static final String TYPE = "minecraft:beacon";
	private static final String NAME = "Beacon";
	public BeaconInventory() {
		super(TYPE, 1, 1, NAME);
	}
	public void setBeaconSlotIcon(Icon icon){
		setIconElement(0, icon);
	}
	public Icon getBeaconSlotIcon(){
		return getIconElement(0);
	}
}
