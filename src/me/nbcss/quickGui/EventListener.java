package me.nbcss.quickGui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class EventListener implements Listener {
	@EventHandler
	public void onPlayerClick(InventoryClickEvent e){
		System.out.println(e.getAction().name());
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		Operator.resetOpenedInventoryView(e.getPlayer());
	}
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		Operator.removeOpenedInventoryView(e.getPlayer());
	}
}
