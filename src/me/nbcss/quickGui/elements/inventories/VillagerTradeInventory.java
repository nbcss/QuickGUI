package me.nbcss.quickGui.elements.inventories;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.nbcss.quickGui.Operator;
import me.nbcss.quickGui.elements.Icon;
import me.nbcss.quickGui.elements.TradePage;
import me.nbcss.quickGui.events.InventoryChangeEvent;
import me.nbcss.quickGui.events.InventoryOpenEvent;
import me.nbcss.quickGui.events.TradePageChangedEvent;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerCustomPayload;

public class VillagerTradeInventory extends AbstractInventory {
	private static final String TYPE = "minecraft:villager";
	private static final String NAME = "Trade";
	private ArrayList<TradePage> list;
	public VillagerTradeInventory(){
		super(TYPE, 3, 3, NAME);
		list = new ArrayList<TradePage>();
	}
	public Icon getFirstTradeIcon(){
		return getIconElement(0);
	}
	public void setFirstTradeIcon(Icon icon){
		setIconElement(0, icon);
	}
	public Icon getSecondTradeIcon(){
		return getIconElement(1);
	}
	public void setSecondTradeIcon(Icon icon){
		setIconElement(1, icon);
	}
	public Icon getTradeResultIcon(){
		return getIconElement(2);
	}
	public void setTradeResultIcon(Icon icon){
		setIconElement(2, icon);
	}
	public int getTradeSize(){
		return list.size();
	}
	public void addTradePage(TradePage page){
		if(page == null)
			return;
		list.add(page);
	}
	public void removeTradePage(int index){
		if(index < 0 || index >= list.size())
			return;
		list.remove(index);
	}
	public TradePage getTradePage(int index){
		if(index < 0 || index >= list.size())
			return null;
		return list.get(index);
	}
	public void onChangePage(TradePageChangedEvent event){
		
	}
	@Override
	public void onOpen(InventoryOpenEvent event){
		super.onOpen(event);
		Player receiver = event.getPlayer();
		updateTrade(receiver);
	}
	@Override
	public void onChange(InventoryChangeEvent event){
		super.onChange(event);
		if(event.isReplaced())
			return;
		for(Player receiver : event.getChangedInventoryView().getWatchers())
			updateTrade(receiver);
	}
	@Override
	public void update(Player receiver){
		super.update(receiver);
		updateTrade(receiver);
	}
	public void updateTrade(Player receiver){
		WrapperPlayServerCustomPayload packet = new WrapperPlayServerCustomPayload();
		packet.setChannel("MC|TrList");
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeInt(Operator.getWindowID());
		out.writeByte(list.size());
		for(TradePage trade : list)
			out.write(trade.toByteArray());
		packet.setContents(out.toByteArray());
		sendPacket(receiver, packet);
	}
}
