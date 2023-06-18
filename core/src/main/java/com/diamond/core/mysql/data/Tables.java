package com.diamond.core.mysql.data;

import lombok.Getter;

@Getter
public enum Tables {
	
	ACCOUNTS("accounts", new DataValues[] { 
			DataValues.UNIQUEID,  
			DataValues.NICKNAME, 
			DataValues.ADDRESS, 
			DataValues.GROUP, 
			DataValues.TIMEGROUP, 
			DataValues.JOININ, 
			DataValues.LASTSEE, 
			DataValues.TAG, 
			DataValues.RANK, 
			DataValues.PERMISSIONS, 
			DataValues.XP, 
			DataValues.COINS, });

	String name;
	DataValues[] dataValues;

	private Tables(String name, DataValues[] dataValues) {
		this.name = name;
		this.dataValues = dataValues;
	}
	
	public String getText() { 
		StringBuilder stringBuilder = new StringBuilder();
		for(DataValues da : getDataValues()) {
			stringBuilder.append("`" + da.getNameData() + "` " + da.getTypeData() + ",");
		}
		return "CREATE TABLE IF NOT EXISTS `" + getName() + "` (" + stringBuilder.toString().substring(0, stringBuilder.length() - 1) + ");";
	}
}
