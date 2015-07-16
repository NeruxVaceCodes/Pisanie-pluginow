package me.kamilkime.youtube;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
	private static final Random RAND = new Random();
	private Connection conn;
	
	public void onEnable(){
		checkTable();
		try {
			loadData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	public void onDisable(){
		try {
			saveData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void checkTable(){
		openConnection();
		StringBuilder sb = new StringBuilder();
		sb.append("create table if not exists users(");
		sb.append("uuid varchar(100) not null,");
		sb.append("name varchar(50) not null,");
		sb.append("rank int not null,");
		sb.append("kills int not null,");
		sb.append("deaths int not null,");
		sb.append("primary key(uuid));");
		try {
			conn.createStatement().executeUpdate(sb.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    closeConnection();
	}
	
	private void loadData() throws SQLException{
		openConnection();
		int i = 0;
		ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM `users`");
		while(rs.next()){
			User u = User.get(UUID.fromString(rs.getString("uuid")));
			u.setName(rs.getString("name"));
			u.setRank(rs.getInt("rank"));
			u.setKills(rs.getInt("kills"));
			u.setDeaths(rs.getInt("deaths"));
			i++;
			
		}
		Bukkit.getConsoleSender().sendMessage("§a§lLoaded §6§l" + i + " §a§lusers");
		closeConnection();
	}
	
	private void saveData() throws SQLException{
		openConnection();
		int i = 0;
		for(User u : UserUtils.getUsers()){
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO users (uuid, name, rank, kills, deaths) VALUES (");
			sb.append("'" + u.getUUID().toString() +"',");
			sb.append("'" + u.getName() +"',");
			sb.append("'" + u.getRank() +"',");
			sb.append("'" + u.getKills() +"',");
			sb.append("'" + u.getDeaths() +"'");
			sb.append(") ON DUPLICATE KEY UPDATE ");
			sb.append("name='" + u.getName() +"',");
			sb.append("rank='" + u.getRank() +"',");
			sb.append("kills='" + u.getKills() +"',");
			sb.append("deaths='" + u.getDeaths() +"';");
			conn.createStatement().executeUpdate(sb.toString());
			i++;
		}
		Bukkit.getConsoleSender().sendMessage("§a§lSaved §6§l" + i + " §a§lusers");
		closeConnection();
	}
	
	private synchronized void openConnection(){
		if(!isConnected()){
			try{
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/youtube?user=root&password=");
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	private synchronized void closeConnection(){
		if(isConnected()){
			try{
				conn.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	public boolean isConnected() {
		try{
			if(conn == null) return false;
			if(conn.isClosed()) return false;
		} catch(SQLException e){
			e.printStackTrace();
		}
		return true;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		User u = User.get(e.getPlayer());
		switch (RAND.nextInt(4)){
		case 0:
			u.setRank(u.getRank() + 50);
			Bukkit.broadcastMessage("§aRank");
			break;
		case 1:
			u.setKills(u.getKills() + 1);
			Bukkit.broadcastMessage("§aKills");
			break;
		case 2:
			u.setDeaths(u.getDeaths() + 1);
			Bukkit.broadcastMessage("§aDeaths");
			break;
		case 3:
			u.setRank(u.getRank() + 50);
			u.setKills(u.getKills() + 1);
			u.setDeaths(u.getDeaths() + 1);
			Bukkit.broadcastMessage("§aAll");
			break;
		}
	}
}
