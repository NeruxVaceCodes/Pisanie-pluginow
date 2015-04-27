package me.kamilkime.youtube;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listenery implements Listener{

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		EconomyResponse er = Main.eco.depositPlayer(e.getPlayer().getName(), 10);
		if(er.transactionSuccess()){
			e.getPlayer().sendMessage("§aTrzymaj dyche!");
		}
		if(Main.eco.getBalance(e.getPlayer().getName()) >= 100){
			Main.perm.playerAdd(e.getPlayer(), "kimek.click");
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		if(e.getPlayer().getItemInHand().getType().equals(Material.GOLD_HOE)){
			if(!e.getPlayer().hasPermission("kimek.click")) return;
			Main.eco.withdrawPlayer(e.getPlayer().getName(), 50);
			Main.perm.playerRemove(e.getPlayer(), "kimek.click");
			e.getPlayer().sendMessage("§cDzieki za 50$ :P");
			e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.LEVEL_UP, 10, 1);
		}
	}
}