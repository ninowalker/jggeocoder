package org.nnio.geocode;

/**
 * Error object capturing all forms of errors in the geocoding cycle.
 * 
 */
public class GeocodeError {
	/** The potential errors. */
	public enum Code {INTERNAL, CONNECTION, FAILED_LOOKUP, SERVER_ERROR}
	
	/** An associated trapped exception, if present. */
	private Exception mException = null;
	
	/** The type of error */
	private Code mCode = null;
	
	/** A human readable message. */
	private String mMessage = "";
	
	////////////// Methods
	
	public GeocodeError(Code code, String message, Exception e) {
		mCode = code;
		mMessage = message;
		mException = e;
	}

	public Code getCode() {
		return mCode;
	}

	public void setCode(Code code) {
		mCode = code;
	}

	public Exception getException() {
		return mException;
	}

	public void setException(Exception exception) {
		mException = exception;
	}

	public String getMessage() {
		return mMessage;
	}

	public void setMessage(String message) {
		mMessage = message;
	}
	
	public String toString() {
		return "Code: {" + mCode + "} Msg: {" + mMessage 
		+ "} Exception: {" + mException + "}";
	}
	
	public boolean isA(Code type) {
		return this.mCode == type;
	}
}
