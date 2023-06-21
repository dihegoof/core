package com.diamond.core.listeners.custom;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CustomEvent extends Event {
	
	private static HandlerList handlers = new HandlerList();

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
