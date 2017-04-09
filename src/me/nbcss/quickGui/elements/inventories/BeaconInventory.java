package me.nbcss.quickGui.elements.inventories;

public class BeaconInventory extends AbstractInventory {
	private static final String TYPE = "minecraft:beacon";
	private static final String NAME = "Beacon";
	public BeaconInventory() {
		super(TYPE, 1, 1, NAME);
	}
}
