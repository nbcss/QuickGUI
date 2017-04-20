package me.nbcss.quickGui.elements.factory;

import me.nbcss.quickGui.elements.inventories.DispenserInventory;

public class DispenserInventoryFactory implements InventoryFactory {
	
	@Override
	public DispenserInventory createInventory() {
		return new DispenserInventory();
	}

}
