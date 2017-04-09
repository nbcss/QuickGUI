package me.nbcss.quickGui.elements.inventories;

public class EnchantmentTableInventory extends AbstractInventory {
	private static final String TYPE = "minecraft:enchanting_table";
	private static final String NAME = "Enchant";
	public EnchantmentTableInventory() {
		super(TYPE, 2, 2, NAME);
	}

}
