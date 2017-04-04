package me.nbcss.quickGui.elements;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class Icon {
	private ItemStack icon;
	public Icon(){
		icon = null;
	}
	public Icon(ItemStack item) {
		icon = item;
	}
	public final void setItemStack(ItemStack item){
		icon = item;
	}
	public final ItemStack getItem(){
		return icon;
	}
	public final void setName(String name){
		if(!isNull())
			icon.getItemMeta().setDisplayName(name);
	}
	public final String getName(){
		if(!isNull())
			return icon.getItemMeta().getDisplayName();
		return null;
	}
	public final void setLore(List<String> lore){
		if(!isNull())
			icon.getItemMeta().setLore(lore);
	}
	public final void setLore(String... lore){
		if(!isNull()){
			List<String> list = new ArrayList<String>();
			for(String line : lore)
				list.add(line);
			icon.getItemMeta().setLore(list);
		}
	}
	public final List<String> getLore(){
		if(!isNull())
			return icon.getItemMeta().getLore();
		return null;
	}
	public final void setType(Material type){
		if(!isNull())
			icon.setType(type);
	}
	public final Material getType(){
		if(!isNull())
			return icon.getType();
		return null;
	}
	public final void setAmount(int amount){
		if(!isNull())
			icon.setAmount(amount);
	}
	public final int getAmount(){
		if(!isNull())
			return icon.getAmount();
		return 0;
	}
	public final void setDurability(short durability){
		if(!isNull())
			icon.setDurability(durability);
	}
	public final short getDurability(){
		if(!isNull())
			return icon.getDurability();
		return 0;
	}
	public final void setHighlight(boolean enable){
		if(!isNull())
			if(isHighlight() != enable)
				if(enable){
					icon.getItemMeta().addEnchant(Enchantment.LUCK, 1, false);
				}else{
					icon.getItemMeta().removeEnchant(Enchantment.LUCK);
				}
	}
	public final boolean isHighlight(){
		if(!isNull())
			return icon.getItemMeta().hasEnchants();
		return false;
	}
	public final boolean isNull(){
		return icon == null;
	}
	public void onClick(ClickIconEvent event){}
}
