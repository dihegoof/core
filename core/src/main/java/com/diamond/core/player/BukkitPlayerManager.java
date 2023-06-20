package com.diamond.core.player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.diamond.core.Main;
import com.diamond.core.mysql.data.CoreQuerys;

import lombok.Getter;

public class BukkitPlayerManager  {
	
	@Getter
	static BukkitPlayerManager instance = new BukkitPlayerManager();
	static List<BukkitPlayer> storageBukkitPlayers = new ArrayList<>();
	
	public void add(BukkitPlayer bp) { 
		if(!storageBukkitPlayers.contains(bp)) { 
			storageBukkitPlayers.add(bp);
		}
	}
	
	public void remove(BukkitPlayer bp) { 
		if(storageBukkitPlayers.contains(bp)) { 
			storageBukkitPlayers.remove(bp);
		}
	}
	
	public void load() { 
		int amount = 0;
		try {
			PreparedStatement stmt = Main.getMysql().getConn().prepareStatement(CoreQuerys.ACCOUNT_SELECT_ALL.getQuery());;
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) { 
				add(new BukkitPlayer(UUID.fromString(rs.getString("uniqueid")), rs.getString("nickname"), rs.getString("address"), rs.getString("group"), rs.getString("tag"), rs.getLong("timegroup"), rs.getLong("joinin"), rs.getLong("lastsee"), Arrays.asList(rs.getString("permissions").replace("[", "").replace("]", ""))));
				amount++;
			}
			if(amount > 0) 
				Main.debug("Carregado " + amount + " conta(s)");
		} catch (Exception e) {
			Main.debug("Ocorreu um erro ao carregar as contas!");
		}
	}
	
	public void save() { 
		
	}
	
	public BukkitPlayer get(UUID uniqueId) { 
		for(BukkitPlayer bps : storageBukkitPlayers) { 
			if(bps.getUniqueId().equals(uniqueId)) { 
				return bps;
			}
		}
		return null;
	}
	
	public BukkitPlayer get(String nickname) { 
		for(BukkitPlayer bps : storageBukkitPlayers) { 
			if(bps.getName().equals(nickname)) { 
				return bps;
			}
		}
		return null;
	}
}
