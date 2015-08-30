package me.kamilkime.youtube;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.comphenix.packetwrapper.WrapperPlayClientUseEntity;
import com.comphenix.packetwrapper.WrapperPlayServerEntityDestroy;
import com.comphenix.packetwrapper.WrapperPlayServerSpawnEntity;
import com.comphenix.packetwrapper.WrapperPlayServerSpawnEntityLiving;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.EnumWrappers.EntityUseAction;

public class Main extends JavaPlugin implements Listener{
	
	private ProtocolManager protocolManager;
	private int entId = 6000;
	private Map<String, Integer> spawned = new HashMap<String, Integer>();
	
	@Override
	public void onLoad(){
		protocolManager = ProtocolLibrary.getProtocolManager();
	}
	
	@Override
	public void onEnable(){
		Bukkit.getPluginManager().registerEvents(this, this);
		protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.USE_ENTITY){
			@Override
			public void onPacketReceiving(PacketEvent e){
				if(!e.getPacketType().equals(PacketType.Play.Client.USE_ENTITY)) return;
				WrapperPlayClientUseEntity packet = new WrapperPlayClientUseEntity(e.getPacket());
				if(!spawned.containsKey(e.getPlayer().getName())) return;
				if(!packet.getMouse().equals(EntityUseAction.INTERACT)) return;
				if(packet.getTargetID() !=spawned.get(e.getPlayer().getName())) return;
				if(e.getPlayer().getItemInHand() == null || !e.getPlayer().getItemInHand().getType().equals(Material.REDSTONE)) return;
				WrapperPlayServerEntityDestroy packetToSend = new WrapperPlayServerEntityDestroy();
				packetToSend.setEntities(new int[]{ spawned.remove(e.getPlayer().getName()) });
				packetToSend.sendPacket(e.getPlayer());
			}
		});
		for(Player p : Bukkit.getOnlinePlayers()){
			runMobSpawner(p);
		}
	}
	
	@EventHandler
	public void onJoin(final PlayerJoinEvent e){
		runMobSpawner(e.getPlayer());
	}
	
	private void runMobSpawner(final Player p){
		new BukkitRunnable(){
			public void run(){
				int id = entId++;
				spawned.put(p.getName(), id);
				WrapperPlayServerSpawnEntityLiving packet = new WrapperPlayServerSpawnEntityLiving();
				packet.setType(EntityType.BAT);
				packet.setEntityID(id);
				packet.setX(p.getLocation().getX());
				packet.setY(p.getLocation().getY() + 3);
				packet.setZ(p.getLocation().getZ());
				WrappedDataWatcher data = new WrappedDataWatcher();
				data.setObject(0, (byte) 0x01);
				data.setObject(6, (float) 5);
				data.setObject(10, "§5Nietoperek :3");
				data.setObject(11, (byte) 1);
				data.setObject(16, (byte) 1);
				packet.setMetadata(data);
				packet.sendPacket(p);
				
				WrapperPlayServerSpawnEntity packet2 = new WrapperPlayServerSpawnEntity();
				packet2.setEntityID(entId++);
				packet2.setX(p.getLocation().getX());
				packet2.setY(p.getLocation().getY() + 10);
				packet2.setZ(p.getLocation().getZ());
				packet2.setType(70);
				packet2.setObjectData(49);
				packet2.sendPacket(p);
			}
		}.runTaskLater(this, 40);
	}
}