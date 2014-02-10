package se.jiv.webshop.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public enum Properties {
	INSTANCE;

	private final java.util.Properties _prop;

	private Properties() {
		_prop = new java.util.Properties();
		try {
			_prop.load(new FileInputStream("config.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getDBDriver() {
		return _prop.getProperty("dbdriver");
	}

	public String getDB() {
		return _prop.getProperty("database");
	}

	public String getDBUser() {
		return _prop.getProperty("dbuser");
	}

	public String getDBPassword() {
		return _prop.getProperty("dbpassword");
	}

	public String getLogLevel() {
		return _prop.getProperty("loglevel");
	}

}
