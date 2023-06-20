package com.diamond.core.player.group;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.diamond.core.Main;
import com.diamond.core.mysql.data.CoreQuerys;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Group {
	
	String name;
	List<String> permissions;
	boolean temporary, defaulted;
	
	public Group(String name, List<String> permissions, boolean temporary, boolean defaulted) {
		this.name = name;
		this.permissions = permissions;
		this.temporary = temporary;
		this.defaulted = defaulted;
	}
	
	public void save() { 
		try {
			PreparedStatement stmt = Main.getMysql().getConn().prepareStatement(CoreQuerys.GROUP_SELECT.getQuery());
			stmt.setString(1, getName());
			if(exists()) { 
				PreparedStatement stmtUpdate = Main.getMysql().getConn().prepareStatement(CoreQuerys.GROUP_UPDATE.getQuery());
				stmtUpdate.setString(1, getPermissions().toString());
				stmtUpdate.setBoolean(2, isTemporary());
				stmtUpdate.setBoolean(3, isDefaulted());
				stmtUpdate.execute();
				stmtUpdate.close();
			} else { 
				PreparedStatement stmtInsert = Main.getMysql().getConn().prepareStatement(CoreQuerys.GROUP_INSERT.getQuery());
				stmtInsert.setString(1, getName());
				stmtInsert.setString(2, getPermissions().toString());
				stmtInsert.setBoolean(3, isTemporary());
				stmtInsert.setBoolean(4, isDefaulted());
				stmtInsert.execute();
				stmtInsert.close();
			}
		} catch (Exception e) {
			Main.debug(e.getMessage());
		}
	}
	
	public void delete() { 
		try {
			PreparedStatement stmt = Main.getMysql().getConn().prepareStatement(CoreQuerys.GROUP_DELETE.getQuery());
			stmt.setString(1, getName());
			stmt.close();
		} catch (Exception e) {
			Main.debug(e.getMessage());
		}
	}
	
	public boolean exists() {
		try {
			PreparedStatement stmt = Main.getMysql().getConn().prepareStatement(CoreQuerys.GROUP_SELECT.getQuery());
			stmt.setString(1, getName());
			ResultSet rs = stmt.executeQuery();
			return rs.next();
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean hasPermission(String permission) { 
		return getPermissions().contains(permission) || getPermissions().contains("*");
	}
}
