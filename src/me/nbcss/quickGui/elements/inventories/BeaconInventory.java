package me.nbcss.quickGui.elements.inventories;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import me.nbcss.quickGui.Operator;
import me.nbcss.quickGui.elements.Icon;
import me.nbcss.quickGui.events.InventoryChangeEvent;
import me.nbcss.quickGui.events.InventoryOpenEvent;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerWindowData;

public class BeaconInventory extends AbstractInventory {
	private static final String TYPE = "minecraft:beacon";
	private static final String NAME = "Beacon";
	private int level;
	private PotionEffectType firstEffect;
	private PotionEffectType secondEffect;
	public BeaconInventory() {
		super(TYPE, 1, 1, NAME);
		level = 0;
		firstEffect = null;
		secondEffect = null;
	}
	public void setBeaconSlotIcon(Icon icon){
		setIconElement(0, icon);
	}
	public Icon getBeaconSlotIcon(){
		return getIconElement(0);
	}
	public PotionEffectType getFirstEffect() {
		return firstEffect;
	}
	public void setFirstEffect(PotionEffectType firstEffect) {
		this.firstEffect = firstEffect;
	}
	public PotionEffectType getSecondEffect() {
		return secondEffect;
	}
	public void setSecondEffect(PotionEffectType secondEffect) {
		this.secondEffect = secondEffect;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	@Override
	public void onOpen(InventoryOpenEvent event){
		super.onOpen(event);
		Player receiver = event.getPlayer();
		updateBeaconInfo(receiver);
	}
	@Override
	public void onChange(InventoryChangeEvent event){
		super.onChange(event);
		if(event.isReplaced())
			return;
		for(Player receiver : event.getChangedInventoryView().getWatchers())
			updateBeaconInfo(receiver);
	}
	@Override
	public void update(Player receiver){
		super.update(receiver);
		updateBeaconInfo(receiver);
	}
	@SuppressWarnings("deprecation")
	public void updateBeaconInfo(Player receiver){
		WrapperPlayServerWindowData packet = new WrapperPlayServerWindowData();
		packet.setWindowId(Operator.getWindowID());
		packet.setProperty(0);
		packet.setValue(level);
		sendPacket(receiver, packet);
		
		if(firstEffect != null){
			WrapperPlayServerWindowData first = new WrapperPlayServerWindowData();
			first.setWindowId(Operator.getWindowID());
			first.setProperty(1);
			first.setValue(firstEffect.getId());
			sendPacket(receiver, first);
		}
		
		if(secondEffect != null){
			WrapperPlayServerWindowData second = new WrapperPlayServerWindowData();
			second.setWindowId(Operator.getWindowID());
			second.setProperty(2);
			second.setValue(secondEffect.getId());
			sendPacket(receiver, second);
		}
	}
}
