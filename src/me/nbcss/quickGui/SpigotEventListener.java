package me.nbcss.quickGui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.nbcss.quickGui.elements.InventoryView;

public final class SpigotEventListener implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		Operator.resetOpenedInventoryView(e.getPlayer());
	}
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		InventoryView view = Operator.getOpenedInventoryView(e.getPlayer());
		if(view != null)
			view.closeInventoryView(e.getPlayer());
		Operator.removeOpenedInventoryView(e.getPlayer());
	}
	@EventHandler
	public void onPlayerPickup(PlayerPickupItemEvent e){
		if(e.isCancelled())
			return;
		Player player = e.getPlayer();
		if(Operator.getOpenedInventoryView(player) != null)
			e.setCancelled(true);
	}
}
