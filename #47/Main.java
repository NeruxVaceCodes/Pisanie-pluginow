package me.kamilkime.youtube;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	public static final Random RANDOM = new Random();
	private ServerVersion version;
	private String serverVersion;
	
	@Override
	public void onEnable(){
		Bukkit.getPluginManager().registerEvents(this, this);
		serverVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + ".";
		if(serverVersion.equalsIgnoreCase("v1_8_R1.")) version = ServerVersion.v1_8;
		else version = ServerVersion.v1_7;
	}
	
	@EventHandler
	public void onJoin(final PlayerJoinEvent e){
		final Location l = e.getPlayer().getLocation();
		Bukkit.getScheduler().runTaskLater(this, new Runnable(){
			public void run(){
				try {
					sendPacket(createPacket("heart", l.getBlockX(), (float) (l.getBlockY()+0.5), l.getBlockZ(), 1000), e.getPlayer());
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchFieldException | ClassNotFoundException
						| InstantiationException e) {
					e.printStackTrace();
				}
			}
		}, 50L);
	}
	
	private Class<?> getNmsClass(String className) throws ClassNotFoundException{
		String name = "net.minecraft.server." + serverVersion + className;
		return Class.forName(name);
	}
	
	private void sendPacket(Object packet, Player toSend) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException, ClassNotFoundException{
		Method getHandle = toSend.getClass().getMethod("getHandle");
		Object entityPlayer = getHandle.invoke(toSend);
		Field f = entityPlayer.getClass().getField("playerConnection");
		Object playerConnection = f.get(entityPlayer);
		Method sendPacket = playerConnection.getClass().getMethod("sendPacket", getNmsClass("Packet"));
		sendPacket.invoke(playerConnection, packet);
	}
	
	private Object createPacket(String name, float x, float y, float z, int particleAmount) throws NoSuchMethodException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if(version.equals(ServerVersion.v1_8)){
			Class<?> enumPart = getNmsClass("EnumParticle");
			Method valueOf = enumPart.getMethod("valueOf", String.class);
			Object partType = valueOf.invoke(enumPart, name.toUpperCase());
			Constructor<?> constr = getNmsClass("PacketPlayOutWorldParticles").getConstructor(getNmsClass("EnumParticle"), boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class, int[].class);
			return constr.newInstance(partType, true, x, y, z, 0.1F, 0.1F, 0.1F, 0F, particleAmount, null);
		} else {
			Constructor<?> constructor = getNmsClass("PacketPlayOutWorldParticles").getConstructor(String.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class);
			return constructor.newInstance(name, x, y, z, 0.1F, 0.1F, 0.1F, 0F, particleAmount);
		}
	}
}