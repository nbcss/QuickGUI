package me.nbcss.quickGui.elements.factory;

import me.nbcss.quickGui.elements.inventories.BrewingStandInventory;

public class BrewingStandInventoryFactory implements InventoryFactory {

	@Override
	public BrewingStandInventory createInventory() {
		return new BrewingStandInventory();
	}

}
