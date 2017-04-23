package me.nbcss.quickGui.elements.inventories;

import org.bukkit.entity.Player;

import me.nbcss.quickGui.Operator;
import me.nbcss.quickGui.elements.Icon;
import me.nbcss.quickGui.events.InventoryChangeEvent;
import me.nbcss.quickGui.events.InventoryOpenEvent;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerWindowData;

public class FurnaceInventory extends AbstractInventory {
	private static final String TYPE = "minecraft:furnace";
	private static final String NAME = "Furnace";
	private int maxBurnTime;
	private int leftBurnTime;
	private int maxProcessTime;
	private int processTime;
	public FurnaceInventory() {
		super(TYPE, 3, 3, NAME);
		maxBurnTime = 0;
		leftBurnTime = 0;
		maxProcessTime = 200;
		processTime = 0;
	}
	public void setIngredientIcon(Icon icon){
		setIconElement(0, icon);
	}
	public void setFuelIcon(Icon icon){
		setIconElement(1, icon);
	}
	public void setOutputIcon(Icon icon){
		setIconElement(2, icon);
	}
	public Icon getIngredientIcon(){
		return getIconElement(0);
	}
	public Icon getFuelInputIcon(){
		return getIconElement(1);
	}
	public Icon getOutputIcon(){
		return getIconElement(2);
	}
	public int getMaxProcessTime() {
		return maxProcessTime;
	}
	public void setMaxProcessTime(int maxProcess) {
		this.maxProcessTime = maxProcess;
	}
	public int getProcessTime() {
		return processTime;
	}
	public void setProcessTime(int processTime) {
		this.processTime = processTime;
	}
	public void setProcessTime(float percent) {
		this.processTime = (int) (percent * maxProcessTime / 100);
	}
	public int getMaxBurnTime() {
		return maxBurnTime;
	}
	public void setMaxBurnTime(int maxBurnTime) {
		this.maxBurnTime = maxBurnTime;
	}
	public int getLeftBurnTime() {
		return leftBurnTime;
	}
	public void setLeftBurnTime(int burnTime) {
		this.leftBurnTime = burnTime;
	}
	public void setLeftBurnTime(float percent) {
		this.leftBurnTime = (int) (percent / 100 * maxBurnTime);
	}
	public void resetFuel(){
		maxBurnTime = 0;
		leftBurnTime = 0;
		maxProcessTime = 200;
		processTime = 0;
	}
	@Override
	public void onOpen(InventoryOpenEvent event){
		super.onOpen(event);
		Player receiver = event.getPlayer();
		updateFuel(receiver);
	}
	@Override
	public void onChange(InventoryChangeEvent event){
		super.onChange(event);
		if(event.isReplaced())
			return;
		for(Player receiver : event.getChangedInventoryView().getWatchers())
			updateFuel(receiver);
	}
	@Override
	public void update(Player receiver){
		super.update(receiver);
		updateFuel(receiver);
	}
	public void updateFuel(Player receiver){
		for(int i = 0; i < 4; i++){
			WrapperPlayServerWindowData packet = new WrapperPlayServerWindowData();
			packet.setWindowId(Operator.getWindowID());
			packet.setProperty(i);
			if(i == 0)
				packet.setValue(leftBurnTime);
			else if(i == 1)
				packet.setValue(maxBurnTime);
			else if(i == 2)
				packet.setValue(processTime);
			else if(i == 3)
				packet.setValue(maxProcessTime);
			else
				continue;
			sendPacket(receiver, packet);
		}
	}
}
