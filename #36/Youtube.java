package me.kamilkime.youtube;

import java.util.Arrays;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Youtube extends JavaPlugin implements Listener {

	private static Youtube instance;

	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		getServer().getPluginManager().registerEvents(this, this);
	}

	public void onDisable() {}

	public static Youtube getInst() {
		return instance;
	}

	@EventHandler
	public void onEnchant(EnchantItemEvent e) {
		Set<Enchantment> toAdd = e.getEnchantsToAdd().keySet();
		if (toAdd.contains(Enchantment.FIRE_ASPECT) || toAdd.contains(Enchantment.KNOCKBACK)) {
			e.getEnchanter().sendMessage("§cTen enchant jest wylaczony!");
			e.setCancelled(true);
		}
	}

	public static ItemStack create(Material m, int amount) {
		return new ItemStack(m, amount);
	}

	public static ItemStack create(Material m, int amount, short data, String name) {
		ItemStack i = new ItemStack(m, amount, data);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		i.setItemMeta(im);
		return i;
	}

	public static ItemStack create(Material m, int amount, short data, String... lore) {
		ItemStack i = new ItemStack(m, amount, data);
		ItemMeta im = i.getItemMeta();
		im.setLore(Arrays.asList(lore));
		i.setItemMeta(im);
		return i;
	}

	public static ItemStack create(Material m, int amount, short data, String name, String... lore) {
		ItemStack i = new ItemStack(m, amount, data);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		im.setLore(Arrays.asList(lore));
		i.setItemMeta(im);
		return i;
	}

	public static ItemStack create(Material m, int amount, short data) {
		return new ItemStack(m, amount, data);
	}
}