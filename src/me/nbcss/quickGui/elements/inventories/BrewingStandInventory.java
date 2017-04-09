package me.nbcss.quickGui.elements.inventories;

public class BrewingStandInventory extends AbstractInventory {
	private static final String TYPE = "minecraft:brewing_stand";
	private static final String NAME = "Brewing Stand";
	public BrewingStandInventory() {
		super(TYPE, 5, 5, NAME);
	}
}
