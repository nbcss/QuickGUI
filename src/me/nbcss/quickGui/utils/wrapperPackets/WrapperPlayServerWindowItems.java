package me.nbcss.quickGui.utils.wrapperPackets;

import org.bukkit.inventory.ItemStack;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperPlayServerWindowItems extends AbstractPacket {
	public static final PacketType TYPE = PacketType.Play.Server.WINDOW_ITEMS;

	public WrapperPlayServerWindowItems() {
		super(new PacketContainer(TYPE), TYPE);
		handle.getModifier().writeDefaults();
	}

	public WrapperPlayServerWindowItems(PacketContainer packet) {
		super(packet, TYPE);
	}

	/**
	 * Retrieve Window ID.
	 * <p>
	 * Notes: the id of window which items are being sent for. 0 for player
	 * inventory.
	 * 
	 * @return The current Window ID
	 */
	public int getWindowId() {
		return handle.getIntegers().read(0);
	}

	/**
	 * Set Window ID.
	 * 
	 * @param value - new value.
	 */
	public void setWindowId(int value) {
		handle.getIntegers().write(0, value);
	}

	/**
	 * Retrieve Slot data.
	 * 
	 * @return The current Slot data
	 */
	public ItemStack[] getSlotData() {
		return handle.getItemArrayModifier().read(0);
	}

	/**
	 * Set Slot data.
	 * 
	 * @param value - new value.
	 */
	public void setSlotData(ItemStack[] value) {
		handle.getItemArrayModifier().write(0, value);
	}

}
