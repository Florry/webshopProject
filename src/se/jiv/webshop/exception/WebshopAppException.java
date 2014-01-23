package se.jiv.webshop.exception;

public final class WebshopAppException extends Exception {

	private static final long serialVersionUID = 20140123L;

	private String actionName;

	public WebshopAppException(String message, String actionName) {
		super(message);
		this.actionName = actionName;
	}

	public String getActionName() {
		return actionName;
	}

}
