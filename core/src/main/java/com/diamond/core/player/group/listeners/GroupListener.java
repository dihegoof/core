package com.diamond.core.player.group.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.diamond.core.listeners.ServerTimerEvent;
import com.diamond.core.player.BukkitPlayer;
import com.diamond.core.player.BukkitPlayerManager;
import com.diamond.core.util.Utils;

public class GroupListener extends Utils implements Listener {
	
	@EventHandler
	public void onServerTimerEvent(ServerTimerEvent event) {
		for(BukkitPlayer bu : BukkitPlayerManager.getInstance().getAccounts()) { 
			if(!bu.isGroupPermanent() && bu.getTimeGroup() < System.currentTimeMillis()) { 
				if(bu.isOnline()) { 
					sendMessage(bu.getPlayer(), true, "§cSeu grupo temporário expirou!");
				}
				bu.setGroup(bu.getLastGroup());
				bu.setLastGroup("NRE");
				bu.setTimeGroup(-1);
			}
		}
	}
}
