package com.diamond.core;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.diamond.core.listeners.ServerTimerEvent;
import com.diamond.core.mysql.MySql;
import com.diamond.core.player.BukkitPlayerManager;
import com.diamond.core.player.group.GroupManager;
import com.diamond.core.player.tags.TagManager;

import lombok.Getter;

/**
 *
 * @author Dihego
 */

public class Main extends JavaPlugin {
	
	@Getter
	static Plugin plugin = null;
	@Getter
	static MySql mysql = null;
	static boolean debug = true;
	static String[] listeners = { "player.listener",
								  "player.tags.listener"
	};
	static String[] commands = { "commands", 
					             "player.tags.commands", 
	};
	
    @Override
    public void onEnable() {
    	plugin = this;
    	saveDefaultConfig();
    	debug("Plugin ativado com sucesso!");
    	mysql = new MySql(getConfig().getString("mysql.address"), getConfig().getString("mysql.user"), getConfig().getString("mysql.password"), getConfig().getString("mysql.database"));
    	mysql.open();
    	mysql.create();
    	BukkitPlayerManager.getInstance().load();
    	TagManager.getInstance().load();
    	GroupManager.getInstance().load();
    	for(String loaderListener : listeners) { 
			ClassGetter.getInstance().events("com.diamond.core." + loaderListener);
		}
		for(String loaderCommands : commands) { 
			ClassGetter.getInstance().commands("com.diamond.core." + loaderCommands);
		}
		new BukkitRunnable() {
			
			public void run() {
				Bukkit.getPluginManager().callEvent(new ServerTimerEvent());
			}
		}.runTaskTimer(getPlugin(), 20L, 20L);
    }

    @Override
    
    public void onDisable() {
    	debug("Plugin desativado com sucesso!");
    	TagManager.getInstance().save();
    	GroupManager.getInstance().save();
    	plugin = null;
    }
    
    public static void debug(String... messages) { 
    	if(!debug) return;
    	for(String lines : messages) {
    		Bukkit.getConsoleSender().sendMessage(lines);
    	}
    }
}
