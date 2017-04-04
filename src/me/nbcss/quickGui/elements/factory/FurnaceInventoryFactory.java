package me.nbcss.quickGui.elements.factory;

import me.nbcss.quickGui.elements.inventories.FurnaceInventory;

public class FurnaceInventoryFactory implements InventoryFactory {
	@Override
	public FurnaceInventory createInventory() {
		return new FurnaceInventory();
	}

}
