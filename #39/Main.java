package me.kamilkime.youtube;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	private static Main inst;
	
	public Main(){
		inst = this;
	}
	
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	public void onDisable(){}

	public static Main getInst() {
		return inst;
	}
	
	@EventHandler
	public void onHelp(HelpCallEvent e){
		if(e.isCancelled()) return;
		if(!e.getPlayer().hasPermission("khelp.cmd")){
			e.getPlayer().sendMessage("§cNie masz uprawnien!");
			Bukkit.broadcastMessage(e.getMessage());
			e.setCancelled(true);
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("cmdevent")){
			if(args.length == 0) return true;
			String msg = "";
			for(int i=0; i<args.length; i++){
				msg += args[i] + " ";
			}
			Player p = (Player) sender;
			HelpCallEvent event = new HelpCallEvent(msg, p);
			Bukkit.getPluginManager().callEvent(event);
			if(event.isCancelled()) return true;
			Bukkit.getConsoleSender().sendMessage("§6§l" + p.getName() + "§8§l: §b" + msg);
		}
		return false;
	}
}