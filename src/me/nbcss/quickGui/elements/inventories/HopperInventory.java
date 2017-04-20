package me.nbcss.quickGui.elements.inventories;

public class HopperInventory extends AbstractInventory {
	private static final String TYPE = "minecraft:hopper";
	private static final String NAME = "Hopper";
	public HopperInventory() {
		super(TYPE, 5, 5, NAME);
	}
}
