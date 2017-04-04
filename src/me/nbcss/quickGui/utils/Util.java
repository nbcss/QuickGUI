<<<<<<< HEAD
package me.nbcss.quickGui.utils;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.comphenix.protocol.wrappers.WrappedChatComponent;

import me.nbcss.quickGui.elements.inventories.AbstractInventory;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerOpenWindow;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerWindowItems;

public class Util {
	public static ItemStack createItem(int amount, short durability, boolean ench, Material material, String name, String[] lores) {
		ItemStack item = new ItemStack(material);
		item.setAmount(amount);
		item.setDurability(durability);
		ItemMeta meta = item.getItemMeta();
		if(name != null)
			meta.setDisplayName(name);
		if(lores != null)
			if(lores.length > 0){
				ArrayList<String> Lore = new ArrayList<String>();
				for(String lore : lores)
					Lore.add(lore);
				meta.setLore(Lore);
			}
		if(ench){
			meta.addEnchant(Enchantment.LUCK, 1, false);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		item.setItemMeta(meta);
		
		return item;
	}
	public static WrapperPlayServerOpenWindow getWindowPacket(AbstractInventory inventory){
		WrapperPlayServerOpenWindow packet = new WrapperPlayServerOpenWindow();
		packet.setWindowID(255);
		packet.setNumberOfSlots(inventory.getNumSlot());
		packet.setInventoryType(inventory.getType());
		packet.setWindowTitle(WrappedChatComponent.fromText("None"));
		return packet;
	}
	public static WrapperPlayServerWindowItems getWindowItemPacket(AbstractInventory inventory, Player player){
		WrapperPlayServerWindowItems packet = new WrapperPlayServerWindowItems();
		packet.setWindowId(255);
		ItemStack[] inv = inventory.getItemList();
		ItemStack[] data = new ItemStack[inv.length + 36];
		int index = 0;
		for(int i = 0; i < inv.length; i++)
			data[index++] = inv[i];
		ItemStack[] storage = player.getInventory().getStorageContents();
		for(int i = 9; i < 36; i++)
			data[index++] = storage[i];
		for(int i = 0; i < 9; i++)
			data[index++] = storage[i];
		packet.setSlotData(data);
		return packet;
	}
}
=======
package me.nbcss.quickGui.utils;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.comphenix.protocol.wrappers.WrappedChatComponent;

import me.nbcss.quickGui.elements.inventories.AbstractInventory;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerOpenWindow;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerWindowItems;

public class Util {
	public static ItemStack createItem(int amount, short durability, boolean ench, Material material, String name, String[] lores) {
		ItemStack item = new ItemStack(material);
		item.setAmount(amount);
		item.setDurability(durability);
		ItemMeta meta = item.getItemMeta();
		if(name != null)
			meta.setDisplayName(name);
		if(lores != null)
			if(lores.length > 0){
				ArrayList<String> Lore = new ArrayList<String>();
				for(String lore : lores)
					Lore.add(lore);
				meta.setLore(Lore);
			}
		if(ench){
			meta.addEnchant(Enchantment.LUCK, 1, false);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		item.setItemMeta(meta);
		
		return item;
	}
	public static WrapperPlayServerOpenWindow getWindowPacket(AbstractInventory inventory){
		WrapperPlayServerOpenWindow packet = new WrapperPlayServerOpenWindow();
		packet.setWindowID(255);
		packet.setNumberOfSlots(inventory.getNumSlot());
		packet.setInventoryType(inventory.getType());
		packet.setWindowTitle(WrappedChatComponent.fromText("None"));
		return packet;
	}
	public static WrapperPlayServerWindowItems getWindowItemPacket(AbstractInventory inventory, Player player){
		WrapperPlayServerWindowItems packet = new WrapperPlayServerWindowItems();
		packet.setWindowId(255);
		ItemStack[] inv = inventory.getItemList();
		ItemStack[] data = new ItemStack[inv.length + 36];
		int index = 0;
		for(int i = 0; i < inv.length; i++)
			data[index++] = inv[i];
		ItemStack[] storage = player.getInventory().getStorageContents();
		for(int i = 9; i < 36; i++)
			data[index++] = storage[i];
		for(int i = 0; i < 9; i++)
			data[index++] = storage[i];
		packet.setSlotData(data);
		return packet;
	}
}
>>>>>>> origin/master
