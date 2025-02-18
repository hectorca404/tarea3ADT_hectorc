package com.luisdbb.tarea3AD2024base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

@Component
public class DBO4OConnection {

	@Value("${db4o.path}")
	private String dbPath;

	private static DBO4OConnection instance;
	private ObjectContainer db;

	private DBO4OConnection() {
	}

	public static DBO4OConnection getInstance() {
		if (instance == null) {
			instance = new DBO4OConnection();
		}
		return instance;
	}

	public ObjectContainer openDatabase() {
		if (db == null || db.ext().isClosed()) {
			db = Db4oEmbedded.openFile(dbPath);
		}
		return db;
	}

	public void closeDatabase() {
		if (db != null) {
			db.close();
			db = null;
		}
	}
}