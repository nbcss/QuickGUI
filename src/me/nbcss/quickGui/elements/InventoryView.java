package me.nbcss.quickGui.elements;

import java.util.ArrayList;

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
		InventoryClickType type = packet.getShift();
		ClickAction action = ClickAction.fromInventoryAction(packet.getButton(), type);
		int inventorySlot = getInventorySlot(viewSlot);
		AbstractInventory clickedInventory = getLocatedInventory(viewSlot);
		if(inventorySlot < 0)
			return;
		Icon icon = clickedInventory.getIconElement(inventorySlot);
		boolean cursorResult, clickedResult;
		switch(type){
		case PICKUP:
			if(cursor == null && icon == null)
				return;
			cursorResult = false;
			if(cursor != null){
				ClickEvent event = new NormalClickEvent(player, action, ClickType.MOVE_OUT_CURSOR, inventorySlot, clickedInventory);
				cursor.onClick(event);
				cursorResult = event.isCancelled();
			}
			clickedResult = false;
			if(icon != null){
				ClickEvent event = new NormalClickEvent(player, action, ClickType.MOVE_INTO_CURSOR, inventorySlot, clickedInventory);
				icon.onClick(event);
				clickedResult = event.isCancelled();
			}
			if(cursorResult || clickedResult || clickedInventory.isLocked()){
				updateCursor(player);
				if(icon != null)
					updateSlot(player, viewSlot);
			}else{
				Icon replace = null;
				if(cursor != null)
					replace = cursor;
				cursor = icon;
				clickedInventory.setIconElement(inventorySlot, replace);
			}
			break;
		case QUICK_MOVE:
			if(icon == null)
				return;
			clickedResult = false;
			ArrayList<Integer> slots = new ArrayList<Integer>();
			int lastSlot = -1;
			ClickEvent event = new QuickMoveClickEvent(player, action, ClickType.NONE, inventorySlot, clickedInventory);
			icon.onClick(event);
			clickedResult = event.isCancelled();
			if(icon.isNull())
				return;
			boolean changed = false;
			boolean cancelled = false;
			int count = icon.getAmount();
			int max = icon.getItem().getMaxStackSize();
			int last = 0;
			if(clickedInventory.equals(topInventory)){
				boolean bottom = false;
				boolean hotbar = false;
				for(int i = 8; i >= 0; i--){
					if(count == 0)
						break;
					if(hotbarInventory.getIconElement(i) == null)
						continue;
					if(icon.getItem().isSimilar(hotbarInventory.getIconElement(i).getItem())){
						ItemStack compare = hotbarInventory.getIconElement(i).getItem();
						if(compare.getAmount() >= max)
							continue;
						hotbar = true;
						count -= max - compare.getAmount();
						if(count < 0){
							last = -count;
							lastSlot = topInventory.getSlot() + bottomInventory.getSlot() + i;
							count = 0;
							break;
						}
						slots.add(topInventory.getSlot() + bottomInventory.getSlot() + i);
					}
				}
				for(int i = 26; i >= 0; i--){
					if(count == 0)
						break;
					if(bottomInventory.getIconElement(i) == null)
						continue;
					if(icon.getItem().isSimilar(bottomInventory.getIconElement(i).getItem())){
						ItemStack compare = bottomInventory.getIconElement(i).getItem();
						if(compare.getAmount() >= max)
							continue;
						bottom = true;
						count -= max - compare.getAmount();
						if(count < 0){
							last = -count;
							lastSlot = topInventory.getSlot() + i;
							count = 0;
							break;
						}
						slots.add(topInventory.getSlot() + i);
					}
				}
				if(count > 0){
					for(int i = 8; i >= 0; i--){
						if(count == 0)
							break;
						if(hotbarInventory.getIconElement(i) == null){
							hotbar = true;
							lastSlot = topInventory.getSlot() + bottomInventory.getSlot() + i;
							last = count;
							count = 0;
							break;
						}
						if(hotbarInventory.getIconElement(i).isNull()){
							lastSlot = topInventory.getSlot() + bottomInventory.getSlot() + i;
							last = count; //****!!!!
							count = 0;
							break;
						}
					}
					for(int i = 26; i >= 0; i--){
						if(count == 0)
							break;
						if(bottomInventory.getIconElement(i) == null){
							bottom = true;
							lastSlot = topInventory.getSlot() + i;
							last = count;
							count = 0;
							break;
						}
						if(bottomInventory.getIconElement(i).isNull()){
							lastSlot = topInventory.getSlot() + i;
							last = count; //****!!!!
							count = 0;
							break;
						}
					}
				}
				if(bottom || hotbar)
					changed = true;
				if(clickedResult || (hotbar && hotbarInventory.isLocked()) || (bottom && bottomInventory.isLocked()) || clickedInventory.isLocked())
					cancelled = true;
			}else{
				boolean top = false;
				for(int i = 0; i < topInventory.getSlot(); i++){
					if(count == 0)
						break;
					if(topInventory.getIconElement(i) == null)
						continue;
					if(icon.getItem().isSimilar(topInventory.getIconElement(i).getItem())){
						ItemStack compare = topInventory.getIconElement(i).getItem();
						if(compare.getAmount() >= max)
							continue;
						top = true;
						count -= max - compare.getAmount();
						if(count <= 0){
							last = -count;
							lastSlot = i;
							count = 0;
							break;
						}
						slots.add(i);
					}
				}
				if(count > 0){
					for(int i = 0; i < topInventory.getSlot(); i++){
						if(count == 0)
							break;
						if(topInventory.getIconElement(i) == null){
							top = true;
							lastSlot = i;
							last = count;
							count = 0;
							break;
						}
						if(bottomInventory.getIconElement(i).isNull()){
							lastSlot = i;
							last = count; //****!!!!
							count = 0;
							break;
						}
					}
				}
				if(top)
					changed = true;
				if(clickedResult || clickedInventory.isLocked() || (top && bottomInventory.isLocked()))
					cancelled = true;
			}
			if(changed){
				if(cancelled){
					updateSlot(player, viewSlot);
					for(int i : slots)
						updateSlot(player, i);
					if(lastSlot >= 0)
						updateSlot(player, lastSlot);
				}else{
					for(int i : slots)
						getLocatedInventory(i).getIconElement(getInventorySlot(i)).setAmount(max);
					if(lastSlot >= 0){
						AbstractInventory inv = getLocatedInventory(lastSlot);
						int slot = getInventorySlot(lastSlot);
						Icon before = inv.getIconElement(slot);
						if(before != null){
							int amount = before.getAmount() + last;
							before.setAmount(amount);
						}else{
							before = new Icon(icon.getItem());
							before.setAmount(last);
						}
						inv.setIconElement(slot, before);
					}
					clickedInventory.setIconElement(inventorySlot, null);
				}
			}
			break;
		case SWAP:
			if(cursor == null){
				
			}
		default:
			break;
		}
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
	private AbstractInventory getLocatedInventory(int viewSlot){
		if(viewSlot < topInventory.getSlot()){
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
	private void updateCursor(Player player){
		WrapperPlayServerSetSlot packet = new WrapperPlayServerSetSlot();
		packet.setWindowId(-1);
		packet.setSlot(-1);
		ItemStack item = null;
		if(cursor != null)
			item = cursor.getItem();
		packet.setSlotData(item);
		packet.sendPacket(player);
	}
	private void updateSlot(Player player, int viewSlot){
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
		packet.setWindowId(MainClass.getID());
		packet.sendPacket(player);
	}
}
