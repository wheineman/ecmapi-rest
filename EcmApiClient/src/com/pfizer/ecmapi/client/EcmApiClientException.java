package com.pfizer.ecmapi.client;

public class EcmApiClientException extends Exception {
	private static final long serialVersionUID = -8678601963374400602L;

	public EcmApiClientException(String message) {
		super(message);
	}

	public EcmApiClientException(Throwable cause) {
		super(cause);
	}

	public EcmApiClientException(String message, Throwable cause) {
		super(message, cause);
	}

}
