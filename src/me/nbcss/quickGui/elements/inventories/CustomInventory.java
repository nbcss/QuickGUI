package me.nbcss.quickGui.elements.inventories;

public class CustomInventory extends AbstractInventory {
	private static final String TYPE = "minecraft:container";
	private static final String NAME = "Container";
	protected CustomInventory(int slot) {
		super(TYPE, slot, slot, NAME);
	}
	public static CustomInventory constractCustomInventory(int row){
		if(row < 0 || row > 6)
			return null;
		return new CustomInventory(row * 9);
	}
}
