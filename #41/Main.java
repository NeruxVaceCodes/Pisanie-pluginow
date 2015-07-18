package me.kamilkime.youtube;

import net.minecraft.server.v1_7_R1.PacketPlayOutWorldParticles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	public void onEnable(){
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e){
	    Location from = e.getFrom();
	    Location to = e.getTo();
	    if((from.getX() != to.getX()) || (from.getY() != to.getY()) || (from.getZ() != to.getZ())){
	    	Location l = e.getPlayer().getLocation().add(0, 1.15, 0);
	    	PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles("heart", (float) l.getX(), (float) l.getY(), (float) l.getZ(), 0F, 0F, 0F, 0F, 100);
//Na 1.8    PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.HEART, false, (float) l.getX(), (float) l.getY(), (float) l.getZ(), 0F, 0F, 0F, 0F, 100, null);
	    	for(Player p : Bukkit.getOnlinePlayers()){
	    		((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
	    	}
	    }
	}
}