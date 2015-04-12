package me.kamilkime.youtube;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

public class HoloNagrobek implements Listener{
	
	// 07.04.2015 17:43:xx timeZone
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		Location l = e.getEntity().getLocation().add(0,2,0);
		Hologram h = HologramsAPI.createHologram(Main.getInst(), l);
		h.appendTextLine("§6§lTutaj zginal gracz §b§l" + e.getEntity().getName());
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z");
		h.insertTextLine(1, "§a§l" + sdf.format(new Date()));
		if(e.getEntity().getLastDamageCause().getCause().equals(DamageCause.FALL)){
			h.appendItemLine(new ItemStack(Material.GRASS));
		}
		if(e.getEntity().getLastDamageCause().getCause().equals(DamageCause.LIGHTNING)){
			h.appendItemLine(new ItemStack(Material.NETHER_STAR));
		}
		if(e.getEntity().getLastDamageCause().getCause().equals(DamageCause.FIRE)){
			h.appendItemLine(new ItemStack(Material.FIRE));
		}
		if(e.getEntity().getLastDamageCause().getCause().equals(DamageCause.ENTITY_ATTACK)){
			h.appendItemLine(new ItemStack(Material.DIAMOND_SWORD));
		}
		if(e.getEntity().getLastDamageCause().getCause().equals(DamageCause.CONTACT)){
			h.appendItemLine(new ItemStack(Material.CACTUS));
		}
	}
}