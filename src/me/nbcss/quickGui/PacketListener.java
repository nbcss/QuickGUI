package me.nbcss.quickGui;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.ListeningWhitelist;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
//import com.google.common.io.ByteArrayDataInput;
//import com.google.common.io.ByteStreams;

import me.nbcss.quickGui.elements.InventoryView;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayClientCloseWindow;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayClientCustomPayload;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayClientWindowClick;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerCloseWindow;
//import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayServerCustomPayload;

public class PacketListener extends PacketAdapter {
	private static final PacketType[] TYPES = {PacketType.Play.Server.CLOSE_WINDOW, PacketType.Play.Client.CLOSE_WINDOW, 
			PacketType.Play.Server.OPEN_WINDOW, PacketType.Play.Client.WINDOW_CLICK, PacketType.Play.Client.CUSTOM_PAYLOAD};
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
		return ListeningWhitelist.newBuilder(super.getSendingWhitelist()).types(PacketType.Play.Server.SET_SLOT, PacketType.Play.Server.WINDOW_DATA).build();
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
			event.getPlayer().sendMessage(packet.getChannel());
			
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
		}else if(event.getPacketType() == PacketType.Play.Server.CUSTOM_PAYLOAD){
			/*
			WrapperPlayServerCustomPayload packet = new WrapperPlayServerCustomPayload(event.getPacket());
			event.getPacket().getItemModifier();
			event.getPlayer().sendMessage(packet.getChannel());
			byte[] array = packet.getContents();
			try{
				event.getPlayer().sendMessage("length: " + array.length);
				ByteArrayDataInput in = ByteStreams.newDataInput(array);
				int id = in.readInt();
				event.getPlayer().sendMessage("id: " + id);
				byte size = in.readByte();
				event.getPlayer().sendMessage("size: " + size);
				for(int i = 1; i <= size; i++){
					event.getPlayer().sendMessage("Trade Page " + i);
					event.getPlayer().sendMessage("Input Item Data");
					event.getPlayer().sendMessage("Item Id: " + in.readShort());
					event.getPlayer().sendMessage("Count: " + in.readByte());
					event.getPlayer().sendMessage("Damage: " + in.readShort());
					event.getPlayer().sendMessage("NBT: " + in.readByte());
					event.getPlayer().sendMessage("Output Item Data");
					event.getPlayer().sendMessage("Item Id: " + in.readShort());
					event.getPlayer().sendMessage("Count: " + in.readByte());
					event.getPlayer().sendMessage("Damage: " + in.readShort());
					event.getPlayer().sendMessage("NBT: " + in.readByte());
					boolean second = in.readBoolean();
					event.getPlayer().sendMessage("Has second require: " + second);
					if(second){
						event.getPlayer().sendMessage("Second Input Item Data");
						event.getPlayer().sendMessage("Item Id: " + in.readShort());
						event.getPlayer().sendMessage("Count: " + in.readByte());
						event.getPlayer().sendMessage("Damage: " + in.readShort());
						event.getPlayer().sendMessage("NBT: " + in.readByte());
					}
					event.getPlayer().sendMessage("Disabled: " + in.readBoolean());
					event.getPlayer().sendMessage("Used Count: " + in.readInt());
					event.getPlayer().sendMessage("Max Count: " + in.readInt());
				}
				//int length = array.length;
				//event.getPlayer().sendMessage("length: " + in.read);
				//byte[] bytes = new byte[length];
				//event.getPlayer().sendMessage("length: " + length);
				//ByteArrayInputStream input = new ByteArrayInputStream(array);
				
				//BukkitObjectInputStream dataInput = new BukkitObjectInputStream(input);
				//ItemStack item = (ItemStack) dataInput.readObject();
				//ObjectInputStream oiStream = new ObjectInputStream(new ByteInputStream(array, array.length));
				//event.getPlayer().sendMessage("UTF: " + in.readUTF());
			}catch(Exception e){
				e.printStackTrace();
			}
			
			packet.setContents(array);
			*/
		}else if(event.getPacket().getType() == PacketType.Play.Server.OPEN_WINDOW){
			
		}
	}
}
