package me.nbcss.quickGui.elements.factory;

import me.nbcss.quickGui.elements.inventories.CustomInventory;

public class CustomInventoryFactory implements InventoryFactory {
	private int row;
	public CustomInventoryFactory(int row){
		this.row = row;
	}
	@Override
	public CustomInventory createInventory() {
		return CustomInventory.constractCustomInventory(row);
	}

}
