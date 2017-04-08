package me.nbcss.quickGui;

import java.util.HashMap;

import org.bukkit.entity.Player;

import me.nbcss.quickGui.elements.InventoryView;
import me.nbcss.quickGui.elements.inventories.AbstractInventory;
import me.nbcss.quickGui.elements.inventories.BottomInventory;
import me.nbcss.quickGui.elements.inventories.HotbarInventory;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerCloseWindow;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerOpenWindow;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerWindowItems;
public final class Operator {
	private static HashMap<Player, InventoryView> map = new HashMap<Player, InventoryView>();
	public static void openInventory(InventoryView view, Player player){
		if(view == null)
			return;
		view.onOpenInventoryView(player);
		WrapperPlayServerOpenWindow window = view.getOpenWindowPacket();
		WrapperPlayServerWindowItems setup = view.getWindowItemsPacket();
		window.sendPacket(player);
		setup.sendPacket(player);
		map.put(player, view);
	}
	public static void openInventory(AbstractInventory inv, Player player){
		BottomInventory bottom = BottomInventory.createFromPlayer(player);
		HotbarInventory hotbar = HotbarInventory.createFromPlayer(player);
		InventoryView view = new InventoryView(inv, bottom, hotbar);
		view.onOpenInventoryView(player);
		WrapperPlayServerOpenWindow window = view.getOpenWindowPacket();
		WrapperPlayServerWindowItems setup = view.getWindowItemsPacket();
		window.sendPacket(player);
		setup.sendPacket(player);
		map.put(player, view);
	}
	public static void openInventory(AbstractInventory inv, BottomInventory bottom, Player player){
		if(bottom == null)
			bottom = BottomInventory.createFromPlayer(player);
		HotbarInventory hotbar = HotbarInventory.createFromPlayer(player);
		InventoryView view = new InventoryView(inv, bottom, hotbar);
		view.onOpenInventoryView(player);
		WrapperPlayServerOpenWindow window = view.getOpenWindowPacket();
		WrapperPlayServerWindowItems setup = view.getWindowItemsPacket();
		window.sendPacket(player);
		setup.sendPacket(player);
		map.put(player, view);
	}
	public static void openInventory(AbstractInventory inv, BottomInventory bottom, HotbarInventory hotbar, Player player){
		if(bottom == null)
			bottom = BottomInventory.createFromPlayer(player);
		if(hotbar == null)
			hotbar = HotbarInventory.createFromPlayer(player);
		InventoryView view = new InventoryView(inv, bottom, hotbar);
		view.onOpenInventoryView(player);
		WrapperPlayServerOpenWindow window = view.getOpenWindowPacket();
		WrapperPlayServerWindowItems setup = view.getWindowItemsPacket();
		window.sendPacket(player);
		setup.sendPacket(player);
		map.put(player, view);
	}
	public static void closeInventory(Player player){
		WrapperPlayServerCloseWindow packet = new WrapperPlayServerCloseWindow();
		packet.setWindowId(MainClass.getID());
		packet.sendPacket(player);
	}
	public static int getWindowID(){
		return MainClass.getID();
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
