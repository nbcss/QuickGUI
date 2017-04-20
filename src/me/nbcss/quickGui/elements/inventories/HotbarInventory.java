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
	@Override
	public void update(Player receiver){
		WrapperPlayServerSetSlot[] array = super.getSlotPacketsArray();
		for(WrapperPlayServerSetSlot packet : array){
			packet.setWindowId(-2);
			packet.sendPacket(receiver);
		}
	}
}
