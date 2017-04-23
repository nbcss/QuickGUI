package me.nbcss.quickGui.elements.inventories;

import org.bukkit.entity.Player;

import me.nbcss.quickGui.Operator;
import me.nbcss.quickGui.elements.Icon;
import me.nbcss.quickGui.events.AnvilTypeTextEvent;
import me.nbcss.quickGui.events.InventoryChangeEvent;
import me.nbcss.quickGui.events.InventoryOpenEvent;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerWindowData;

public class AnvilInventory extends AbstractInventory {
	private static final String TYPE = "minecraft:anvil";
	private static final String NAME = "Anvil";
	private int cost;
	public AnvilInventory() {
		super(TYPE, 3, 0, NAME);
		cost = 0;
	}
	public void setLeftInputIcon(Icon icon){
		setIconElement(0, icon);
	}
	public void setRightInputIcon(Icon icon){
		setIconElement(1, icon);
	}
	public void setOutputIcon(Icon icon){
		setIconElement(2, icon);
	}
	public Icon getLeftInputIcon(){
		return getIconElement(0);
	}
	public Icon getRightInputIcon(){
		return getIconElement(1);
	}
	public Icon getOutputIcon(){
		return getIconElement(2);
	}
	public int getLevelCost() {
		return cost;
	}
	public void setLevelCost(int cost) {
		this.cost = cost;
	}
	public void onTypeText(AnvilTypeTextEvent event){
		
	}
	@Override
	public void onOpen(InventoryOpenEvent event){
		super.onOpen(event);
		Player receiver = event.getPlayer();
		updateCost(receiver);
	}
	@Override
	public void onChange(InventoryChangeEvent event){
		super.onChange(event);
		if(event.isReplaced())
			return;
		for(Player receiver : event.getChangedInventoryView().getWatchers())
			updateCost(receiver);
	}
	@Override
	public void update(Player receiver){
		super.update(receiver);
		updateCost(receiver);
	}
	public void updateCost(Player receiver){
		WrapperPlayServerWindowData packet = new WrapperPlayServerWindowData();
		packet.setWindowId(Operator.getWindowID());
		packet.setProperty(0);
		packet.setValue(cost);
		sendPacket(receiver, packet);
	}
}
