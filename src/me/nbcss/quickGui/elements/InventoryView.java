package me.nbcss.quickGui.elements;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.comphenix.protocol.wrappers.WrappedChatComponent;

import me.nbcss.quickGui.MainClass;
import me.nbcss.quickGui.elements.inventories.AbstractInventory;
import me.nbcss.quickGui.elements.inventories.BottomInventory;
import me.nbcss.quickGui.elements.inventories.CustomInventory;
import me.nbcss.quickGui.elements.inventories.HotbarInventory;
import me.nbcss.quickGui.events.ClickAction;
import me.nbcss.quickGui.events.ClickEvent;
import me.nbcss.quickGui.events.ClickType;
import me.nbcss.quickGui.events.CloseInventoryEvent;
import me.nbcss.quickGui.events.NormalClickEvent;
import me.nbcss.quickGui.events.OpenInventoryEvent;
import me.nbcss.quickGui.events.QuickMoveClickEvent;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayClientWindowClick;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayClientWindowClick.InventoryClickType;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerOpenWindow;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerSetSlot;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerWindowItems;

public class InventoryView {
	private AbstractInventory topInventory;
	private BottomInventory bottomInventory;
	private HotbarInventory hotbarInventory;
	private Icon cursor;
	public InventoryView(AbstractInventory inventory, BottomInventory bottom, HotbarInventory hotbar){
		if(inventory == null)
			inventory = CustomInventory.constractCustomInventory(0);
		topInventory = inventory;
		if(bottom == null)
			bottom = new BottomInventory();
		bottomInventory = bottom;
		if(hotbar == null)
			hotbar = new HotbarInventory();
		hotbarInventory = hotbar;
		cursor = null;
	}
	public Icon getCursor() {
		return cursor;
	}
	public void setCursor(Icon cursor) {
		this.cursor = cursor;
	}
	public HotbarInventory getHotbarInventory() {
		return hotbarInventory;
	}
	public BottomInventory getBottomInventory() {
		return bottomInventory;
	}
	public AbstractInventory getTopInventory() {
		return topInventory;
	}
	//==============================================*****
	public void onOpenInventoryView(Player player){
		OpenInventoryEvent e = new OpenInventoryEvent(player);
		topInventory.onOpen(e);
		bottomInventory.onOpen(e);
		hotbarInventory.onOpen(e);
	}
	public void onCloseInventoryView(Player player){
		CloseInventoryEvent e = new CloseInventoryEvent(player);
		topInventory.onClose(e);
		bottomInventory.onClose(e);
		hotbarInventory.onClose(e);
	}
	public void onClickInventoryView(WrapperPlayClientWindowClick packet, Player player){
		int viewSlot = packet.getSlot();
		InventoryClickType type = packet.getShift();
		ClickAction action = ClickAction.fromInventoryAction(packet.getButton(), type);
		int inventorySlot = 0;
		AbstractInventory clickedInventory = null;
		if(viewSlot < 0){
			return;
		}
		if(viewSlot < topInventory.getSlot()){
			inventorySlot = viewSlot;
			clickedInventory = topInventory;
		}else if(viewSlot < topInventory.getSlot() + bottomInventory.getSlot()){
			inventorySlot = viewSlot - topInventory.getSlot();
			clickedInventory = bottomInventory;
		}else if(viewSlot < topInventory.getSlot() + bottomInventory.getSlot() + hotbarInventory.getSlot()){
			inventorySlot = viewSlot - topInventory.getSlot() - bottomInventory.getSlot();
			clickedInventory = hotbarInventory;
		}
		boolean cancelled = false;
		switch(type){
		case PICKUP:
			boolean cursorResult = false;
			if(cursor != null){
				ClickEvent event = new NormalClickEvent(player, action, ClickType.MOVE_OUT_CURSOR, inventorySlot, clickedInventory);
				cursor.onClick(event);
				cursorResult = event.isCancelled();
			}
			boolean clickedResult = false;
			if(clickedInventory.getIconElement(inventorySlot) != null){
				ClickEvent event = new NormalClickEvent(player, action, ClickType.MOVE_INTO_CURSOR, inventorySlot, clickedInventory);
				clickedInventory.getIconElement(inventorySlot).onClick(event);
				clickedResult = event.isCancelled();
			}
			if(cursorResult || clickedResult || clickedInventory.isLocked())
				cancelled = true;
			break;
		case QUICK_MOVE:
			if(clickedInventory.getIconElement(inventorySlot) != null){
				ClickEvent event = new QuickMoveClickEvent(player, action, ClickType.NONE, inventorySlot, clickedInventory);
				clickedInventory.getIconElement(inventorySlot).onClick(event);
			}
		case SWAP:
			if(cursor == null){
				
			}
		default:
			break;
		}
		if(cancelled)
			cancelClick(player, clickedInventory);
	}
	public void onClickOutsideInventoryView(ClickEvent event){
		
	}
	private void cancelClick(Player player, AbstractInventory inv){
		WrapperPlayServerSetSlot packet = new WrapperPlayServerSetSlot();
		packet.setWindowId(-1);
		packet.setSlot(-1);
		ItemStack item = null;
		if(cursor != null)
			item = cursor.getItem();
		packet.setSlotData(item);
		packet.sendPacket(player);
		for(WrapperPlayServerSetSlot p : inv.getSlotPacketsArray())
			p.sendPacket(player);
	}
	public WrapperPlayServerOpenWindow getOpenWindowPacket(){
		WrapperPlayServerOpenWindow packet = new WrapperPlayServerOpenWindow();
		packet.setWindowID(MainClass.getID());
		packet.setInventoryType(topInventory.getType());
		packet.setNumberOfSlots(topInventory.getNumSlot());
		packet.setWindowTitle(WrappedChatComponent.fromText(topInventory.getTitle()));
		return packet;
	}
	public WrapperPlayServerWindowItems getWindowItemsPacket(){
		WrapperPlayServerWindowItems packet = new WrapperPlayServerWindowItems();
		packet.setWindowId(MainClass.getID());
		ItemStack[] inv = topInventory.getItemList();
		ItemStack[] down = bottomInventory.getItemList();
		ItemStack[] shortcut = hotbarInventory.getItemList();
		ItemStack[] data = new ItemStack[inv.length + down.length + shortcut.length];
		int index = 0;
		for(int i = 0; i < inv.length; i++)
			data[index++] = inv[i];
		for(int i = 0; i < down.length; i++)
			data[index++] = down[i];
		for(int i = 0; i < shortcut.length; i++)
			data[index++] = shortcut[i];
		packet.setSlotData(data);
		return packet;
	}
}
