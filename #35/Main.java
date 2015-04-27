package me.kamilkime.youtube;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	private static Main instance;
	public static Economy eco;
	public static Permission perm;
	
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		Bukkit.getPluginManager().registerEvents(new Listenery(), this);
		if(!checkVault()){
			this.setEnabled(false);
		}
		checkEco();
		checkPerm();
	}
	
	public void onDisable() {}

	public static Main getInst() {
		return instance;
	}
	
	private boolean checkVault(){
		if(Bukkit.getPluginManager().getPlugin("Vault") !=null) return true;
		return false;
	}
	
	private boolean checkEco(){
		RegisteredServiceProvider<Economy> ecop = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		if(ecop == null) return false;
		eco = ecop.getProvider();
		if(eco == null) return false;
		return true;
	}
	
	private boolean checkPerm(){
		RegisteredServiceProvider<Permission> ecop = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
		if(ecop == null) return false;
		perm = ecop.getProvider();
		if(perm == null) return false;
		return true;
	}
}