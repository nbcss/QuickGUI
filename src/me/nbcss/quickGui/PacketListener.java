package me.nbcss.quickGui;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.ListeningWhitelist;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import me.nbcss.quickGui.elements.InventoryView;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayClientCloseWindow;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayClientWindowClick;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerCloseWindow;
//import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerSetSlot;

public class PacketListener extends PacketAdapter {
	private static final PacketType[] TYPES = {PacketType.Play.Server.CLOSE_WINDOW, PacketType.Play.Client.CLOSE_WINDOW, 
			PacketType.Play.Client.WINDOW_CLICK, PacketType.Play.Server.SET_SLOT};
	public PacketListener(Plugin plugin) {
		super(plugin, ListenerPriority.NORMAL, TYPES);
	}

	@Override
	public Plugin getPlugin() {
		return super.getPlugin();
	}

	@Override
	public ListeningWhitelist getReceivingWhitelist() {
		return super.getReceivingWhitelist();
	}

	@Override
	public ListeningWhitelist getSendingWhitelist() {
		return super.getSendingWhitelist();
	}

	@Override
	public void onPacketReceiving(PacketEvent event) {
		if(event.getPacketType() == PacketType.Play.Client.WINDOW_CLICK){
			WrapperPlayClientWindowClick packet = new WrapperPlayClientWindowClick(event.getPacket());
			if(packet.getWindowId() != MainClass.getID())
				return;
			event.setCancelled(true);
			Player player = event.getPlayer();
			InventoryView view = Operator.getOpenedInventoryView(player);
			view.onClickInventoryView(packet, player);
			
		}else if(event.getPacketType() == PacketType.Play.Client.CLOSE_WINDOW){
			WrapperPlayClientCloseWindow packet = new WrapperPlayClientCloseWindow(event.getPacket());
			if(packet.getWindowId() != MainClass.getID())
				return;
			event.setCancelled(true);
			Player player = event.getPlayer();
			InventoryView view = Operator.getOpenedInventoryView(player);
			view.onCloseInventoryView(player);
			player.getInventory().setStorageContents(player.getInventory().getStorageContents());
			Operator.resetOpenedInventoryView(player);
		}
	}

	@Override
	public void onPacketSending(PacketEvent event) {
		if(event.getPacketType() == PacketType.Play.Server.CLOSE_WINDOW){
			WrapperPlayServerCloseWindow packet = new WrapperPlayServerCloseWindow(event.getPacket());
			if(packet.getWindowId() != MainClass.getID())
				return;
			Player player = event.getPlayer();
			InventoryView view = Operator.getOpenedInventoryView(player);
			view.onCloseInventoryView(player);
			player.getInventory().setStorageContents(player.getInventory().getStorageContents());
			Operator.resetOpenedInventoryView(player);
		}else if(event.getPacketType() == PacketType.Play.Server.SET_SLOT){
			/*
			WrapperPlayServerSetSlot packet = new WrapperPlayServerSetSlot(event.getPacket());
			String line = "";
			line += " Slot: " + packet.getSlot();
			line += " Item: " + packet.getSlotData().getType().name();
			line += " Id: "   + packet.getWindowId();
			System.out.println(line);
			*/
		}
	}

}
