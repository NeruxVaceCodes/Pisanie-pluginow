package com.gmail.kamilkime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.server.v1_7_R4.PacketPlayOutWorldParticles;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{

	private List<UUID> hidden = new ArrayList<UUID>();
	
	public void onEnable(){
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@SuppressWarnings("deprecation")
	public void onDisable(){
		for(UUID id : hidden){
			if(Bukkit.getPlayer(id) !=null){
				for(Player p : Bukkit.getOnlinePlayers()) p.showPlayer(Bukkit.getPlayer(id));
			}
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		for(UUID id : hidden){
			if(Bukkit.getPlayer(id) !=null)  e.getPlayer().hidePlayer(Bukkit.getPlayer(id));
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		if(hidden.contains(e.getPlayer().getUniqueId())) hidden.remove(e.getPlayer().getUniqueId());
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("hide")){
			Player p = (Player) sender;
			if(hidden.contains(p.getUniqueId())) return true;
			for(Player pl: Bukkit.getOnlinePlayers()){
				pl.hidePlayer(p);
				((CraftPlayer)pl).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles("smoke", p.getLocation().getBlockX(),
						p.getLocation().getBlockY()+1, p.getLocation().getBlockZ(),(float) 0.5,(float) 0.5,(float) 0.5, 0, 1000));
				// 1.7.2 // ((CraftPlayer)pl).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(p.getPlayerListName(), false, 9999));
				// 1.7.10-1.8.x // PacketPlayOutPlayerInfo.removePlayer(((CraftPlayer)p).getHandle());
			}
			hidden.add(p.getUniqueId());
		}
		else if(cmd.getName().equalsIgnoreCase("show")){
			Player p = (Player) sender;
			if(!hidden.contains(p.getUniqueId())) return true;
			for(Player pl: Bukkit.getOnlinePlayers()){
				pl.showPlayer(p);
				((CraftPlayer)pl).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles("smoke", p.getLocation().getBlockX(),
						p.getLocation().getBlockY()+1, p.getLocation().getBlockZ(),(float) 0.5,(float) 0.5,(float) 0.5, 0, 1000));
				// 1.7.2 // ((CraftPlayer)pl).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerInfo(p.getPlayerListName(), true, 9999));
				// 1.7.10-1.8.x // PacketPlayOutPlayerInfo.addPlayer(((CraftPlayer)p).getHandle());
			}
			hidden.remove(p.getUniqueId());
		}
		return false;
	}
}