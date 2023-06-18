package com.diamond.core.player.ranking;

import lombok.Getter;

@Getter
public enum Rank {
	
	UNRANKED("UNRANKED", "-", 0, "§f", 14), 
	PRIMARY("Primary", "☰", 1500, "§a", 13), 
	ADVANCED("Advanced", "☲", 3500, "§e", 12), 
	EXPERT("Expert", "☷", 6000, "§1", 11), 
	SILVER("Silver", "✶", 9000, "§7", 10), 
	GOLD("Gold", "✳", 13000, "§6", 9), 
	DIAMOND("Diamond", "✦", 17000, "§b", 8), 
	EMERALD("Emerald", "✥", 22000, "§2", 7), 
	CRYSTAL("Crystal", "❉", 27000, "§9", 6), 
	SAPPHIRE("Sapphire", "❁", 32000, "§3", 5), 
	ELITE("Elite", "✹", 37000, "§5", 4), 
	MASTER("Master", "✫", 42000, "§c", 3), 
	LEGENDARY("Legendary", "✪", 50000, "§4", 2);
	
	String name, symbol, color;
	int id;
	int xpNumber, order;
	
	private Rank(String name, String symbol, int xpNumber, String color, int order) {
		this.name = name;
		this.symbol = symbol;
		this.color = color;
		this.xpNumber = xpNumber;
		this.order = order;
	}
}
