package com.diamond.core.mysql.data;

import lombok.Getter;

@Getter
public enum CoreQuerys {
	
	ACCOUNT_SELECT("SELECT * FROM `accounts` WHERE `uniqueid`=?"),
	ACCOUNT_SELECT_ALL("SELECT * FROM `accounts`;"),
	ACCOUNT_INSERT("INSERT INTO `accounts` (`uniqueid`,`nickname`,`address`,`group`,`lastgroup`,`timegroup`,`joinin`,`lastsee`,`tag`,`permissions`) VALUES (?,?,?,?,?,?,?,?,?,?);"),
	ACCOUNT_UPDATE("UPDATE `accounts` SET `nickname`=?,`address`=?,`group`=?,`lastgroup`=?,`timegroup`=?,`lastsee`=?,`tag`=?,`permissions`=? WHERE `uniqueid`=?"),
	
	TAG_SELECT("SELECT * FROM `tags` WHERE `tagname`=?"),
	TAG_SELECT_ALL("SELECT * FROM `tags`;"),
	TAG_INSERT("INSERT INTO `tags` (`tagname`,`tagprefix`,`tagorder`,`tagexclusive`,`tagpermission`) VALUES (?,?,?,?,?);"),
	TAG_UPDATE("UPDATE `tags` SET `tagprefix`=?,`tagorder`=?,`tagexclusive`=?,`tagpermission`=? WHERE `tagname`=?"),
	TAG_DELETE("DELETE FROM `tags` WHERE `tagname`=?;"),
	
	GROUP_SELECT("SELECT * FROM `groups` WHERE `groupname`=?"),
	GROUP_SELECT_ALL("SELECT * FROM `groups`;"),
	GROUP_INSERT("INSERT INTO `groups` (`groupname`,`grouppermissions`,`groupexclusive`,`groupdefaulted`) VALUES (?,?,?,?);"),
	GROUP_UPDATE("UPDATE `groups` SET `grouppermissions`=?,`groupexclusive`=?,`groupdefaulted`=? WHERE `groupname`=?"),
	GROUP_DELETE("DELETE FROM `groups` WHERE `groupname`=?;");
	
	String query;

	private CoreQuerys(String query) {
		this.query = query;
	}
}
