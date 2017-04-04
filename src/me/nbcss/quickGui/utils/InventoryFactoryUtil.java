package me.nbcss.quickGui.utils;

import me.nbcss.quickGui.elements.factory.AnvilInventoryFactory;
import me.nbcss.quickGui.elements.factory.CustomInventoryFactory;
import me.nbcss.quickGui.elements.factory.DropperInventoryFactory;
import me.nbcss.quickGui.elements.inventories.AnvilInventory;
import me.nbcss.quickGui.elements.inventories.CustomInventory;
import me.nbcss.quickGui.elements.inventories.DropperInventory;

public class InventoryFactoryUtil {
	public static AnvilInventory createAnvilInventory(){
		return new AnvilInventoryFactory().createInventory();
	}
	public static CustomInventory createCustomInventory(int row){
		return new CustomInventoryFactory(row).createInventory();
	}
	public static DropperInventory createDropperInventory(){
		return new DropperInventoryFactory().createInventory();
	}
}
