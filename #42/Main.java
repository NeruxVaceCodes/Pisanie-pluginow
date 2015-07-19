package me.kamilkime.youtube;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;

public class Main extends JavaPlugin implements Listener{
	
	private ProtocolManager protocolManager;
	
	@Override
	public void onLoad(){
		protocolManager = ProtocolLibrary.getProtocolManager();
	}
	
	@Override
	public void onEnable(){
		Bukkit.getPluginManager().registerEvents(this, this);
		protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Server.WORLD_PARTICLES){
			public void onPacketSending(PacketEvent e){
				if(!e.getPacketType().equals(PacketType.Play.Server.WORLD_PARTICLES)) return;
				PacketContainer packet = e.getPacket();
				StructureModifier<Float> floats = packet.getFloat();
				Location l = new Location(Bukkit.getWorlds().get(0), floats.read(0), floats.read(1), floats.read(2));
				List<Entity> ents = getEntitiesInRange(l, 4);
				for(Entity ent : ents){
					if(ent.getType().equals(EntityType.CREEPER)){
						packet.getStrings().write(0, "angryVillager");
						return;
					}
				}
			}
		});
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e){
		Location from = e.getFrom();
	    Location to = e.getTo();
	    if((from.getX() != to.getX()) || (from.getY() != to.getY()) || (from.getZ() != to.getZ())){
	    	Location l = e.getPlayer().getLocation().add(0, 1.15, 0);
	    	PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.WORLD_PARTICLES);
	    	packet.getStrings().write(0, "heart");
	    	packet.getFloat().write(0, (float) l.getX())
	    		.write(1, (float) l.getY())
	    		.write(2, (float) l.getZ())
	    		.write(3, 0F)
	    		.write(4, 0F)
	    		.write(5, 0F)
	    		.write(6, 0F);
	    	packet.getIntegers().write(0, 100);
	    	for(Player p : Bukkit.getOnlinePlayers()){
	    		try {
					protocolManager.sendServerPacket(p, packet);
				} catch (InvocationTargetException e1) {
					e1.printStackTrace();
				}
	    	}
	    }
	}
	
	private List<Entity> getEntitiesInRange(Location loc, int range){
		List<Entity> ents = new ArrayList<Entity>();
		for(Entity e : Bukkit.getWorlds().get(0).getEntities()){
			if(e.getLocation().distance(loc) < range+1){
				ents.add(e);
			}
		}
		return ents;
	}
}