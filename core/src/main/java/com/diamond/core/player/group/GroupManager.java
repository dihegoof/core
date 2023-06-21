package com.diamond.core.player.group;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.diamond.core.Main;
import com.diamond.core.mysql.data.CoreQuerys;

import lombok.Getter;

public class GroupManager {

	@Getter
	static GroupManager instance = new GroupManager();
	static List<Group> storageGroups = new ArrayList<>();
	
	public void add(Group group) { 
		if(!storageGroups.contains(group)) { 
			storageGroups.add(group);
		}
	}
	
	public void remove(Group group) { 
		if(storageGroups.contains(group)) { 
			storageGroups.remove(group);
		}
	}
	
	public void load() {
		int amount = 0;
		try {
			PreparedStatement stmt = Main.getMysql().getConn().prepareStatement(CoreQuerys.GROUP_SELECT_ALL.getQuery());
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) { 
				add(new Group(rs.getString("groupname"), Arrays.asList(rs.getString("grouppermissions").replace("[", "").replace("]", "")), rs.getBoolean("groupexclusive"), rs.getBoolean("groupdefaulted")));
				amount++;
			}
			if(amount > 0) { 
				Main.debug("Carregado " + amount + " grupo(s)");
			} else { 
				add(new Group("Membro", new ArrayList<>(), false, true));
			}
		} catch (Exception e) {
			Main.debug("Ocorreu um erro ao carregar os grupos!");
		}
	}
	
	public void save() { 
		for(Group gr : storageGroups) { 
			gr.save();
		}
	}

	public Group get(String name) {
		for(Group gr : storageGroups) { 
			if(gr.getName().equals(name)) { 
				return gr;
			}
		}
		return null;
	}
	
	public Group defaultGroup() { 
		if(!storageGroups.isEmpty()) { 
			for(Group gr : storageGroups) { 
				if(gr.isDefaulted()) {
					return gr;
				}
			}
		}
		return null;
	}
	
	public List<Group> getGroups() {
		return storageGroups;
	}
}
