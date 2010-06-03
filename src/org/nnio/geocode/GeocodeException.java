package org.nnio.geocode;

public class GeocodeException extends Exception {
	protected GeocodeServiceResult mResult;
	public GeocodeException(GeocodeServiceResult r) {
		this(r.toString(), r);
	}
	public GeocodeException(String s, GeocodeServiceResult r) {
		super(s);
		mResult = r;
	}
		
	
	public GeocodeServiceResult getResult() {
		return mResult;
	}
}
