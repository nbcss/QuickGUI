package me.nbcss.quickGui.elements;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
//import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.nbcss.quickGui.events.InventoryInteractEvent;


public class Icon {
	private static final GlowEnchant GLOW = new GlowEnchant();
	private ItemStack icon;
	public Icon(){
		icon = null;
	}
	public Icon(ItemStack item) {
		icon = item;
	}
	public void setItem(ItemStack item){
		icon = item;
	}
	public ItemStack getItem(){
		return icon;
	}
	public void setName(String name){
		if(!isNull()){
			ItemMeta meta = icon.getItemMeta();
			meta.setDisplayName(name);
			icon.setItemMeta(meta);
		}
	}
	public String getName(){
		if(!isNull())
			return icon.getItemMeta().getDisplayName();
		return null;
	}
	public void setLore(List<String> lore){
		if(!isNull()){
			ItemMeta meta = icon.getItemMeta();
			meta.setLore(lore);
			icon.setItemMeta(meta);
		}
	}
	public void setLore(String... lore){
		if(!isNull()){
			ItemMeta meta = icon.getItemMeta();
			List<String> list = new ArrayList<String>();
			for(String line : lore)
				list.add(line);
			meta.setLore(list);
			icon.setItemMeta(meta);
		}
	}
	public List<String> getLore(){
		if(!isNull())
			return icon.getItemMeta().getLore();
		return null;
	}
	public void setType(Material type){
		if(!isNull())
			icon.setType(type);
	}
	public Material getType(){
		if(!isNull())
			return icon.getType();
		return null;
	}
	public void setAmount(int amount){
		if(!isNull())
			icon.setAmount(amount);
	}
	public int getAmount(){
		if(!isNull())
			return icon.getAmount();
		return 0;
	}
	public void setDurability(short durability){
		if(!isNull())
			icon.setDurability(durability);
	}
	public short getDurability(){
		if(!isNull())
			return icon.getDurability();
		return 0;
	}
	public void setGlow(boolean enable){
		if(!isNull())
			if(isGlow() != enable)
				if(enable){
					ItemMeta meta = icon.getItemMeta();
					meta.addEnchant(GLOW, 1, true);
					icon.setItemMeta(meta);
				}else{
					ItemMeta meta = icon.getItemMeta();
					meta.removeEnchant(GLOW);
					icon.setItemMeta(meta);
				}
	}
	public boolean isGlow(){
		if(!isNull())
			return icon.getItemMeta().hasEnchant(GLOW);
		return false;
	}
	public void addEnchant(Enchantment ench, int level, boolean ignoreLevelRestriction){
		if(!isNull()){
			ItemMeta meta = icon.getItemMeta();
			meta.addEnchant(ench, level, ignoreLevelRestriction);
			icon.setItemMeta(meta);
		}
	}
	public void removeEnchant(Enchantment ench){
		if(!isNull()){
			ItemMeta meta = icon.getItemMeta();
			meta.removeEnchant(ench);
			icon.setItemMeta(meta);
		}
	}
	public boolean hasEnchant(Enchantment ench){
		if(!isNull())
			return icon.getItemMeta().hasEnchant(ench);
		return false;
	}
	public boolean isNull(){
		return icon == null;
	}
	public void onInteract(InventoryInteractEvent event){}
}
