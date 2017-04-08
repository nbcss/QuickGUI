package me.nbcss.quickGui.elements.inventories;

import org.bukkit.inventory.ItemStack;

import me.nbcss.quickGui.elements.Icon;

public class HopperInventory extends AbstractInventory {
	private static final String TYPE = "minecraft:hopper";
	private static final String NAME = "Hopper";
	public HopperInventory() {
		super(TYPE, 5, 5, NAME);
	}
	public void setIcon(int slot, Icon item){
		if(slot < 0 || slot >= getSlot())
			return;
		super.setIconElement(slot, item);
	}
	public Icon getIcon(int slot){
		if(slot < 0 || slot >= getSlot())
			return null;
		return super.getIconElement(slot);
	}
	@Override
	public boolean isLegalItemStack(int slot, ItemStack item) {
		if(slot < 0 || slot >= getSlot())
			return false;
		return true;
	}
}
