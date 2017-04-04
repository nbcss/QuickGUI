package me.nbcss.quickGui.elements.inventories;

import me.nbcss.quickGui.elements.Icon;

public class AnvilInventory extends AbstractInventory {
	private static final String TYPE = "minecraft:anvil";
	public AnvilInventory() {
		super(TYPE, 3, 0);
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
}
