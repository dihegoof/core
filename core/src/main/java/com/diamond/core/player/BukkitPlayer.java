package com.diamond.core.player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.diamond.core.Main;
import com.diamond.core.mysql.data.CoreQuerys;
import com.diamond.core.player.group.Group;
import com.diamond.core.player.group.GroupManager;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BukkitPlayer {
	
	UUID uniqueId;
	String name, address, group, tag;
	Player player;
	long timeGroup, joinIn, lastSee;
	List<String> permissions;
	boolean online;
	
	public BukkitPlayer(UUID uniqueId, String name, String address, String group, String tag, long timeGroup, long joinIn, long lastSee, List<String> permissions) {
		this.uniqueId = uniqueId;
		this.name = name;
		this.address = address;
		this.group = group;
		this.tag = tag;
		this.player = null;
		this.joinIn = joinIn;
		this.lastSee = lastSee;
		this.timeGroup = timeGroup;
		this.permissions = permissions;
		this.online = false;
	}
	
	public BukkitPlayer(UUID uniqueId, String name, String address) { 
		this.uniqueId = uniqueId;
		this.name = name;
		this.address = address;
		this.group = "NRE";
		this.tag = "NRE";
		this.player = null;
		this.joinIn = System.currentTimeMillis();
		this.lastSee = -1L;
		this.timeGroup = -1L;
		this.permissions = new ArrayList<>();
		this.online = false;
	}
	
	public BukkitPlayer(Player player) { 
		this.uniqueId = player.getUniqueId();
		this.name = player.getName();
		this.address = player.getAddress().getHostString();
		this.group = "NRE";
		this.tag = "NRE";
		this.player = null;
		this.joinIn = System.currentTimeMillis();
		this.lastSee = -1L;
		this.timeGroup = -1L;
		this.permissions = new ArrayList<>();
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
				stmtUpdate.setString(3, getGroup().getName());
				stmtUpdate.setLong(4, getTimeGroup());
				stmtUpdate.setLong(5, getLastSee());
				stmtUpdate.setString(6, getTag());
				stmtUpdate.setString(7, getPermissions().toString());
				stmtUpdate.setString(8, getUniqueId().toString());
				stmtUpdate.execute();
				stmtUpdate.close();
			} else { 
				PreparedStatement stmtInsert = Main.getMysql().getConn().prepareStatement(CoreQuerys.ACCOUNT_INSERT.getQuery());
				stmtInsert.setString(1, getUniqueId().toString());
				stmtInsert.setString(2, getName());
				stmtInsert.setString(3, getAddress());
				stmtInsert.setString(4, getGroup().getName());
				stmtInsert.setLong(5, getTimeGroup());
				stmtInsert.setLong(6, getJoinIn());
				stmtInsert.setLong(7, getLastSee());
				stmtInsert.setString(8, getTag());
				stmtInsert.setString(9, getPermissions().toString());
				stmtInsert.execute();
				stmtInsert.close();
			}
		} catch (Exception e) {
			Main.debug(e.getMessage());
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
	
	public Group getGroup() { 
		return GroupManager.getInstance().get(this.group);
	}
	
	public boolean hasPermission(String permission) { 
		return getPermissions().contains(permission) || getPermissions().contains("*") || getGroup().hasPermission(permission);
	}

	public void verify() {
		if(GroupManager.getInstance().get(getGroup().getName()) == null && GroupManager.getInstance().defaultGroup() != null) {
			setGroup(GroupManager.getInstance().defaultGroup().getName());
		}
	}
}
