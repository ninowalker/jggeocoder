package org.nnio.geocode.google;

import java.util.EnumMap;
import java.util.HashMap;

/**
 * Enumerations of accuracy values returned by the Google Geocoder. See
 * http://www.google.com/apis/maps/documentation/reference.html
 * 
 */
public enum GGeoAddressAccuracy {
	UNKNOWN(0), COUNTRY(1), REGION(2), SUBREGION(3), TOWN(4), POSTCODE(5), STREET(
			6), INTERSECTION(7), ADDRESS(8);

	private final int level;

	/** Constructor */
	GGeoAddressAccuracy(int value) {
		this.level = value;
	}

	/** Verify that the accuracy is at least at or above a certain threshold. */
	public boolean atLeast(GGeoAddressAccuracy o) {
		return this.level >= o.level;
	}

	/** Map an int to an object. */
	public static GGeoAddressAccuracy map(int v) {
		return em.get(new Integer(v));
	}

	/* Init the map. */
	private static final HashMap<Integer, GGeoAddressAccuracy> em = new HashMap<Integer, GGeoAddressAccuracy>();
	static {
		for (GGeoAddressAccuracy code : GGeoAddressAccuracy.values()) {
			em.put(code.level, code);
		}
	}
}
