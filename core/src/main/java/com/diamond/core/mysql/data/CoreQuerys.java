package com.diamond.core.mysql.data;

import lombok.Getter;

@Getter
public enum CoreQuerys {
	
	ACCOUNT_SELECT_NICKNAME("SELECT * FROM `accounts` WHERE `nickname`=?"),
	ACCOUNT_SELECT("SELECT * FROM `accounts` WHERE `uniqueid`=?"),
	ACCOUNT_SELECT_ALL("SELECT * FROM `accounts`;"),
	ACCOUNT_INSERT("INSERT INTO `accounts` (`uniqueid`,`nickname`,`address`,`group`,`timegroup`,`joinin`,`lastsee`,`tag`,`rank`,`permissions`,`xp`,`coins`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);"),
	ACCOUNT_UPDATE("UPDATE `accounts` SET `nickname`=?,`address`=?,`group`=?,`timegroup`=?,`lastsee`=?,`tag`=?,`rank`=?,`permissions`=?,`xp`=?,`coins`=? WHERE `uniqueid`=?");
	
	String query;

	private CoreQuerys(String query) {
		this.query = query;
	}
}
