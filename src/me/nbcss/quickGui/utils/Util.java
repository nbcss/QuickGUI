package me.nbcss.quickGui.utils;

import java.util.ArrayList;

import org.bukkit.Material;
//import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Util {
	public static ItemStack createItem(int amount, short durability, Material material, String name, String[] lores) {
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
		item.setItemMeta(meta);
		
		return item;
	}
}
