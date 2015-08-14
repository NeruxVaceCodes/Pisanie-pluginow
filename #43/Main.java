package me.kamilkime.youtube;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.packetwrapper.WrapperStatusServerOutServerInfo;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import com.comphenix.protocol.wrappers.WrappedServerPing.CompressedImage;

public class Main extends JavaPlugin implements Listener{
	
	private ProtocolManager protocolManager;
	private CompressedImage favicon;
	
	@Override
	public void onLoad(){
		protocolManager = ProtocolLibrary.getProtocolManager();
	}
	
	@Override
	public void onEnable(){
		Bukkit.getPluginManager().registerEvents(this, this);
		try {
			favicon = CompressedImage.fromPng(getIcon("skkf"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Status.Server.OUT_SERVER_INFO){
			public void onPacketSending(PacketEvent e){
				if(e.getPacketType() !=PacketType.Status.Server.OUT_SERVER_INFO) return;
				WrapperStatusServerOutServerInfo packet = new WrapperStatusServerOutServerInfo(e.getPacket());
				WrappedServerPing ping = packet.getServerPing();
				ping.setFavicon(favicon);
				ping.setMotD(WrappedChatComponent.fromText("§c§lZhackowalem :D"));
				ping.setPlayersMaximum(ping.getPlayersMaximum() + 5);
				ping.setPlayersOnline(ping.getPlayersOnline() + 3);
				List<WrappedGameProfile> wgp = new ArrayList<WrappedGameProfile>();
				int id = 0;
				for(String s : Arrays.asList("§6§lIP Serwera", "§b§lIP TSa", "§a§lStrona serwera")){
					wgp.add(new WrappedGameProfile(String.valueOf(id++), s));
				}
				ping.setPlayers(wgp);
				ping.setVersionProtocol(3);
				ping.setVersionName("§6Online§8: §7" + (ping.getPlayersOnline()+3) + "§8/§7" + (ping.getPlayersMaximum() + 5) + "                     ");
			}
		});
	}
	
	private BufferedImage getIcon(String userName) throws IOException {
		URL asset = new URL("http://s3.amazonaws.com/MinecraftSkins/" + userName + ".png");
		Image img = ImageIO.read(asset).getSubimage(8, 8, 8, 8);
		return toBufferedImage(img.getScaledInstance(64, 64, 1));
	}
	    
	private BufferedImage toBufferedImage(Image image) {
		BufferedImage buffer = new BufferedImage(
				image.getWidth(null), 
				image.getHeight(null), 
				BufferedImage.TYPE_INT_ARGB
				);
		Graphics2D g = buffer.createGraphics();
		g.drawImage(image, null, null);
		return buffer;
	}
}