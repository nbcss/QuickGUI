package me.nbcss.quickGui.elements.inventories;

import org.bukkit.inventory.ItemStack;

import me.nbcss.quickGui.elements.Icon;

public class FurnaceInventory extends AbstractInventory {
	private static final String TYPE = "minecraft:furnace";
	private static final String NAME = "Furnace";
	public FurnaceInventory() {
		super(TYPE, 3, 3, NAME);
	}
	public void setIngredientIcon(Icon icon){
		setIconElement(0, icon);
	}
	public void setFuelIcon(Icon icon){
		setIconElement(1, icon);
	}
	public void setOutputIcon(Icon icon){
		setIconElement(2, icon);
	}
	public Icon getIngredientIcon(){
		return getIconElement(0);
	}
	public Icon getFuelInputIcon(){
		return getIconElement(1);
	}
	public Icon getOutputIcon(){
		return getIconElement(2);
	}
	@Override
	public boolean isLegalItemStack(int slot, ItemStack item) {
		if(slot < 0 || slot >= getSlot())
			return false;
		if(slot == 1 && !item.getType().isFuel())
			return false;
		if(slot == 2)
			return false;
		return true;
	}
}
