package me.nbcss.quickGui.elements.inventories;

import org.bukkit.inventory.ItemStack;

import me.nbcss.quickGui.elements.Icon;

public class AnvilInventory extends AbstractInventory {
	private static final String TYPE = "minecraft:anvil";
	private static final String NAME = "Anvil";
	public AnvilInventory() {
		super(TYPE, 3, 0, NAME);
	}
	public void setLeftInputIcon(Icon icon){
		setIconElement(0, icon);
	}
	public void setRightInputIcon(Icon icon){
		setIconElement(1, icon);
	}
	public void setOutputIcon(Icon icon){
		setIconElement(2, icon);
	}
	public Icon getLeftInputIcon(){
		return getIconElement(0);
	}
	public Icon getRightInputIcon(){
		return getIconElement(1);
	}
	public Icon getOutputIcon(){
		return getIconElement(2);
	}
	@Override
	public boolean isLegalItemStack(int slot, ItemStack item) {
		if(slot < 0 || slot >= getSlot())
			return false;
		if(slot == 2)
			return false;
		return true;
	}
}
