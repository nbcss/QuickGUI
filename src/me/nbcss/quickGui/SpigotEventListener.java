package me.nbcss.quickGui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class SpigotEventListener implements Listener {
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		Operator.resetOpenedInventoryView(e.getPlayer());
	}
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		Operator.removeOpenedInventoryView(e.getPlayer());
	}
}
