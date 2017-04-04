<<<<<<< HEAD
package me.nbcss.quickGui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class EventListener implements Listener {
	@EventHandler
	public void onPlayerClick(InventoryClickEvent e){
		//e.setCancelled(true);
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		Operator.addOpenedInventory(e.getPlayer());
	}
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		Operator.removeOpenedInventory(e.getPlayer());
	}
}
=======
package me.nbcss.quickGui;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class EventListener implements Listener {
	@EventHandler
	public void onPlayerClick(InventoryClickEvent e){
		//e.setCancelled(true);
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		Operator.addOpenedInventory(e.getPlayer());
	}
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		Operator.removeOpenedInventory(e.getPlayer());
	}
}
>>>>>>> origin/master
