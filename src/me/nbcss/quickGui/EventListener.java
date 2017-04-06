package me.nbcss.quickGui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class EventListener implements Listener {
	@EventHandler
	public void onPlayerClick(InventoryClickEvent e){
		Player player = (Player) e.getWhoClicked();
		if(e.getCurrentItem().isSimilar(null)){
			player.sendMessage("PUPU");
		}
		//e.setCancelled(true);
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
