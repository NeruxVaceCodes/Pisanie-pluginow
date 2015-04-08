package me.kamilkime.youtube;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

public class Main extends JavaPlugin{
	
	private static Main instance;
	
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		Bukkit.getPluginManager().registerEvents(new HoloNagrobek(), this);
		delHolograms();
	}
	
	public void onDisable() {}

	public static Main getInst() {
		return instance;
	}
	
	private void delHolograms(){
		new BukkitRunnable(){
			public void run(){
				for(Hologram h : HologramsAPI.getHolograms(Main.getInst())){
					if((System.currentTimeMillis() - h.getCreationTimestamp()) > 15*1000){
						h.delete();
					}
				}
			}
		}.runTaskTimer(this, 0, 20);
	}
}