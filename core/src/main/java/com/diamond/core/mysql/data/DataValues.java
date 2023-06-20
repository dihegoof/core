package com.diamond.core.mysql.data;

import lombok.Getter;

@Getter
public enum DataValues {
	
	//NomeNaTabela,TipoDadoSql,ValorPadrao
	
	UNIQUE_ID("uniqueid", "VARCHAR(36)", "NRE"),
	NICKNAME("nickname", "VARCHAR(16)", "NRE"),
	GROUP("group", "VARCHAR(32)", "MEMBRO"),
	TAG("tag", "VARCHAR(32)", "MEMBRO"),
	RANK("rank", "VARCHAR(32)", "UNRANKED"),
	PERMISSIONS("permissions", "VARCHAR(1000)", "NRE"),
	XP("xp", "INT", "0"),
	COINS("coins", "DOUBLE", "0.0"),
	ADDRESS("address", "VARCHAR(64)", "127.0.0.1"),
	JOIN_IN("joinin", "LONG", "-1"),
	LAST_SEE("lastsee", "LONG", "-1"),
	TIME_GROUP("timegroup", "LONG", "-1"),
	
	TAG_NAME("tagname", "VARCHAR(32)", "NRE"),
	TAG_PREFIX("tagprefix", "VARCHAR(32)", "NRE"),
	TAG_ORDER("tagorder", "INT", "1"),
	TAG_EXCLUSIVE("tagexclusive", "BOOLEAN", "0"),

	GROUP_NAME("groupname", "VARCHAR(32)", "NRE"),
	GROUP_PERMISSIONS("grouppermissions", "VARCHAR(1000)", "NRE"),
	GROUP_EXCLUSIVE("groupexclusive", "BOOLEAN", "0"),
	GROUP_DEFAULTED("groupdefaulted", "BOOLEAN", "0"),
	
	;
	
	String nameData, typeData, valueDefault;

	private DataValues(String nameData, String typeData, String valueDefault) {
		this.nameData = nameData;
		this.typeData = typeData;
		this.valueDefault = valueDefault;
	}
}
