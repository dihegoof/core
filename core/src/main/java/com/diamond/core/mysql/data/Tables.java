package com.diamond.core.mysql.data;

import lombok.Getter;

@Getter
public enum Tables {
	
	ACCOUNTS("accounts", new DataValues[] { 
			DataValues.UNIQUE_ID,  
			DataValues.NICKNAME, 
			DataValues.ADDRESS, 
			DataValues.GROUP, 
			DataValues.LAST_GROUP, 
			DataValues.TIME_GROUP, 
			DataValues.JOIN_IN, 
			DataValues.LAST_SEE, 
			DataValues.TAG, 
			DataValues.PERMISSIONS }),
	
	TAGS("tags", new DataValues[] {
			DataValues.TAG_NAME,
			DataValues.TAG_PREFIX,
			DataValues.TAG_ORDER,
			DataValues.TAG_EXCLUSIVE,
			DataValues.TAG_PERMISSION }),
	
	GROUPS("groups", new DataValues[] {
			DataValues.GROUP_NAME,
			DataValues.GROUP_PERMISSIONS,
			DataValues.GROUP_EXCLUSIVE,
			DataValues.GROUP_DEFAULTED })
	;

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
