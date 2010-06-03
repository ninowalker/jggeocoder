package org.nnio.geocode.google;

import org.nnio.geocode.Coordinate;
import org.nnio.geocode.GeocodeException;
import org.nnio.geocode.GeocodeServiceResult;

public class GAccuracyException extends GeocodeException {

	public GAccuracyException(GeocodeServiceResult r, GGeoAddressAccuracy accuracyThreshold) {
		super("Accuracy " + accuracyThreshold + " > " + ((GPlacemark)r.getFirst()).getAccuracy(), r);
	}

}

