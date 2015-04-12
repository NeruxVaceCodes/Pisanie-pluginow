package me.kamilkime.youtube;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.handler.PickupHandler;
import com.gmail.filoghost.holographicdisplays.api.handler.TouchHandler;
import com.gmail.filoghost.holographicdisplays.api.line.ItemLine;

public class PowerUp implements CommandExecutor{

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("power")){
			Location l = ((Player)sender).getTargetBlock(null, 5).getLocation().add(0,2.6,0);
			final Hologram h = HologramsAPI.createHologram(Main.getInst(), l);
			h.appendTextLine("§a§lCukier chyzosci");
			ItemLine it = h.appendItemLine(new ItemStack(Material.SUGAR));
			it.setPickupHandler(new PickupHandler(){
				@Override
				public void onPickup(Player p) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10*20, 1));
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 2);
					h.delete();
				}
			});
		}
		if(cmd.getName().equalsIgnoreCase("power2")){
			Location l = ((Player)sender).getTargetBlock(null, 5).getLocation().add(0,2.6,0);
			final Hologram h = HologramsAPI.createHologram(Main.getInst(), l);
			h.appendTextLine("§9§lWoda oddechu");
			ItemLine it = h.appendItemLine(new ItemStack(Material.WATER));
			it.setTouchHandler(new TouchHandler(){
				@Override
				public void onTouch(Player p) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 10*20, 4));
					p.playSound(p.getLocation(), Sound.WATER, 1, 2);
					h.delete();
				}
			});
		}
		return false;
	}
}