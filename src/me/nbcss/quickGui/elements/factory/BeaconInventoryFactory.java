package me.nbcss.quickGui.elements.factory;

import me.nbcss.quickGui.elements.inventories.BeaconInventory;

public class BeaconInventoryFactory implements InventoryFactory {

	@Override
	public BeaconInventory createInventory() {
		return new BeaconInventory();
	}

}
