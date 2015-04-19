package me.kamilkime.youtube;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmial.zahusek.api.tabapi.TabAPI;

public class Main extends JavaPlugin implements Listener{
	
	private static Main instance;
	
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		new TabAPI(this).register();
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	public void onDisable() {
		new TabAPI(this).unregister();
	}

	public static Main getInst() {
		return instance;
	}
	
	@EventHandler
	public void onJoin(final PlayerJoinEvent e){
		new BukkitRunnable(){
			public void run(){
				/*
				 * Zwykle TabAPI:
				TabAPI.setPriority(Main.getInst(), e.getPlayer(), 2);
				TabAPI.setTabString(Main.getInst(), e.getPlayer(), 9, 2, "§a§lWiadomosc", 1);
				TabAPI.setTabString(Main.getInst(), e.getPlayer(), 5, 0, "§b§lWiadomosc", -1);
				TabAPI.setTabString(Main.getInst(), e.getPlayer(), 12, 1, "§c§lWiadomosc", 0);
				TabAPI.setTabString(Main.getInst(), e.getPlayer(), 2, 1, "§6§lWiadomoscPowyzej16", 3);
				TabAPI.updatePlayer(e.getPlayer());
				 */
				TabAPI.clear(e.getPlayer());
				TabAPI.update(e.getPlayer(), "pierwsza", "§bdruga", "§6§lTrzeciaBardzoDlugaLinjka");
			}
		}.runTaskLater(Main.getInst(), 3);
	}
}