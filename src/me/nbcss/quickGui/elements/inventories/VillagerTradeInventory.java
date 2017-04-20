package me.nbcss.quickGui.elements.inventories;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.nbcss.quickGui.Operator;
import me.nbcss.quickGui.elements.TradePage;
import me.nbcss.quickGui.events.InventoryChangeEvent;
import me.nbcss.quickGui.events.InventoryOpenEvent;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerCustomPayload;

public class VillagerTradeInventory extends AbstractInventory {
	private static final String TYPE = "minecraft:villager";
	private static final String NAME = "Trade";
	private ArrayList<TradePage> list;
	public VillagerTradeInventory(){
		super(TYPE, 3, 3, NAME);
		list = new ArrayList<TradePage>();
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
	@Override
	public void onOpen(InventoryOpenEvent event){
		Player receiver = event.getPlayer();
		updateTrade(receiver);
	}
	@Override
	public void onChange(InventoryChangeEvent event){
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
		packet.sendPacket(receiver);
	}
}
