package me.nbcss.quickGui.events;

import org.bukkit.entity.Player;

import me.nbcss.quickGui.elements.InventoryView;

public class EnchantmentSelectEvent {
	private final InventoryView view;
	private final Player player;
	private final EnchantmentSlot slot;
	public EnchantmentSelectEvent (EnchantmentSlot slot, Player player, InventoryView view){
		this.slot = slot;
		this.player = player;
		this.view = view;
	}
	public EnchantmentSlot getClickedSlot(){
		return slot;
	}
	public Player getPlayer() {
		return player;
	}
	public InventoryView getClickedInventoryView() {
		return view;
	}
	public enum EnchantmentSlot {
		TOP, MIDDLE, BOTTOM;
		public static EnchantmentSlot get(int index){
			return EnchantmentSlot.values()[index];
		}
	}
}
