package me.nbcss.quickGui.elements;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
//import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.nbcss.quickGui.events.ClickEvent;


public class Icon {
	private static final GlowEnchant GLOW = new GlowEnchant();
	private boolean movable;
	private ItemStack icon;
	public Icon(){
		icon = null;
		setMovable(false);
	}
	public Icon(ItemStack item) {
		icon = item;
		setMovable(false);
	}
	public void setItem(ItemStack item){
		icon = item;
		setMovable(false);
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
	public boolean isMovable() {
		return movable;
	}
	public void setMovable(boolean movable) {
		this.movable = movable;
	}
	public void onClick(ClickEvent event){}
	//call when move icon to cursor (mode 0, 3, 6)
	public void onPickupIcon(){}
	//call when move icon from cursor
	public void onPutDownIcon(){}
	//call when drag icon from cursor to slots (mode 5)
	public void onDragIcon(){} 
	//call when quick move icon to another inventory (shift) (mode 1)
	public void onQuickMoveIcon(){}
	//call when icon swap with hotbar icon (mode 2)
	public void onSwapWithHotbar(){}
}
