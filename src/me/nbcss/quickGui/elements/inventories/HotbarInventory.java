package me.nbcss.quickGui.elements.inventories;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.nbcss.quickGui.elements.Icon;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerSetSlot;

public class HotbarInventory extends AbstractInventory {
	private static final String TYPE = "minecraft:container";
	private static final String NAME = "Hotbar";
	public HotbarInventory(){
		super(TYPE, 9, 9, NAME);
	}
	public static HotbarInventory createFromPlayer(Player player){
		if(player == null)
			return null;
		ItemStack[] storage = player.getInventory().getStorageContents();
		HotbarInventory inv = new HotbarInventory();
		for(int i = 0; i < 9; i++){
			Icon icon = null;
			if(storage[i] != null)
				icon = new Icon(storage[i]);
			inv.setIconElement(i, icon);
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
	public WrapperPlayServerSetSlot getSlotPacket(int slot){
		WrapperPlayServerSetSlot packet = super.getSlotPacket(slot);
		if(packet != null)
			packet.setWindowId(-2);
		return packet;
	}
}
