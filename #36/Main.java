package me.kamilkime.youtube;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	private static Main instance;
	
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	public void onDisable() {}

	public static Main getInst() {
		return instance;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		e.getPlayer().getInventory().addItem(Youtube.create(Material.DIAMOND, 23));
		e.getPlayer().getInventory().addItem(Youtube.create(Material.GOLDEN_APPLE, 12, (short) 1));
		e.getPlayer().getInventory().addItem(Youtube.create(Material.EMERALD, 35, (short) 0, "§aEmeraldy"));
	}
}