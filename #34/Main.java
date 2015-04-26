package me.kamilkime.youtube;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;

public class Main extends JavaPlugin{
	
	private static Main instance;
	private WorldEditPlugin we;
	private WorldGuardPlugin wg;
	
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
		wg = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
	}
	
	public void onDisable() {}

	public static Main getInst() {
		return instance;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("cub")){
			Player p = (Player) sender;
			String owner = args[0];
			String ulica = args[1];
			if(we.getSelection(p) == null){
				p.sendMessage("§cNo selection!");
				return true;
			}
			ProtectedCuboidRegion c = new ProtectedCuboidRegion("plot_" + owner,
					new BlockVector(we.getSelection(p).getNativeMinimumPoint()),
					new BlockVector(we.getSelection(p).getNativeMaximumPoint())
			);
			if(wg.getRegionManager(p.getWorld()).getRegion(c.getId()) !=null){
				p.sendMessage("§cTen gracz ma juz dzialke!");
				return true;
			}
			if(ulica.equalsIgnoreCase("las")){
				c.setMinimumPoint(new BlockVector(c.getMinimumPoint().subtract(0, 20, 0)));
				c.setMaximumPoint(new BlockVector(c.getMaximumPoint().add(0, 30, 0)));
			}
			if(ulica.equalsIgnoreCase("morska")){
				c.setMinimumPoint(new BlockVector(c.getMinimumPoint().subtract(0, 40, 0)));
				c.setMaximumPoint(new BlockVector(c.getMaximumPoint().add(0, 60, 0)));
			} else{
				p.sendMessage("§cNie ma takiej ulicy!");
				return true;
			}
			DefaultDomain dd = new DefaultDomain();
			dd.addPlayer(owner);
			c.setOwners(dd);
			wg.getRegionManager(p.getWorld()).addRegion(c);
			try{
				wg.getRegionManager(p.getWorld()).save();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		return false;
	}
}