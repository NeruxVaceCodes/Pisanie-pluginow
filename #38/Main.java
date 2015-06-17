package me.kamilkime.youtube;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	private static Main inst;
	
	public Main(){
		inst = this;
	}
	
	public void onEnable() {
		FileManager.checkFiles();
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	public void onDisable(){}

	public static Main getInst() {
		return inst;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) throws IOException{
		File f;
		if(FileManager.getPFile(e.getPlayer()) == null){
			f = new File(FileManager.getUsersFolder(), e.getPlayer().getName() + ".yml");
			f.createNewFile();
		} else{
			f = FileManager.getPFile(e.getPlayer());
		}
		YamlConfiguration fYml = YamlConfiguration.loadConfiguration(f);
		fYml.set("name", e.getPlayer().getName());
		fYml.set("uuid", e.getPlayer().getUniqueId().toString());
		fYml.set("ip", e.getPlayer().getAddress().getAddress().toString().replace("/", ""));
		fYml.save(f);
		e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', FileManager.getMsg().getString("helloMsg")));
	}
}