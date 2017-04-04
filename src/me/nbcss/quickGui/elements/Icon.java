package me.nbcss.quickGui.elements;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

public class Icon {
	private ItemStack icon;
	public Icon(){
		
	}
	public Icon(ItemStack item) {
		icon = item;
	}
	public final ItemStack getItem(){
		return icon;
	}
	public final void setName(String name){
		if(icon != null)
			icon.getItemMeta().setDisplayName(name);
	}
	public final String getName(){
		if(icon != null)
			return icon.getItemMeta().getDisplayName();
		return null;
	}
	public final void setLore(List<String> lore){
		if(icon != null)
			icon.getItemMeta().setLore(lore);
	}
	public final void setLore(String... lore){
		if(icon != null){
			List<String> list = new ArrayList<String>();
			for(String line : lore)
				list.add(line);
			icon.getItemMeta().setLore(list);
		}
	}
	public final List<String> getLore(){
		if(icon != null)
			return icon.getItemMeta().getLore();
		return null;
	}
	public void onClick(ClickIconEvent event){}
}
