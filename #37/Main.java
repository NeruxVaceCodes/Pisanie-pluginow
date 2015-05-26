package me.kamilkime.youtube;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	private static Main instance;
	
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		System.out.println("§c§lWiadomosc bez koloru!");
		Bukkit.getLogger().info("To jest INFO");
		Bukkit.getLogger().severe("To jest SEVERE");
		Bukkit.getLogger().warning("To jest WARNING");
		Bukkit.getConsoleSender().sendMessage("§4§lDZIALA :D");
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "ChatColor tez dziala :D");
	}
	
	public void onDisable() {}

	public static Main getInst() {
		return instance;
	}
}