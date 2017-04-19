package me.nbcss.quickGui.elements;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.comphenix.protocol.wrappers.WrappedChatComponent;

import me.nbcss.quickGui.Operator;
import me.nbcss.quickGui.elements.inventories.AbstractInventory;
import me.nbcss.quickGui.elements.inventories.BottomInventory;
import me.nbcss.quickGui.elements.inventories.CustomInventory;
import me.nbcss.quickGui.elements.inventories.HotbarInventory;
import me.nbcss.quickGui.events.CloseInventoryEvent;
import me.nbcss.quickGui.events.InteractAction;
import me.nbcss.quickGui.events.InventoryInteractEvent;
import me.nbcss.quickGui.events.OpenInventoryEvent;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayClientWindowClick;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerCloseWindow;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayClientWindowClick.InventoryClickType;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerOpenWindow;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerSetSlot;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerWindowItems;

public class InventoryView {
	private ArrayList<Player> watchers;
	private String title;
	private AbstractInventory topInventory;
	private BottomInventory bottomInventory;
	private HotbarInventory hotbarInventory;
	private Icon cursor;
	public InventoryView(AbstractInventory inventory, BottomInventory bottom, HotbarInventory hotbar){
		watchers = new ArrayList<Player>();
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
		title = topInventory.getTitle();
	}
	public Icon getCursor() {
		return cursor;
	}
	public void setCursor(Icon cursor) {
		this.cursor = cursor;
	}
	public void setTitle(String title){
		this.title = title;
		WrapperPlayServerOpenWindow packet = getOpenWindowPacket();
		WrapperPlayServerSetSlot[] array = topInventory.getSlotPacketsArray();
		for(Player watcher : watchers){
			packet.sendPacket(watcher);
			for(WrapperPlayServerSetSlot slot : array)
				slot.sendPacket(watcher);
		}
	}
	public String getTitle(){
		return title;
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
	public void setTopInventory(AbstractInventory inventory){
		if(inventory == null)
			return;
		topInventory = inventory;
		title = topInventory.getTitle();
		WrapperPlayServerOpenWindow packet = getOpenWindowPacket();
		WrapperPlayServerSetSlot[] array = topInventory.getSlotPacketsArray();
		for(Player watcher : watchers){
			packet.sendPacket(watcher);
			for(WrapperPlayServerSetSlot slot : array)
				slot.sendPacket(watcher);
		}
	}
	public void setBottomInventory(BottomInventory inventory){
		if(inventory == null)
			return;
		bottomInventory = inventory;
		WrapperPlayServerSetSlot[] array = bottomInventory.getSlotPacketsArray();
		for(Player watcher : watchers)
			for(WrapperPlayServerSetSlot slot : array)
				slot.sendPacket(watcher);
	}
	public void setHotbarInventory(HotbarInventory inventory){
		if(inventory == null)
			return;
		hotbarInventory = inventory;
		WrapperPlayServerSetSlot[] array = hotbarInventory.getSlotPacketsArray();
		for(Player watcher : watchers)
			for(WrapperPlayServerSetSlot slot : array)
				slot.sendPacket(watcher);
	}
	public Player[] getWatchers(){
		Player[] array = new Player[watchers.size()];
		for(int i = 0; i < array.length; i++)
			array[i] = watchers.get(i);
		return array;
	}
	//==============================================*****
	public void openInventoryView(Player player){
		OpenInventoryEvent e = new OpenInventoryEvent(player);
		topInventory.onOpen(e);
		bottomInventory.onOpen(e);
		hotbarInventory.onOpen(e);
		watchers.add(player);
		WrapperPlayServerOpenWindow window = getOpenWindowPacket();
		window.sendPacket(player);
		WrapperPlayServerWindowItems setup = getWindowItemsPacket();
		setup.sendPacket(player);
		//open
	}
	public void closeInventoryView(Player player){
		if(!watchers.contains(player))
			return;
		CloseInventoryEvent e = new CloseInventoryEvent(player);
		topInventory.onClose(e);
		bottomInventory.onClose(e);
		hotbarInventory.onClose(e);
		watchers.remove(player);
		WrapperPlayServerCloseWindow packet = new WrapperPlayServerCloseWindow();
		packet.setWindowId(Operator.getWindowID());
		packet.sendPacket(player);
	}
	public void onClickInventoryView(WrapperPlayClientWindowClick packet, Player player){
		int viewSlot = packet.getSlot();
		int key = packet.getButton();
		InventoryClickType type = packet.getShift();
		int inventorySlot = getInventorySlot(viewSlot);
		InteractAction action = InteractAction.fromInventoryAction(key, type, viewSlot);
		AbstractInventory clickedInventory = getLocatedInventory(viewSlot);
		Icon icon = null;
		if(clickedInventory != null && inventorySlot >= 0)
			icon = clickedInventory.getIconElement(inventorySlot);
		boolean updateCursor = false, updateIcon = false;
		if(cursor != null){
			InventoryInteractEvent event = new InventoryInteractEvent(true, player, action, this, clickedInventory);
			cursor.onInteract(event);
			updateCursor = event.isUpdate();
		}
		if(icon != null){
			InventoryInteractEvent event = new InventoryInteractEvent(false, player, action, this, clickedInventory);
			icon.onInteract(event);
			updateIcon = event.isUpdate();
		}
		if(updateCursor || updateIcon){
			updateContents(player);
			//updateCursor(player);
		}
	}
	public WrapperPlayServerOpenWindow getOpenWindowPacket(){
		WrapperPlayServerOpenWindow packet = new WrapperPlayServerOpenWindow();
		packet.setWindowID(Operator.getWindowID());
		packet.setInventoryType(topInventory.getType());
		packet.setNumberOfSlots(topInventory.getNumSlot());
		packet.setWindowTitle(WrappedChatComponent.fromText(title));
		return packet;
	}
	public WrapperPlayServerWindowItems getWindowItemsPacket(){
		WrapperPlayServerWindowItems packet = new WrapperPlayServerWindowItems();
		packet.setWindowId(Operator.getWindowID());
		ItemStack[] inv = topInventory.getItemList();
		ItemStack[] down = bottomInventory.getItemList();
		ItemStack[] shortcut = hotbarInventory.getItemList();
		ArrayList<ItemStack> data = new ArrayList<ItemStack>();
		for(int i = 0; i < inv.length; i++)
			data.add(inv[i]);
		for(int i = 0; i < down.length; i++)
			data.add(down[i]);
		for(int i = 0; i < shortcut.length; i++)
			data.add(shortcut[i]);
		packet.setSlotData(data);
		return packet;
	}
	public void updateContents(Player player){
		if(!Operator.getOpenedInventoryView(player).equals(this))
			return;
		updateCursor(player);
		WrapperPlayServerSetSlot[] top = topInventory.getSlotPacketsArray();
		WrapperPlayServerSetSlot[] bottom = bottomInventory.getSlotPacketsArray();
		WrapperPlayServerSetSlot[] hotbar = hotbarInventory.getSlotPacketsArray();
		for(WrapperPlayServerSetSlot packet : top)
			packet.sendPacket(player);
		for(WrapperPlayServerSetSlot packet : bottom)
			packet.sendPacket(player);
		for(WrapperPlayServerSetSlot packet : hotbar)
			packet.sendPacket(player);
	}
	private AbstractInventory getLocatedInventory(int viewSlot){
		if(viewSlot < 0){
			return null;
		}else if(viewSlot < topInventory.getSlot()){
			return topInventory;
		}else if(viewSlot < topInventory.getSlot() + bottomInventory.getSlot()){
			return bottomInventory;
		}else if(viewSlot < topInventory.getSlot() + bottomInventory.getSlot() + hotbarInventory.getSlot()){
			return hotbarInventory;
		}else{
			return null;
		}
	}
	private int getInventorySlot(int viewSlot){
		AbstractInventory inv = getLocatedInventory(viewSlot);
		if(inv == null)
			return -1;
		int slot = -1;
		if(inv.equals(topInventory)){
			slot = viewSlot;
		}else if(inv.equals(bottomInventory)){
			slot = viewSlot - topInventory.getSlot();
		}else if(inv.equals(hotbarInventory)){
			slot = viewSlot - topInventory.getSlot() - bottomInventory.getSlot();
		}
		return slot;
	}
	public void updateCursor(Player player){
		if(!Operator.getOpenedInventoryView(player).equals(this))
			return;
		WrapperPlayServerSetSlot packet = new WrapperPlayServerSetSlot();
		packet.setWindowId(-1);
		packet.setSlot(-1);
		ItemStack item = null;
		if(cursor != null)
			item = cursor.getItem();
		packet.setSlotData(item);
		packet.sendPacket(player);
	}
	public void updateSlot(Player player, int viewSlot){
		if(!Operator.getOpenedInventoryView(player).equals(this))
			return;
		AbstractInventory inv = getLocatedInventory(viewSlot);
		int slot = getInventorySlot(viewSlot);
		if(slot < 0)
			return;
		WrapperPlayServerSetSlot packet = inv.getSlotPacket(slot);
		packet.sendPacket(player);
	}
}
