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
import com.diamond.core.player.tags.Tag;
import com.diamond.core.player.tags.TagManager;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BukkitPlayer {
	
	UUID uniqueId;
	String name, address, group, lastGroup, tag;
	Player player;
	long timeGroup, joinIn, lastSee;
	List<String> permissions;
	boolean online;
	
	public BukkitPlayer(UUID uniqueId, String name, String address, String group, String lastGroup, String tag, long timeGroup, long joinIn, long lastSee, List<String> permissions) {
		this.uniqueId = uniqueId;
		this.name = name;
		this.address = address;
		this.group = group;
		this.lastGroup = lastGroup;
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
		this.lastGroup = "NRE";
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
		this.lastGroup = "NRE";
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
				stmtUpdate.setString(4, getLastGroup());
				stmtUpdate.setLong(5, getTimeGroup());
				stmtUpdate.setLong(6, getLastSee());
				stmtUpdate.setString(7, getTag());
				stmtUpdate.setString(8, getPermissions().toString());
				stmtUpdate.setString(9, getUniqueId().toString());
				stmtUpdate.execute();
				stmtUpdate.close();
			} else { 
				PreparedStatement stmtInsert = Main.getMysql().getConn().prepareStatement(CoreQuerys.ACCOUNT_INSERT.getQuery());
				stmtInsert.setString(1, getUniqueId().toString());
				stmtInsert.setString(2, getName());
				stmtInsert.setString(3, getAddress());
				stmtInsert.setString(4, getGroup().getName());
				stmtInsert.setString(5, getLastGroup());
				stmtInsert.setLong(6, getTimeGroup());
				stmtInsert.setLong(7, getJoinIn());
				stmtInsert.setLong(8, getLastSee());
				stmtInsert.setString(9, getTag());
				stmtInsert.setString(10, getPermissions().toString());
				stmtInsert.execute();
				stmtInsert.close();
			}
		} catch (Exception e) {
			Main.debug("> " + e.getLocalizedMessage());
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

	public void check() {
		if(!GroupManager.getInstance().getGroups().isEmpty()) { 
			if(this.group.equals("NRE") && GroupManager.getInstance().defaultGroup() != null) {
				setGroup(GroupManager.getInstance().defaultGroup().getName());
			}
		}
		if(getTag().equalsIgnoreCase("NRE")) { 
			if(!TagManager.getInstance().getTags().isEmpty()) { 
				if(TagManager.getInstance().get(getGroup().getName()) != null && TagManager.getInstance().get(getGroup().getName()).hasPrefix()) {
					TagManager.getInstance().get(getGroup().getName()).applyTag(this);
				}
			}
			return;
		}
		TagManager.getInstance().get(getTag()).applyTag(this);
	}
	
	public boolean isGroupPermanent() { 
		return getTimeGroup() == -1;
	}
	
	public List<Tag> getTags() { 
		List<Tag> tags = new ArrayList<>();
		for(Tag ta : TagManager.getInstance().getTags()) { 
			if(hasPermission(ta.getPermission()) || ta.getName().equals(getGroup().getName())) { 
				tags.add(ta);
			}
		}
		return tags;
	}
}
