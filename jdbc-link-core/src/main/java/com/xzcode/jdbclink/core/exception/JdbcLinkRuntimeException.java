package com.xzcode.jdbclink.core.exception;

public class JdbcLinkRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 8199140798951382056L;

	public JdbcLinkRuntimeException() {
		super();
	}

	public JdbcLinkRuntimeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public JdbcLinkRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public JdbcLinkRuntimeException(String message) {
		super(message);
	}

	public JdbcLinkRuntimeException(Throwable cause) {
		super(cause);
	}
	
	

}
