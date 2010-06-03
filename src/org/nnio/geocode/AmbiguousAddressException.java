package org.nnio.geocode;

public class AmbiguousAddressException extends GeocodeException {
	
	public AmbiguousAddressException(GeocodeServiceResult r) {
		super(r);
	}

}
