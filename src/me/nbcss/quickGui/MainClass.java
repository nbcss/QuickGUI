package me.nbcss.quickGui;

import java.lang.reflect.Field;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;

import me.nbcss.quickGui.elements.GlowEnchant;

public class MainClass extends JavaPlugin {
	private static JavaPlugin plugin;
	@Override
	public void onEnable(){
		plugin = this;
		getServer().getPluginManager().registerEvents(new SpigotEventListener(), this);
		registerGlow();
		for(Player player : getServer().getOnlinePlayers())
			Operator.resetOpenedInventoryView(player);
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketListener(this));
	}
	public static JavaPlugin getHandle(){
		return plugin;
	}
	public void registerGlow() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Enchantment.registerEnchantment(new GlowEnchant());
        }
        catch (IllegalArgumentException e){
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
