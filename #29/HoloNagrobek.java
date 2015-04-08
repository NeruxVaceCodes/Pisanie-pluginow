package me.kamilkime.youtube;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;

public class HoloNagrobek implements Listener{
	
	// 07.04.2015 17:43:xx timeZone
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		Location l = e.getEntity().getLocation().add(0,2,0);
		Hologram h = HologramsAPI.createHologram(Main.getInst(), l);
		h.appendTextLine("§6§lTutaj zginal gracz §b§l" + e.getEntity().getName());
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z");
		h.insertTextLine(1, "§a§l" + sdf.format(new Date()));
		VisibilityManager vm = h.getVisibilityManager();
		vm.hideTo(e.getEntity());
		vm.setVisibleByDefault(true);
		/*
		 * vm.setVisibleByDefault(true);
		 * vm.resetVisibilityAll();
		 */
	}
}