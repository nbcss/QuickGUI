package me.nbcss.quickGui.elements.inventories;

import org.bukkit.inventory.ItemStack;

import me.nbcss.quickGui.elements.Icon;

public class DropperInventory extends AbstractInventory {
	private static final String TYPE = "minecraft:dropper";
	private static final String NAME = "Dropper";
	public DropperInventory() {
		super(TYPE, 9, 9, NAME);
		
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
