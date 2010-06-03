package org.nnio.geocode;

/**
 * A service capable of returning geocoded addresses.
 */
public interface GeocodeService {

	GeocodeServiceResult geocode(String address);
	GeocodeServiceResult[] geocode(String[] addresses);
}
