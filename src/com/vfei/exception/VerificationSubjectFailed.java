package com.vfei.exception;

public class VerificationSubjectFailed extends Exception {

	private static final long serialVersionUID = 1L;
	String msg;

	public VerificationSubjectFailed(String msg) {
		super(msg);
		this.msg = msg;
	}

}
