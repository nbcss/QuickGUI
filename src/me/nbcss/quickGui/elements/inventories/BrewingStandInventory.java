package me.nbcss.quickGui.elements.inventories;

import org.bukkit.entity.Player;

import me.nbcss.quickGui.Operator;
import me.nbcss.quickGui.events.InventoryChangeEvent;
import me.nbcss.quickGui.events.InventoryOpenEvent;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerWindowData;

public class BrewingStandInventory extends AbstractInventory {
	private static final String TYPE = "minecraft:brewing_stand";
	private static final String NAME = "Brewing Stand";
	private int brewTime;
	public BrewingStandInventory() {
		super(TYPE, 5, 5, NAME);
		brewTime = 0;
	}
	public int getBrewTime() {
		return brewTime;
	}
	public void setBrewTime(int brewTime) {
		this.brewTime = brewTime;
	}
	public void setBrewTime(float percent) {
		this.brewTime = (int) (4 * percent);
	}
	@Override
	public void onOpen(InventoryOpenEvent event){
		super.onOpen(event);
		Player receiver = event.getPlayer();
		updateBrewTime(receiver);
	}
	@Override
	public void onChange(InventoryChangeEvent event){
		super.onChange(event);
		if(event.isReplaced())
			return;
		for(Player receiver : event.getChangedInventoryView().getWatchers())
			updateBrewTime(receiver);
	}
	@Override
	public void update(Player receiver){
		super.update(receiver);
		updateBrewTime(receiver);
	}
	public void updateBrewTime(Player receiver){
		WrapperPlayServerWindowData packet = new WrapperPlayServerWindowData();
		packet.setWindowId(Operator.getWindowID());
		packet.setProperty(0);
		packet.setValue(brewTime);
		sendPacket(receiver, packet);
	}
}
