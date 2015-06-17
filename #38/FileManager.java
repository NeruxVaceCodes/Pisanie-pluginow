package me.kamilkime.youtube;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class FileManager {

	private static YamlConfiguration msg;
	private static File users = new File(Main.getInst().getDataFolder(), "users");
	
	public static void checkFiles(){
		if(!Main.getInst().getDataFolder().exists()){
			Main.getInst().getDataFolder().mkdir();
		}
		if(!new File(Main.getInst().getDataFolder(), "config.yml").exists()){
			Main.getInst().saveDefaultConfig();
		}
		File m = new File(Main.getInst().getDataFolder(), "messages.yml");
		if(!m.exists()){
			Main.getInst().saveResource("messages.yml", true);
		}
		if(!users.exists()){
			users.mkdir();
		}
		msg = YamlConfiguration.loadConfiguration(m);
	}
	
	public static YamlConfiguration getMsg(){
		return msg;
	}
	
	public static File getPFile(Player p){
		File f = new File(users, p.getName() + ".yml");
		if(!f.exists()) return null;
		return f;
	}
	
	public static File getUsersFolder(){
		return users;
	}
}