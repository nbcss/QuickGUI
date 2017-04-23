package me.nbcss.quickGui;

import java.io.UnsupportedEncodingException;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.ListeningWhitelist;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import me.nbcss.quickGui.elements.InventoryView;
import me.nbcss.quickGui.elements.inventories.AnvilInventory;
import me.nbcss.quickGui.elements.inventories.EnchantmentTableInventory;
import me.nbcss.quickGui.elements.inventories.VillagerTradeInventory;
import me.nbcss.quickGui.events.AnvilTypeTextEvent;
import me.nbcss.quickGui.events.EnchantmentSelectEvent;
import me.nbcss.quickGui.events.TradePageChangedEvent;
import me.nbcss.quickGui.events.EnchantmentSelectEvent.EnchantmentSlot;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayClientCloseWindow;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayClientCustomPayload;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayClientEnchantItem;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayClientWindowClick;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerCloseWindow;
//import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerCustomPayload;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerOpenWindow;
//import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerWindowData;

public class PacketListener extends PacketAdapter {
	private static final PacketType[] TYPES = {PacketType.Play.Server.CLOSE_WINDOW, PacketType.Play.Client.CLOSE_WINDOW, 
			PacketType.Play.Server.OPEN_WINDOW, PacketType.Play.Client.WINDOW_CLICK, PacketType.Play.Client.CUSTOM_PAYLOAD, 
			PacketType.Play.Client.ENCHANT_ITEM};
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
		return ListeningWhitelist.newBuilder(super.getSendingWhitelist()).types(PacketType.Play.Server.SET_SLOT, PacketType.Play.Server.WINDOW_DATA, 
				PacketType.Play.Server.CUSTOM_PAYLOAD, PacketType.Play.Server.OPEN_WINDOW, PacketType.Play.Server.CLOSE_WINDOW).build();
	}

	@Override
	public void onPacketReceiving(PacketEvent event) {
		if(event.getPacketType() == PacketType.Play.Client.WINDOW_CLICK){
			WrapperPlayClientWindowClick packet = new WrapperPlayClientWindowClick(event.getPacket());
			if(packet.getWindowId() != Operator.getWindowID())
				return;
			event.setCancelled(true);
			Player player = event.getPlayer();
			InventoryView view = Operator.getOpenedInventoryView(player);
			view.onClickInventoryView(packet, player);
			
		}else if(event.getPacketType() == PacketType.Play.Client.CLOSE_WINDOW){
			WrapperPlayClientCloseWindow packet = new WrapperPlayClientCloseWindow(event.getPacket());
			if(packet.getWindowId() != Operator.getWindowID())
				return;
			event.setCancelled(true);
			Player player = event.getPlayer();
			InventoryView view = Operator.getOpenedInventoryView(player);
			view.closeInventoryView(player);
			player.getInventory().setStorageContents(player.getInventory().getStorageContents());
			Operator.resetOpenedInventoryView(player);
		}else if(event.getPacketType() == PacketType.Play.Client.CUSTOM_PAYLOAD){
			WrapperPlayClientCustomPayload packet = new WrapperPlayClientCustomPayload(event.getPacket());
			InventoryView view = Operator.getOpenedInventoryView(event.getPlayer());
			if(view == null)
				return;
			if(packet.getChannel().equals("MC|TrSel") && (view.getTopInventory() instanceof VillagerTradeInventory)){
				event.setCancelled(true);
				VillagerTradeInventory inv = (VillagerTradeInventory) view.getTopInventory();
				byte[] array = packet.getContents();
				ByteArrayDataInput in = ByteStreams.newDataInput(array);
				int page = in.readInt();
				inv.onChangePage(new TradePageChangedEvent(view, event.getPlayer(), page));
			}else if(packet.getChannel().equals("MC|ItemName") && (view.getTopInventory() instanceof AnvilInventory)){
				event.setCancelled(true);
				AnvilInventory inv = (AnvilInventory) view.getTopInventory();
				byte[] array = packet.getContents();
				String name = null;
				try {
					name = new String(array, "UTF-8").substring(1);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				inv.onTypeText(new AnvilTypeTextEvent(event.getPlayer(), name, view));
			}
		}else if(event.getPacketType() == PacketType.Play.Client.ENCHANT_ITEM){
			WrapperPlayClientEnchantItem packet = new WrapperPlayClientEnchantItem(event.getPacket());
			if(packet.getWindowId() != Operator.getWindowID())
				return;
			InventoryView view = Operator.getOpenedInventoryView(event.getPlayer());
			if(view == null)
				return;
			if(!(view.getTopInventory() instanceof EnchantmentTableInventory))
				return;
			event.setCancelled(true);
			EnchantmentTableInventory inv = (EnchantmentTableInventory) view.getTopInventory();
			EnchantmentSlot slot = EnchantmentSlot.get(packet.getEnchantment());
			inv.onSelectEnchant(new EnchantmentSelectEvent(slot, event.getPlayer(), view));
		}
	}

	@Override
	public void onPacketSending(PacketEvent event) {
		if(event.getPacketType() == PacketType.Play.Server.CLOSE_WINDOW){
			WrapperPlayServerCloseWindow packet = new WrapperPlayServerCloseWindow(event.getPacket());
			if(packet.getWindowId() != Operator.getWindowID())
				return;
			Player player = event.getPlayer();
			InventoryView view = Operator.getOpenedInventoryView(player);
			if(view == null)
				return;
			view.closeInventoryView(player);
			player.getInventory().setStorageContents(player.getInventory().getStorageContents());
			Operator.resetOpenedInventoryView(player);
		}else if(event.getPacket().getType() == PacketType.Play.Server.OPEN_WINDOW){
			WrapperPlayServerOpenWindow packet = new WrapperPlayServerOpenWindow(event.getPacket());
			Player player = event.getPlayer();
			InventoryView view = Operator.getOpenedInventoryView(player);
			if(view == null)
				return;
			if(packet.getWindowID() != Operator.getWindowID())
				return;
			view.closeInventoryView(player);
			player.getInventory().setStorageContents(player.getInventory().getStorageContents());
			Operator.resetOpenedInventoryView(player);
		}/*else if(event.getPacket().getType() == PacketType.Play.Server.WINDOW_DATA){
			WrapperPlayServerWindowData packet = new WrapperPlayServerWindowData(event.getPacket());
			int id = packet.getWindowId();
			int property = packet.getProperty();
			int value = packet.getValue();
			event.getPlayer().sendMessage("id: " + id + " property: " + property + " value: " + value);
		}
		*/
	}
}
