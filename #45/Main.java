package me.kamilkime.youtube;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.packetwrapper.WrapperPlayServerEntityEquipment;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

@SuppressWarnings("unused")
public class Main extends JavaPlugin implements Listener{
	
	private ProtocolManager protocolManager;
	private ItemStack[] armor;
	public static final Random RANDOM = new Random();
	
	@Override
	public void onLoad(){
		protocolManager = ProtocolLibrary.getProtocolManager();
	}
	
	@Override
	public void onEnable(){
		Bukkit.getPluginManager().registerEvents(this, this);
		initializeArmor();
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e){
	    Location from = e.getFrom();
	    Location to = e.getTo();
	    if ((from.getBlockX() != to.getBlockX()) || (from.getBlockY() != to.getBlockY()) || (from.getBlockZ() != to.getBlockZ()) || (from.getWorld() != to.getWorld())) {
	    	Player player = e.getPlayer();
	    	ItemStack[] armor = getColoredArmor();
	    	for(Player p : Bukkit.getOnlinePlayers()){
	    		if(p.equals(player)) continue;
	    		for(short i=1; i<5; i++){
	    			WrapperPlayServerEntityEquipment packet = new WrapperPlayServerEntityEquipment();
	    			packet.setEntityId(player.getEntityId());
	    			packet.setSlot(i);
	    			packet.setItem(armor[i-1]);
	    			packet.sendPacket(p);
	    		}
	    	}
	    }
	}
	
	private void initializeArmor(){
		armor = new ItemStack[4];
		String[] endings = new String[4];{
			endings[0] = "_BOOTS";
			endings[1] = "_LEGGINGS";
			endings[2] = "_CHESTPLATE";
			endings[3] = "_HELMET";
		}
		for(int i=0; i<4; i++){
			ItemStack is = new ItemStack(Material.getMaterial("LEATHER" + endings[i]), 1);
			armor[i] = is;
		}
	}
	
	private ItemStack[] getColoredArmor(){
		ItemStack[] armor = this.armor.clone();
		Color c = Color.fromRGB(RANDOM.nextInt(256), RANDOM.nextInt(256), RANDOM.nextInt(256));
		for(ItemStack is : armor){
			LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
			im.setColor(c);
			is.setItemMeta(im);
		}
		return armor;
	}
}