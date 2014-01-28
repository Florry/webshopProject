package se.jiv.webshop.exception;

import java.sql.SQLException;

public final class WebshopAppException extends Exception {

	private static final long serialVersionUID = 20140123L;
	
	private String className;
	private String actionName;

	public WebshopAppException(SQLException e, String className, String actionName) {
		super(e);
		this.className = className;
		this.actionName = actionName;
	}
	
	public WebshopAppException(String message, String className, String actionName) {
		super(message);
		this.className = className;
		this.actionName = actionName;
	}

	public String getActionName() {
		return actionName;
	}
	
	public String getClassName() {
		return className;
	}

}
