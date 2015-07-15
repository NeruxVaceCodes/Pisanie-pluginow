package me.kamilkime.youtube;

import java.util.UUID;

import org.bukkit.entity.Player;

public class User {

	private String name;
	private UUID uuid;
	private int rank;
	private int kills;
	private int deaths;
	
	private User(Player p){
		this.uuid = p.getUniqueId();
		this.name = p.getName();
		this.rank = 1000;
		this.kills = 0;
		this.deaths = 0;
		UserUtils.addUser(this);
	}
	
	private User(UUID uuid){
		this.uuid = uuid;
		UserUtils.addUser(this);
	}
	
	public String getName() {
		return this.name;
	}

	public UUID getUUID() {
		return this.uuid;
	}

	public int getRank() {
		return this.rank;
	}

	public int getKills() {
		return this.kills;
	}

	public int getDeaths() {
		return this.deaths;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public void setKills(int kills) {
		this.kills = kills;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public static User get(UUID uuid){
		for(User u : UserUtils.getUsers()){
			if(u.getUUID().equals(uuid)) return u;
		}
		return new User(uuid);
	}
	
	public static User get(Player p){
		for(User u : UserUtils.getUsers()){
			if(u.getUUID().equals(p.getUniqueId())) return u;
		}
		return new User(p);
	}
	
	public static User get(String name){
		for(User u : UserUtils.getUsers()){
			if(u.getName().equals(name)) return u;
		}
		return null;
	}
}