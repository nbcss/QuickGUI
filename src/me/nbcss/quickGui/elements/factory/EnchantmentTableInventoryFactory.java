package me.nbcss.quickGui.elements.factory;

import me.nbcss.quickGui.elements.inventories.EnchantmentTableInventory;

public class EnchantmentTableInventoryFactory implements InventoryFactory {

	@Override
	public EnchantmentTableInventory createInventory() {
		return new EnchantmentTableInventory();
	}

}
