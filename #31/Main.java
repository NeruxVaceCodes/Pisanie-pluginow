package me.kamilkime.youtube;

import java.util.ArrayList;
import java.util.List;

import me.confuser.barapi.BarAPI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin implements Listener{
	
	private static Main instance;
	private List<String> msgs = new ArrayList<String>();{
		msgs.add("§1§lWiadomosc numer 1!");
		msgs.add("§a§lWiadomosc numer 2!");
		msgs.add("§6§lWiadomosc numer 3!");
		msgs.add("§c§lWiadomosc numer 4!");
		msgs.add("§d§lWiadomosc numer 5!");
		msgs.add("§5§lWiadomosc numer 6!");
	}
	int number;
	
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		Bukkit.getPluginManager().registerEvents(this, this);
		startTimer();
	}
	
	public void onDisable() {}

	public static Main getInst() {
		return instance;
	}
	
	private void startTimer(){
		number = 0;
		new BukkitRunnable(){
			public void run(){
				for(Player p : Bukkit.getOnlinePlayers()){
					if(!BarAPI.hasBar(p)){
						BarAPI.setMessage(p, msgs.get(number), 10);
					}
				}
				number++;
				if(number == msgs.size()){
					number = 0;
				}
			}
		}.runTaskTimer(this, 0, 10*20);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		BarAPI.setMessage(e.getPlayer(), "§6§lWitaj na serwerze!", 5);
	}
}