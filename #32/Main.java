package me.kamilkime.youtube;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;

public class Main extends JavaPlugin implements Listener{
	
	private static Main instance;
	
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	public void onDisable() {}

	public static Main getInst() {
		return instance;
	}
	
	@EventHandler
	public void onJoin(AsyncPlayerReceiveNameTagEvent e){
		if(e.getNamedPlayer().getName().equalsIgnoreCase("kamilkime")){
			e.setTag("§c§lKamilkime");
		}
		if(e.getPlayer().getName().equalsIgnoreCase("kamilkime")){
			e.setTag("§a§l_Miki_");
		}
	}
}