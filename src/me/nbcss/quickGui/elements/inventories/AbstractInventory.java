package me.nbcss.quickGui.elements.inventories;

import java.util.ArrayList;
//import java.util.List;

import org.bukkit.Material;
//import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.nbcss.quickGui.Operator;
import me.nbcss.quickGui.elements.Icon;
import me.nbcss.quickGui.events.CloseInventoryEvent;
import me.nbcss.quickGui.events.OpenInventoryEvent;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerSetSlot;

public abstract class AbstractInventory implements Cloneable {
	private static final ItemStack AIR = new ItemStack(Material.AIR);
	private String title;
	private Icon[] items;
	private final String type;
	private final int slot;
	private final int numSlot;
	protected AbstractInventory(String type, int slot, int numSlot, String name){
		items = new Icon[slot];
		this.type = type;
		this.slot = slot;
		this.numSlot = numSlot;
		setTitle(name);
	}
	
	public final void setIconElement(int slot, Icon icon){
		if(slot >= items.length || slot < 0)
			return;
		items[slot] = icon;
	}
	
	public final Icon getIconElement(int slot){
		if(slot >= items.length || slot < 0)
			return null;
		return items[slot];
	}
	public boolean isSlotEmpty(int slot){
		if(slot < 0 || slot >= items.length)
			return true;
		return items[slot] == null;
	}
	
	public int getSlot(){
		return slot;
	}
	
	public String getType(){
		return type;
	}
	
	public ItemStack[] getItemList(){
		ItemStack[] list = new ItemStack[slot];
		for(int i = 0; i < slot; i++)
			if(items[i] != null)
				list[i] = items[i].getItem();
			else
				list[i] = AIR;	
		return list;
	}
	
	@Override
	public AbstractInventory clone(){
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
	
	public WrapperPlayServerSetSlot[] getSlotPacketsArray(){
		ArrayList<WrapperPlayServerSetSlot> array = new ArrayList<WrapperPlayServerSetSlot>();
		for(int i = 0; i < slot; i++){
			WrapperPlayServerSetSlot packet = new WrapperPlayServerSetSlot();
			packet.setSlot(i);
			if(items[i] != null)
				packet.setSlotData(items[i].getItem());
			else
				packet.setSlotData(AIR);
			packet.setWindowId(Operator.getWindowID());
			array.add(packet);
		}
		return array.toArray(new WrapperPlayServerSetSlot[array.size()]);
	}
	
	public WrapperPlayServerSetSlot getSlotPacket(int slot){
		if(slot < 0 || slot >= items.length)
			return null;
		WrapperPlayServerSetSlot packet = new WrapperPlayServerSetSlot();
		packet.setSlot(slot);
		if(items[slot] != null)
			packet.setSlotData(items[slot].getItem());
		else
			packet.setSlotData(AIR);
		packet.setWindowId(Operator.getWindowID());
		return packet;
	}
	
	public void onClose(CloseInventoryEvent event){}
	
	public void onOpen(OpenInventoryEvent event){}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
