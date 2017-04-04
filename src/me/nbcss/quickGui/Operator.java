package me.nbcss.quickGui;

import java.util.HashMap;

import org.bukkit.entity.Player;

import me.nbcss.quickGui.elements.inventories.AbstractInventory;
import me.nbcss.quickGui.utils.Util;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerOpenWindow;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerWindowItems;
public final class Operator {
	private static HashMap<Player, AbstractInventory> players = new HashMap<Player, AbstractInventory>();
	public static void openInventory(AbstractInventory inv, Player player){
		WrapperPlayServerOpenWindow window = Util.getWindowPacket(inv);
		window.sendPacket(player);
		WrapperPlayServerWindowItems setup = Util.getWindowItemPacket(inv, player);
		setup.sendPacket(player);
		players.put(player, inv);
	}
	protected static AbstractInventory getOpenedInventory(Player player){
		return players.get(player);
	}
	protected static void removeOpenedInventory(Player player){
		players.remove(player);
	}
	protected static void addOpenedInventory(Player player){
		players.put(player, null);
	}
}
