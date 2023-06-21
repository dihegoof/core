package com.diamond.core.mysql.data;

import lombok.Getter;

@Getter
public enum DataValues {
	
	//NomeNaTabela,TipoDadoSql,ValorPadrao
	
	UNIQUE_ID("uniqueid", "VARCHAR(36)"),
	NICKNAME("nickname", "VARCHAR(16)"),
	GROUP("group", "VARCHAR(32)"),
	LAST_GROUP("lastgroup", "VARCHAR(32)"),
	TAG("tag", "VARCHAR(32)"),
	RANK("rank", "VARCHAR(32)"),
	PERMISSIONS("permissions", "VARCHAR(1000)"),
	XP("xp", "INT"),
	COINS("coins", "DOUBLE"),
	ADDRESS("address", "VARCHAR(64)"),
	JOIN_IN("joinin", "LONG"),
	LAST_SEE("lastsee", "LONG"),
	TIME_GROUP("timegroup", "LONG"),
	
	TAG_NAME("tagname", "VARCHAR(32)"),
	TAG_PREFIX("tagprefix", "VARCHAR(32)"),
	TAG_ORDER("tagorder", "INT"),
	TAG_EXCLUSIVE("tagexclusive", "BOOLEAN"),
	TAG_PERMISSION("tagpermission", "VARCHAR(32)"),

	GROUP_NAME("groupname", "VARCHAR(32)"),
	GROUP_PERMISSIONS("grouppermissions", "VARCHAR(1000)"),
	GROUP_EXCLUSIVE("groupexclusive", "BOOLEAN"),
	GROUP_DEFAULTED("groupdefaulted", "BOOLEAN"),
	
	;
	
	String nameData, typeData;

	private DataValues(String nameData, String typeData) {
		this.nameData = nameData;
		this.typeData = typeData;
	}
}
