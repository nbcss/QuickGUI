package me.nbcss.quickGui.elements.factory;

import me.nbcss.quickGui.elements.inventories.HopperInventory;

public class HopperInventoryFactory implements InventoryFactory {
	@Override
	public HopperInventory createInventory() {
		return new HopperInventory();
	}

}
