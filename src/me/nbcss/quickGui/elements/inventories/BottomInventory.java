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
			if(storage[i] != null)
				inv.setIconElement(i - 9, new Icon(storage[i]));
		}
		return inv;
	}
	@Override
	public WrapperPlayServerSetSlot[] getSlotPacketsArray(){
		WrapperPlayServerSetSlot[] array = super.getSlotPacketsArray();
		for(WrapperPlayServerSetSlot packet : array){
			packet.setWindowId(-2);
			packet.setSlot(packet.getSlot() + 9);
		}
		return array;
	}
	@Override
	public WrapperPlayServerSetSlot getSlotPacket(int slot){
		WrapperPlayServerSetSlot packet = super.getSlotPacket(slot);
		if(packet != null){
			packet.setWindowId(-2);
			packet.setSlot(packet.getSlot() + 9);
		}
		return packet;
	}
	@Override
	public void update(Player receiver){
		WrapperPlayServerSetSlot[] array = getSlotPacketsArray();
		for(WrapperPlayServerSetSlot packet : array)
			sendPacket(receiver, packet);
	}
}
