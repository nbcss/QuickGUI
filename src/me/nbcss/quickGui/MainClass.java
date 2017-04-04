package me.nbcss.quickGui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import me.nbcss.quickGui.elements.ClickIconAction;
import me.nbcss.quickGui.elements.ClickIconEvent;
import me.nbcss.quickGui.elements.inventories.AbstractInventory;
import me.nbcss.quickGui.utils.Util;
import me.nbcss.quickGui.utils.wrapperPackets.WrapperPlayClientWindowClick;

public class MainClass extends JavaPlugin {
	private static JavaPlugin plugin;
	@Override
	public void onEnable(){
		plugin = this;
		getServer().getPluginManager().registerEvents(new EventListener(), this);
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Server.WINDOW_ITEMS, PacketType.Play.Client.WINDOW_CLICK){
			@Override
			public void onPacketReceiving(PacketEvent event) {
				if(event.getPacketType() != PacketType.Play.Client.WINDOW_CLICK)
					return;
				WrapperPlayClientWindowClick packet = new WrapperPlayClientWindowClick(event.getPacket());
				if(packet.getWindowId() != -1)
					return;
				event.setCancelled(true);
				Player player = event.getPlayer();
				AbstractInventory inv = Operator.getOpenedInventory(player);
				Util.getWindowItemPacket(inv, player).sendPacket(player);
				player.setItemOnCursor(null);
				if(packet.getSlot() >= inv.getSlot() || packet.getSlot() < 0)
					return;
				ItemStack item = packet.getClickedItem();
				ClickIconEvent e = new ClickIconEvent(event.getPlayer(), ClickIconAction.LEFT_CLICK, packet.getSlot(), item);
				inv.onClick(e);
			}

			@Override
			public void onPacketSending(PacketEvent event) {
				
			}
		});
		//Demo.enable();
	}
	
	public static JavaPlugin getHandle(){
		return plugin;
	}
}
