package com.diamond.core.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import com.diamond.core.Main;
import com.diamond.core.mysql.data.Tables;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MySql {
	
	String address, user, password, database;
	Connection conn;

	public MySql(String address, String user, String password, String database) {
		this.address = address;
		this.user = user;
		this.password = password;
		this.database = database;
	}
	
	public void open() {
		try {
			Connection conexao = DriverManager.getConnection("jdbc:mysql://" + getAddress() + ":3306/" + getDatabase(), getUser(), getPassword());
			if(conn == null) {
				this.conn = conexao;
				Main.debug("Conectado com sucesso!");
			}
		} catch (Exception e) {
			Main.debug("Erro de conexão!");
		}
	}
	
	public void close() { 
		try {
			if(conn != null) {
				this.conn.close();
			}
		} catch (Exception e) {
		}
	}
	
	public void create()  {
		try {
			Statement stmt = null;
			for(Tables ta : Tables.values()) { 
				Main.debug(ta.getText());
				stmt = getConn().prepareStatement(ta.getText());
				stmt.executeUpdate(ta.getText());
			}
		} catch (Exception e) {
			Main.debug("Erro ao criar tabelas!");
		}
	}
}
