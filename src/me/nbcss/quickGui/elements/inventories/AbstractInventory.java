package me.nbcss.quickGui.elements.inventories;

import java.util.ArrayList;
//import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
//import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.nbcss.quickGui.Operator;
import me.nbcss.quickGui.elements.Icon;
import me.nbcss.quickGui.elements.Synchronizable;
//import me.nbcss.quickGui.elements.InventoryView;
import me.nbcss.quickGui.events.InventoryChangeEvent;
import me.nbcss.quickGui.events.InventoryCloseEvent;
import me.nbcss.quickGui.events.InventoryOpenEvent;
import me.nbcss.quickGui.utils.wrapperPackets.AbstractPacket;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerSetSlot;

public abstract class AbstractInventory implements Cloneable {
	protected static final ItemStack AIR = new ItemStack(Material.AIR);
	private ArrayList<Player> watchers;
	private String title;
	private Icon[] items;
	private final String type;
	private final int slot;
	private final int numSlot;
	protected AbstractInventory(String type, int slot, int numSlot, String name){
		items = new Icon[slot];
		for(int i = 0; i < slot; i++)
			items[i] = new Icon(AIR);
		this.type = type;
		this.slot = slot;
		this.numSlot = numSlot;
		this.title = name;
		if(this instanceof Synchronizable)
			watchers = new ArrayList<Player>();
		else
			watchers = null;
	}
	
	public void setIconElement(int slot, Icon icon){
		if(slot >= items.length || slot < 0)
			return;
		items[slot] = icon;
	}
	
	public Icon getIconElement(int slot){
		if(slot >= items.length || slot < 0)
			return null;
		return items[slot];
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
	
	public void onClose(InventoryCloseEvent event){
		if(watchers == null)
			return;
		watchers.remove(event.getPlayer());
	}
	
	public void onOpen(InventoryOpenEvent event){
		if(watchers == null)
			return;
		if(!watchers.contains(event.getPlayer()))
			watchers.add(event.getPlayer());
	}
	
	public void onChange(InventoryChangeEvent event){
		if(watchers == null)
			return;
		Player[] players = event.getChangedInventoryView().getWatchers();
		if(event.isReplaced())
			for(Player watcher : players)
				watchers.remove(watcher);
		else
			for(Player watcher : players)
				if(!watchers.contains(watcher))
					watchers.add(watcher);
	}
	
	protected void sendPacket(Player receiver, AbstractPacket packet){
		if(watchers == null){
			packet.sendPacket(receiver);
			return;
		}
		for(Player player : watchers)
			packet.sendPacket(player);
	}
	
	public void update(Player receiver){
		for(int i = 0; i < items.length; i++){
			WrapperPlayServerSetSlot packet = new WrapperPlayServerSetSlot();
			packet.setSlot(i);
			if(items[i] != null)
				packet.setSlotData(items[i].getItem());
			else
				packet.setSlotData(AIR);
			packet.setWindowId(Operator.getWindowID());
			sendPacket(receiver, packet);
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
