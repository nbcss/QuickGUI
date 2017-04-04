<<<<<<< HEAD
package me.nbcss.quickGui.elements.inventories;

import org.bukkit.inventory.ItemStack;

import me.nbcss.quickGui.elements.ClickIconEvent;
import me.nbcss.quickGui.elements.Icon;

public abstract class AbstractInventory implements Cloneable {
	//private String title;
	private Icon[] items;
	private final String type;
	private final int slot;
	private final int numSlot;
	protected AbstractInventory(String type, int slot, int numSlot){
		items = new Icon[slot];
		this.type = type;
		this.slot = slot;
		this.numSlot = numSlot;
	}
	
	protected final void setIconElement(int slot, Icon icon){
		if(slot >= items.length || slot < 0)
			return;
		items[slot] = icon;
	}
	
	protected final Icon getIconElement(int slot){
		if(slot >= items.length || slot < 0)
			return null;
		return items[slot];
	}
	
	public final int getSlot(){
		return slot;
	}
	
	public final String getType(){
		return type;
	}
	
	public final ItemStack[] getItemList(){
		ItemStack[] list = new ItemStack[slot];
		for(int i = 0; i < slot; i++)
			if(items[i] != null)
				list[i] = items[i].getItem();
		return list;
	}
	
	@Override
	public final AbstractInventory clone(){
		AbstractInventory inv = null;
		try {
			inv = (AbstractInventory) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
		inv.items = new Icon[slot];
		for(int i = 0; i < slot; i++)
			inv.items[i] = items[i];
		return inv;
	}

	public int getNumSlot() {
		return numSlot;
	}
	
	public void onClick(ClickIconEvent e){
		if(items[e.getSlot()] == null)
			return;
		Icon icon = items[e.getSlot()];
		icon.onClick(e);
	}
}
=======
package me.nbcss.quickGui.elements.inventories;

import org.bukkit.inventory.ItemStack;

import me.nbcss.quickGui.elements.ClickIconEvent;
import me.nbcss.quickGui.elements.Icon;

public abstract class AbstractInventory implements Cloneable {
	//private String title;
	private Icon[] items;
	private final String type;
	private final int slot;
	private final int numSlot;
	protected AbstractInventory(String type, int slot, int numSlot){
		items = new Icon[slot];
		this.type = type;
		this.slot = slot;
		this.numSlot = numSlot;
	}
	
	protected final void setIconElement(int slot, Icon icon){
		if(slot >= items.length || slot < 0)
			return;
		items[slot] = icon;
	}
	
	protected final Icon getIconElement(int slot){
		if(slot >= items.length || slot < 0)
			return null;
		return items[slot];
	}
	
	public final int getSlot(){
		return slot;
	}
	
	public final String getType(){
		return type;
	}
	
	public final ItemStack[] getItemList(){
		ItemStack[] list = new ItemStack[slot];
		for(int i = 0; i < slot; i++)
			if(items[i] != null)
				list[i] = items[i].getItem();
		return list;
	}
	
	@Override
	public final AbstractInventory clone(){
		AbstractInventory inv = null;
		try {
			inv = (AbstractInventory) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
		inv.items = new Icon[slot];
		for(int i = 0; i < slot; i++)
			inv.items[i] = items[i];
		return inv;
	}

	public int getNumSlot() {
		return numSlot;
	}
	
	public void onClick(ClickIconEvent e){
		if(items[e.getSlot()] == null)
			return;
		Icon icon = items[e.getSlot()];
		icon.onClick(e);
	}
}
>>>>>>> origin/master
