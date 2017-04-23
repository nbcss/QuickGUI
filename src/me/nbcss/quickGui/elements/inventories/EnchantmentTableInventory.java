package me.nbcss.quickGui.elements.inventories;

import java.util.Random;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import me.nbcss.quickGui.Operator;
import me.nbcss.quickGui.elements.Icon;
import me.nbcss.quickGui.events.EnchantmentSelectEvent;
import me.nbcss.quickGui.events.InventoryChangeEvent;
import me.nbcss.quickGui.events.InventoryOpenEvent;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerWindowData;

public class EnchantmentTableInventory extends AbstractInventory {
	private static final String TYPE = "minecraft:enchanting_table";
	private static final String NAME = "Enchant";
	private int topEnchantRequireLevel;
	private int middleEnchantRequireLevel;
	private int bottomEnchantRequireLevel;
	private int topEnchantmentType;
	private int middleEnchantmentType;
	private int bottomEnchantmentType;
	private int topEnchantmentLevel;
	private int middleEnchantmentLevel;
	private int bottomEnchantmentLevel;
	private int seed;
	public EnchantmentTableInventory() {
		super(TYPE, 2, 0, NAME);
		topEnchantRequireLevel = 0;
		middleEnchantRequireLevel = 0;
		bottomEnchantRequireLevel = 0;
		topEnchantmentType = -1;
		middleEnchantmentType = -1;
		bottomEnchantmentType = -1;
		topEnchantmentLevel = -1;
		middleEnchantmentLevel = -1;
		bottomEnchantmentLevel = -1;
		seed = new Random(System.currentTimeMillis()).nextInt();
	}
	public void setEnchantIcon(Icon icon){
		setIconElement(0, icon);
	}
	public Icon getEnchantIcon(){
		return getIconElement(0);
	}
	public void setLapisSlotIcon(Icon icon){
		setIconElement(1, icon);
	}
	public Icon getLapisSlotIcon(){
		return getIconElement(1);
	}
	public int getTopEnchantRequireLevel() {
		return topEnchantRequireLevel;
	}
	public void setTopEnchantRequireLevel(int topEnchantRequireLevel) {
		this.topEnchantRequireLevel = topEnchantRequireLevel;
	}
	public int getMiddleEnchantRequireLevel() {
		return middleEnchantRequireLevel;
	}
	public void setMiddleEnchantRequireLevel(int middleEnchantRequireLevel) {
		this.middleEnchantRequireLevel = middleEnchantRequireLevel;
	}
	public int getBottomEnchantRequireLevel() {
		return bottomEnchantRequireLevel;
	}
	public void setBottomEnchantRequireLevel(int bottomEnchantRequireLevel) {
		this.bottomEnchantRequireLevel = bottomEnchantRequireLevel;
	}
	public int getTopEnchantmentLevel() {
		return topEnchantmentLevel;
	}
	public void setTopEnchantmentLevel(int topEnchantmentLevel) {
		this.topEnchantmentLevel = topEnchantmentLevel;
	}
	public int getMiddleEnchantmentLevel() {
		return middleEnchantmentLevel;
	}
	public void setMiddleEnchantmentLevel(int middleEnchantmentLevel) {
		this.middleEnchantmentLevel = middleEnchantmentLevel;
	}
	public int getBottomEnchantmentLevel() {
		return bottomEnchantmentLevel;
	}
	public void setBottomEnchantmentLevel(int bottomEnchantmentLevel) {
		this.bottomEnchantmentLevel = bottomEnchantmentLevel;
	}
	public int getTopEnchantmentType() {
		return topEnchantmentType;
	}
	public void setTopEnchantmentType(int topEnchantment) {
		this.topEnchantmentType = topEnchantment;
	}
	public int getMiddleEnchantmentType() {
		return middleEnchantmentType;
	}
	public void setMiddleEnchantmentType(int middleEnchantment) {
		this.middleEnchantmentType = middleEnchantment;
	}
	public int getBottomEnchantmentType() {
		return bottomEnchantmentType;
	}
	public void setBottomEnchantmentType(int bottomEnchantment) {
		this.bottomEnchantmentType = bottomEnchantment;
	}
	public int getSeed() {
		return seed;
	}
	public void setSeed(int seed) {
		this.seed = seed;
	}
	public void onSelectEnchant(EnchantmentSelectEvent event){
		
	}
	@SuppressWarnings("deprecation")
	public void setTopEnchantment(Enchantment type, int level){
		topEnchantmentType = type.getId();
		topEnchantmentLevel = level;
	}
	@SuppressWarnings("deprecation")
	public void setMiddleEnchantment(Enchantment type, int level){
		middleEnchantmentType = type.getId();
		middleEnchantmentLevel = level;
	}
	@SuppressWarnings("deprecation")
	public void setBottomEnchantment(Enchantment type, int level){
		bottomEnchantmentType = type.getId();
		bottomEnchantmentLevel = level;
	}
	@Override
	public void onOpen(InventoryOpenEvent event){
		super.onOpen(event);
		Player receiver = event.getPlayer();
		updateEnchantmentInfo(receiver);
	}
	@Override
	public void onChange(InventoryChangeEvent event){
		super.onChange(event);
		if(event.isReplaced())
			return;
		for(Player receiver : event.getChangedInventoryView().getWatchers())
			updateEnchantmentInfo(receiver);
	}
	@Override
	public void update(Player receiver){
		super.update(receiver);
		updateEnchantmentInfo(receiver);
	}
	public void updateEnchantmentInfo(Player receiver){
		WrapperPlayServerWindowData topRequire = new WrapperPlayServerWindowData();
		topRequire.setWindowId(Operator.getWindowID());
		topRequire.setProperty(0);
		topRequire.setValue(topEnchantRequireLevel);
		sendPacket(receiver, topRequire);
		
		WrapperPlayServerWindowData middleRequire = new WrapperPlayServerWindowData();
		middleRequire.setWindowId(Operator.getWindowID());
		middleRequire.setProperty(1);
		middleRequire.setValue(middleEnchantRequireLevel);
		sendPacket(receiver, middleRequire);
		
		WrapperPlayServerWindowData bottomRequire = new WrapperPlayServerWindowData();
		bottomRequire.setWindowId(Operator.getWindowID());
		bottomRequire.setProperty(2);
		bottomRequire.setValue(bottomEnchantRequireLevel);
		sendPacket(receiver, bottomRequire);
		
		WrapperPlayServerWindowData seedPacket = new WrapperPlayServerWindowData();
		seedPacket.setWindowId(Operator.getWindowID());
		seedPacket.setProperty(3);
		seedPacket.setValue(seed);
		sendPacket(receiver, seedPacket);
		
		WrapperPlayServerWindowData topType = new WrapperPlayServerWindowData();
		topType.setWindowId(Operator.getWindowID());
		topType.setProperty(4);
		topType.setValue(topEnchantmentType);
		sendPacket(receiver, topType);
		
		WrapperPlayServerWindowData middleType = new WrapperPlayServerWindowData();
		middleType.setWindowId(Operator.getWindowID());
		middleType.setProperty(5);
		middleType.setValue(middleEnchantmentType);
		sendPacket(receiver, middleType);
		
		WrapperPlayServerWindowData bottomType = new WrapperPlayServerWindowData();
		bottomType.setWindowId(Operator.getWindowID());
		bottomType.setProperty(6);
		bottomType.setValue(bottomEnchantmentType);
		sendPacket(receiver, bottomType);
		
		WrapperPlayServerWindowData topLevel = new WrapperPlayServerWindowData();
		topLevel.setWindowId(Operator.getWindowID());
		topLevel.setProperty(7);
		topLevel.setValue(topEnchantmentLevel);
		sendPacket(receiver, topLevel);
		
		WrapperPlayServerWindowData middleLevel = new WrapperPlayServerWindowData();
		middleLevel.setWindowId(Operator.getWindowID());
		middleLevel.setProperty(8);
		middleLevel.setValue(middleEnchantmentLevel);
		sendPacket(receiver, middleLevel);
		
		WrapperPlayServerWindowData bottomLevel = new WrapperPlayServerWindowData();
		bottomLevel.setWindowId(Operator.getWindowID());
		bottomLevel.setProperty(9);
		bottomLevel.setValue(bottomEnchantmentLevel);
		sendPacket(receiver, bottomLevel);
	}
}
