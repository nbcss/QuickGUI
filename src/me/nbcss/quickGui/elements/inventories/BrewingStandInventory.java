package me.nbcss.quickGui.elements.inventories;

import org.bukkit.entity.Player;

import me.nbcss.quickGui.Operator;
import me.nbcss.quickGui.elements.Icon;
import me.nbcss.quickGui.events.InventoryChangeEvent;
import me.nbcss.quickGui.events.InventoryOpenEvent;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerWindowData;

public class BrewingStandInventory extends AbstractInventory {
	private static final String TYPE = "minecraft:brewing_stand";
	private static final String NAME = "Brewing Stand";
	private int brewTime;
	private int powerBar;
	public BrewingStandInventory() {
		super(TYPE, 5, 5, NAME);
		brewTime = 0;
		powerBar = 0;
	}
	public Icon getLeftPotionIcon(){
		return getIconElement(0);
	}
	public void setLeftPotionIcon(Icon icon){
		setIconElement(0, icon);
	}
	public Icon getMiddlePotionIcon(){
		return getIconElement(1);
	}
	public void setMiddlePotionIcon(Icon icon){
		setIconElement(1, icon);
	}
	public Icon getRightPotionIcon(){
		return getIconElement(2);
	}
	public void setRightPotionIcon(Icon icon){
		setIconElement(2, icon);
	}
	public Icon getPotionIngredientIcon(){
		return getIconElement(3);
	}
	public void setPotionIngredientIcon(Icon icon){
		setIconElement(3, icon);
	}
	public Icon getBlazePowderIcon(){
		return getIconElement(4);
	}
	public void setBlazePowderIcon(Icon icon){
		setIconElement(4, icon);
	}
	public int getBrewTime() {
		return brewTime;
	}
	public void setBrewTime(int brewTime) {
		this.brewTime = brewTime;
	}
	public void setBrewTime(float percent) {
		this.brewTime = 400 - (int) (4 * percent);
	}
	public int getPowerBar() {
		return powerBar;
	}
	public void setPowerBar(int powerBar) {
		this.powerBar = powerBar;
	}
	public void setPowerBar(float percent) {
		this.powerBar = (int) (percent / 5.0f);
	}
	@Override
	public void onOpen(InventoryOpenEvent event){
		super.onOpen(event);
		Player receiver = event.getPlayer();
		updateBrewInfo(receiver);
	}
	@Override
	public void onChange(InventoryChangeEvent event){
		super.onChange(event);
		if(event.isReplaced())
			return;
		for(Player receiver : event.getChangedInventoryView().getWatchers())
			updateBrewInfo(receiver);
	}
	@Override
	public void update(Player receiver){
		super.update(receiver);
		updateBrewInfo(receiver);
	}
	public void updateBrewInfo(Player receiver){
		WrapperPlayServerWindowData packet = new WrapperPlayServerWindowData();
		packet.setWindowId(Operator.getWindowID());
		packet.setProperty(0);
		packet.setValue(brewTime);
		sendPacket(receiver, packet);
		
		WrapperPlayServerWindowData power = new WrapperPlayServerWindowData();
		power.setWindowId(Operator.getWindowID());
		power.setProperty(1);
		power.setValue(powerBar);
		sendPacket(receiver, power);
	}
}
