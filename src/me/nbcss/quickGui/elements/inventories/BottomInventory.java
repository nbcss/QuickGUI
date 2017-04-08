package me.nbcss.quickGui.elements.inventories;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.nbcss.quickGui.elements.Icon;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerSetSlot;

public class BottomInventory extends AbstractInventory {
	private static final String TYPE = "minecraft:container";
	private static final String NAME = "Inventory";
	public BottomInventory() {
		super(TYPE, 27, 27, NAME);
	}
	public static BottomInventory createFromPlayer(Player player){
		if(player == null)
			return null;
		ItemStack[] storage = player.getInventory().getStorageContents();
		BottomInventory inv = new BottomInventory();
		for(int i = 9; i < 36; i++){
			Icon icon = null;
			if(storage[i] != null)
				icon = new Icon(storage[i]);
			inv.setIconElement(i - 9, icon);
		}
		return inv;
	}
	public void setIcon(int slot, Icon item){
		if(slot < 0 || slot >= getSlot())
			return;
		super.setIconElement(slot, item);
	}
	public Icon getIcon(int slot){
		if(slot < 0 || slot >= getSlot())
			return null;
		return super.getIconElement(slot);
	}
	@Override
	public WrapperPlayServerSetSlot[] getSlotPacketsArray(){
		WrapperPlayServerSetSlot[] array = super.getSlotPacketsArray();
		for(WrapperPlayServerSetSlot packet : array)
			packet.setWindowId(-2);
		return array;
	}
	@Override
	public boolean isLegalItemStack(int slot, ItemStack item) {
		if(slot < 0 || slot >= getSlot())
			return false;
		return true;
	}
}
