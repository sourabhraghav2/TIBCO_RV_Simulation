package com.vfei.exception;

public class IncorrectExpressionException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String msg;

	public IncorrectExpressionException(String msg) {
		super(msg);
		this.msg = msg;
	}

}
