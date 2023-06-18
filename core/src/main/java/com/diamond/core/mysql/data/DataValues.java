package com.diamond.core.mysql.data;

import lombok.Getter;

@Getter
public enum DataValues {
	
	//NomeNaTabela,TipoDadoSql,ValorPadrao
	
	UNIQUEID("uniqueid", "VARCHAR(36)", "NRE"),
	NICKNAME("nickname", "VARCHAR(16)", "NRE"),
	GROUP("group", "VARCHAR(32)", "MEMBRO"),
	TAG("tag", "VARCHAR(32)", "MEMBRO"),
	RANK("rank", "VARCHAR(32)", "UNRANKED"),
	PERMISSIONS("permissions", "VARCHAR(1000)", "NRE"),
	XP("xp", "INT", "0"),
	COINS("coins", "DOUBLE", "0.0"),
	ADDRESS("address", "VARCHAR(64)", "127.0.0.1"),
	JOININ("joinin", "LONG", "-1"),
	LASTSEE("lastsee", "LONG", "-1"),
	TIMEGROUP("timegroup", "LONG", "-1"),
	
	;
	
	String nameData, typeData, valueDefault;

	private DataValues(String nameData, String typeData, String valueDefault) {
		this.nameData = nameData;
		this.typeData = typeData;
		this.valueDefault = valueDefault;
	}
}
