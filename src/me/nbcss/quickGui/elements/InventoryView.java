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
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayClientWindowClick.InventoryClickType;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerOpenWindow;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerSetSlot;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerWindowItems;

public class InventoryView {
	private final AbstractInventory topInventory;
	private final BottomInventory bottomInventory;
	private final HotbarInventory hotbarInventory;
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
		int key = packet.getButton();
		InventoryClickType type = packet.getShift();
		int inventorySlot = getInventorySlot(viewSlot);
		InteractAction action = InteractAction.fromInventoryAction(key, type, viewSlot);
		AbstractInventory clickedInventory = getLocatedInventory(viewSlot);
		Icon icon = null;
		if(clickedInventory != null && inventorySlot >= 0)
			icon = clickedInventory.getIconElement(inventorySlot);
		if(cursor != null){
			InventoryInteractEvent event = new InventoryInteractEvent(true, player, action, this, clickedInventory);
			cursor.onInteract(event);
		}
		if(icon != null){
			InventoryInteractEvent event = new InventoryInteractEvent(false, player, action, this, clickedInventory);
			icon.onInteract(event);
		}
		updateContents(player);
		updateCursor(player);
	}
	public WrapperPlayServerOpenWindow getOpenWindowPacket(){
		WrapperPlayServerOpenWindow packet = new WrapperPlayServerOpenWindow();
		packet.setWindowID(Operator.getWindowID());
		packet.setInventoryType(topInventory.getType());
		packet.setNumberOfSlots(topInventory.getNumSlot());
		packet.setWindowTitle(WrappedChatComponent.fromText(topInventory.getTitle()));
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
		updateCursor(player);
		int total = topInventory.getSlot() + 36;
		for(int i = 0; i < total; i++)
			updateSlot(player, i);
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
		WrapperPlayServerSetSlot packet = new WrapperPlayServerSetSlot();
		AbstractInventory inv = getLocatedInventory(viewSlot);
		int slot = getInventorySlot(viewSlot);
		if(slot < 0)
			return;
		ItemStack item = null;
		Icon icon = inv.getIconElement(slot);
		if(icon != null)
			item = icon.getItem();
		packet.setSlot(viewSlot);
		packet.setSlotData(item);
		packet.setWindowId(Operator.getWindowID());
		packet.sendPacket(player);
	}
}
