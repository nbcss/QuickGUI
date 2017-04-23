package me.nbcss.quickGui;

import java.util.HashMap;

import org.bukkit.entity.Player;

import me.nbcss.quickGui.elements.InventoryView;
import me.nbcss.quickGui.elements.inventories.AbstractInventory;
import me.nbcss.quickGui.elements.inventories.BottomInventory;
import me.nbcss.quickGui.elements.inventories.HotbarInventory;

public final class Operator {
	private static final int ID = 101;
	private static HashMap<Player, InventoryView> map = new HashMap<Player, InventoryView>();
	public static void openInventory(InventoryView view, Player player){
		if(view == null)
			return;
		InventoryView before = map.get(player);
		if(before != null)
			before.closeInventoryView(player);
		view.openInventoryView(player);
		map.put(player, view);
	}
	public static void openInventory(AbstractInventory inv, Player player){
		InventoryView before = map.get(player);
		if(before != null)
			before.closeInventoryView(player);
		BottomInventory bottom = BottomInventory.createFromPlayer(player);
		HotbarInventory hotbar = HotbarInventory.createFromPlayer(player);
		InventoryView view = new InventoryView(inv, bottom, hotbar);
		view.openInventoryView(player);
		map.put(player, view);
	}
	public static void openInventory(AbstractInventory inv, BottomInventory bottom, Player player){
		InventoryView before = map.get(player);
		if(before != null)
			before.closeInventoryView(player);
		if(bottom == null)
			bottom = BottomInventory.createFromPlayer(player);
		HotbarInventory hotbar = HotbarInventory.createFromPlayer(player);
		InventoryView view = new InventoryView(inv, bottom, hotbar);
		view.openInventoryView(player);
		map.put(player, view);
	}
	public static void openInventory(AbstractInventory inv, BottomInventory bottom, HotbarInventory hotbar, Player player){
		InventoryView before = map.get(player);
		if(before != null)
			before.closeInventoryView(player);
		if(bottom == null)
			bottom = BottomInventory.createFromPlayer(player);
		if(hotbar == null)
			hotbar = HotbarInventory.createFromPlayer(player);
		InventoryView view = new InventoryView(inv, bottom, hotbar);
		view.openInventoryView(player);
		map.put(player, view);
	}
	public static void closeInventory(Player player){
		InventoryView view = map.get(player);
		if(view != null)
			view.closeInventoryView(player);
	}
	public static int getWindowID(){
		return ID;
	}
	public static InventoryView getOpenedInventoryView(Player player){
		return map.get(player);
	}
	protected static void removeOpenedInventoryView(Player player){
		map.remove(player);
	}
	protected static void resetOpenedInventoryView(Player player){
		map.put(player, null);
	}
}
