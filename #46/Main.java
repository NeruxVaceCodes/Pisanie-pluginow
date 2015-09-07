package me.kamilkime.youtube;

import java.util.Random;

import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	//private ProtocolManager protocolManager;
	public static final Random RANDOM = new Random();
	
	@Override
	public void onLoad(){
		//protocolManager = ProtocolLibrary.getProtocolManager();
	}
	
	@Override
	public void onEnable(){
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		IChatBaseComponent title = ChatSerializer.a("{\"text\":\"§6§lTo jest tytul dla ciebie!\",\"underlined\":\"true\"}");
		IChatBaseComponent subtitle = ChatSerializer.a("{\"text\":\"To jest podtytul dla ciebie!\",\"bold\":\"true\",\"italic\":\"true\",\"bold\":\"green\"}");
		IChatBaseComponent chat = ChatSerializer.a("{\"text\":\"§b§lWbijaj na moj kanal! \","
													+ "\"extra\":[{"
														+ "\"text\":\"§7§lKliknij mnie!\","
														+ "\"hoverEvent\":{"
															+ "\"action\":\"show_text\",\"value\":\"§a§lTak jest, kliknij mnie!\""
														+ "},\"clickEvent\":{"
															+ "\"action\":\"open_url\",\"value\":\"http://youtube.pl/kamilkime/\""
														+ "}}]}");
		PacketPlayOutChat chatp = new PacketPlayOutChat(chat);
		PacketPlayOutTitle titlep = new PacketPlayOutTitle(EnumTitleAction.TITLE, title, 5, 10, 15);
		PacketPlayOutTitle subtitlep = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, subtitle, 10, 5, 15);
		((CraftPlayer)e.getPlayer()).getHandle().playerConnection.sendPacket(chatp);
		((CraftPlayer)e.getPlayer()).getHandle().playerConnection.sendPacket(titlep);
		((CraftPlayer)e.getPlayer()).getHandle().playerConnection.sendPacket(subtitlep);
	}
}