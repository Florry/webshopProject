package se.jiv.webshop.ui;

import se.jiv.webshop.exception.WebshopAppException;

public final class ExceptionUI {
	public static void printException(WebshopAppException e){
		System.err.println("Exception: " +e.getClassName()+"." + e.getActionName() + ": " + e.getMessage());
	}
}
