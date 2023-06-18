package com.diamond.core.player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.diamond.core.Main;
import com.diamond.core.mysql.data.CoreQuerys;
import com.diamond.core.player.ranking.Rank;
import com.diamond.core.player.tags.Tag;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BukkitPlayer {
	
	UUID uniqueId;
	String name, address;
	Player player;
	Group group;
	long timeGroup, joinIn, lastSee;
	Tag tag;
	Rank rank;
	List<String> permissions;
	int xp;
	double coins;
	boolean online;
	
	public BukkitPlayer(UUID uniqueId, String name, String address, Group group, long timeGroup, long joinIn, long lastSee, Tag tag, Rank rank, List<String> permissions, int xp, double coins) {
		this.uniqueId = uniqueId;
		this.name = name;
		this.address = address;
		this.player = null;
		this.group = group;
		this.joinIn = joinIn;
		this.lastSee = lastSee;
		this.timeGroup = timeGroup;
		this.tag = tag;
		this.rank = rank;
		this.permissions = permissions;
		this.xp = xp;
		this.coins = coins;
		this.online = false;
	}
	
	public BukkitPlayer(UUID uniqueId, String name, String address) { 
		this.uniqueId = uniqueId;
		this.name = name;
		this.address = address;
		this.player = null;
		this.group = Group.MEMBRO;
		this.timeGroup = -1L;
		this.joinIn = System.currentTimeMillis();
		this.lastSee = -1L;
		this.tag = this.group.getTag();
		this.rank = Rank.UNRANKED;
		this.permissions = new ArrayList<>();
		this.xp = 0;
		this.coins = 0.0;
		this.online = false;
	}
	
	public BukkitPlayer(Player player) { 
		this.uniqueId = player.getUniqueId();
		this.name = player.getName();
		this.address = player.getAddress().getHostString();
		this.player = player;
		this.group = Group.MEMBRO;
		this.timeGroup = -1L;
		this.joinIn = System.currentTimeMillis();
		this.lastSee = -1L;
		this.tag = this.group.getTag();
		this.rank = Rank.UNRANKED;
		this.permissions = new ArrayList<>();
		this.xp = 0;
		this.coins = 0.0;
		this.online = true;
	}
	
	public BukkitPlayer(String name) { 
		this.uniqueId = null;
		this.name = name;
		this.address = "127.0.0.1";
		this.player = null;
		this.group = Group.MEMBRO;
		this.timeGroup = -1L;
		this.joinIn = System.currentTimeMillis();
		this.lastSee = -1L;
		this.tag = this.group.getTag();
		this.rank = Rank.UNRANKED;
		this.permissions = new ArrayList<>();
		this.xp = 0;
		this.coins = 0.0;
		this.online = true;
	}
	
	public void save() { 
		try {
			PreparedStatement stmt = Main.getMysql().getConn().prepareStatement(CoreQuerys.ACCOUNT_SELECT.getQuery());
			stmt.setString(1, getUniqueId().toString());
			if(exists()) { 
				PreparedStatement stmtUpdate = Main.getMysql().getConn().prepareStatement(CoreQuerys.ACCOUNT_UPDATE.getQuery());
				stmtUpdate.setString(1, getName());
				stmtUpdate.setString(2, getAddress());
				stmtUpdate.setString(3, getGroup().toString());
				stmtUpdate.setLong(4, getTimeGroup());
				stmtUpdate.setLong(5, getLastSee());
				stmtUpdate.setString(6, getTag().toString());
				stmtUpdate.setString(7, getRank().toString());
				stmtUpdate.setString(8, getPermissions().toString());
				stmtUpdate.setInt(9, getXp());
				stmtUpdate.setDouble(10, getCoins());
				stmtUpdate.setString(11, getUniqueId().toString());
				stmtUpdate.execute();
				stmtUpdate.close();
			} else { 
				PreparedStatement stmtInsert = Main.getMysql().getConn().prepareStatement(CoreQuerys.ACCOUNT_INSERT.getQuery());
				stmtInsert.setString(1, getUniqueId().toString());
				stmtInsert.setString(2, getName());
				stmtInsert.setString(3, getAddress());
				stmtInsert.setString(4, getGroup().toString());
				stmtInsert.setLong(5, getTimeGroup());
				stmtInsert.setLong(6, getJoinIn());
				stmtInsert.setLong(7, getLastSee());
				stmtInsert.setString(8, getTag().toString());
				stmtInsert.setString(9, getRank().toString());
				stmtInsert.setString(10, getPermissions().toString());
				stmtInsert.setInt(11, getXp());
				stmtInsert.setDouble(12, getCoins());
				stmtInsert.execute();
				stmtInsert.close();
			}
		} catch (Exception e) {
			Main.debug("1º> " + e.getMessage());
		}
	}
	
	public boolean exists() {
		try {
			PreparedStatement stmt = Main.getMysql().getConn().prepareStatement(CoreQuerys.ACCOUNT_SELECT.getQuery());
			stmt.setString(1, getUniqueId().toString());
			ResultSet rs = stmt.executeQuery();
			return rs.next();
		} catch (Exception e) {
			return false;
		}
	}
}
