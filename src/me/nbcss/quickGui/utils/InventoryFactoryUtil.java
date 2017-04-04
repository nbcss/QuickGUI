package me.nbcss.quickGui.utils;

import me.nbcss.quickGui.elements.factory.AnvilInventoryFactory;
import me.nbcss.quickGui.elements.factory.CustomInventoryFactory;
import me.nbcss.quickGui.elements.factory.DropperInventoryFactory;
import me.nbcss.quickGui.elements.factory.FurnaceInventoryFactory;
import me.nbcss.quickGui.elements.factory.HopperInventoryFactory;
import me.nbcss.quickGui.elements.inventories.AnvilInventory;
import me.nbcss.quickGui.elements.inventories.CustomInventory;
import me.nbcss.quickGui.elements.inventories.DropperInventory;
import me.nbcss.quickGui.elements.inventories.FurnaceInventory;
import me.nbcss.quickGui.elements.inventories.HopperInventory;

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
	public static FurnaceInventory createFurnaceInventory(){
		return new FurnaceInventoryFactory().createInventory();
	}
	public static HopperInventory createHopperInventory(){
		return new HopperInventoryFactory().createInventory();
	}
}
