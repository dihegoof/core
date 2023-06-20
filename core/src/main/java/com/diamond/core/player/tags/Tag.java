package com.diamond.core.player.tags;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.diamond.core.Main;
import com.diamond.core.mysql.data.CoreQuerys;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Tag {
	
	String name, prefix;
	int order;
	boolean exclusive;
	
	public Tag(String name, String prefix, int order, boolean exclusive) {
		this.name = name;
		this.prefix = prefix;
		this.order = order;
		this.exclusive = exclusive;
	}
	
	public void save() { 
		try {
			PreparedStatement stmt = Main.getMysql().getConn().prepareStatement(CoreQuerys.TAG_SELECT.getQuery());
			stmt.setString(1, getName());
			if(exists()) { 
				PreparedStatement stmtUpdate = Main.getMysql().getConn().prepareStatement(CoreQuerys.TAG_UPDATE.getQuery());
				stmtUpdate.setString(1, getPrefix());
				stmtUpdate.setInt(2, getOrder());
				stmtUpdate.setBoolean(3, isExclusive());
				stmtUpdate.execute();
				stmtUpdate.close();
			} else { 
				PreparedStatement stmtInsert = Main.getMysql().getConn().prepareStatement(CoreQuerys.TAG_INSERT.getQuery());
				stmtInsert.setString(1, getName());
				stmtInsert.setString(2, getPrefix());
				stmtInsert.setInt(3, getOrder());
				stmtInsert.setBoolean(4, isExclusive());
				stmtInsert.execute();
				stmtInsert.close();
			}
		} catch (Exception e) {
			Main.debug(e.getMessage());
		}
	}
	
	public void delete() { 
		try {
			PreparedStatement stmt = Main.getMysql().getConn().prepareStatement(CoreQuerys.TAG_DELETE.getQuery());
			stmt.setString(1, getName());
			stmt.close();
		} catch (Exception e) {
			Main.debug(e.getMessage());
		}
	}
	
	public boolean exists() {
		try {
			PreparedStatement stmt = Main.getMysql().getConn().prepareStatement(CoreQuerys.TAG_SELECT.getQuery());
			stmt.setString(1, getName());
			ResultSet rs = stmt.executeQuery();
			return rs.next();
		} catch (Exception e) {
			return false;
		}
	}
}
