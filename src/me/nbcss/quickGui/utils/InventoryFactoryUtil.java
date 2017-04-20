package me.nbcss.quickGui.utils;

import me.nbcss.quickGui.elements.factory.AnvilInventoryFactory;
import me.nbcss.quickGui.elements.factory.BeaconInventoryFactory;
import me.nbcss.quickGui.elements.factory.BrewingStandInventoryFactory;
import me.nbcss.quickGui.elements.factory.CustomInventoryFactory;
import me.nbcss.quickGui.elements.factory.DispenserInventoryFactory;
import me.nbcss.quickGui.elements.factory.DropperInventoryFactory;
import me.nbcss.quickGui.elements.factory.EnchantmentTableInventoryFactory;
import me.nbcss.quickGui.elements.factory.FurnaceInventoryFactory;
import me.nbcss.quickGui.elements.factory.HopperInventoryFactory;
import me.nbcss.quickGui.elements.inventories.AnvilInventory;
import me.nbcss.quickGui.elements.inventories.BeaconInventory;
import me.nbcss.quickGui.elements.inventories.BrewingStandInventory;
import me.nbcss.quickGui.elements.inventories.CustomInventory;
import me.nbcss.quickGui.elements.inventories.DispenserInventory;
import me.nbcss.quickGui.elements.inventories.DropperInventory;
import me.nbcss.quickGui.elements.inventories.EnchantmentTableInventory;
import me.nbcss.quickGui.elements.inventories.FurnaceInventory;
import me.nbcss.quickGui.elements.inventories.HopperInventory;

public class InventoryFactoryUtil {
	public static AnvilInventory createAnvilInventory(){
		return new AnvilInventoryFactory().createInventory();
	}
	public static BeaconInventory createBeaconInventory(){
		return new BeaconInventoryFactory().createInventory();
	}
	public static BrewingStandInventory createBrewingStandInventory(){
		return new BrewingStandInventoryFactory().createInventory();
	}
	public static CustomInventory createCustomInventory(int row){
		return new CustomInventoryFactory(row).createInventory();
	}
	public static DropperInventory createDropperInventory(){
		return new DropperInventoryFactory().createInventory();
	}
	public static DispenserInventory createDispenserInventory(){
		return new DispenserInventoryFactory().createInventory();
	}
	public static EnchantmentTableInventory createEnchantmentTableInventory(){
		return new EnchantmentTableInventoryFactory().createInventory();
	}
	public static FurnaceInventory createFurnaceInventory(){
		return new FurnaceInventoryFactory().createInventory();
	}
	public static HopperInventory createHopperInventory(){
		return new HopperInventoryFactory().createInventory();
	}
}
