package org.nnio.geocode.google;

import java.util.HashMap;

/**
 * List of server responses in a Google Geocoding result. See 
 * http://www.google.com/apis/maps/documentation/reference.html
 * for more details.
 */
public enum GServerStatus {
	G_GEO_SUCCESS(200), G_GEO_SERVER_ERROR(500), G_GEO_MISSING_ADDRESS(601), G_GEO_UNKNOWN_ADDRESS(
			602), G_UNAVAILABLE_ADDRESS(603), G_GEO_BAD_KEY(610);

	private final int value;
	
	/** Constructor */
	GServerStatus(int c) {
		this.value = c;
	}

	/** Returns true of the status indicates some form of error */
	boolean isAddressError() {
		return this.value >= G_GEO_MISSING_ADDRESS.value
				&& this.value != G_GEO_BAD_KEY.value;
	}

	/** Check equality with a raw int. */
	boolean equals(int i) {
		return this.value == i;
	}

	/** Map an int to an object */
	public static GServerStatus map(int v) {
		return em.get(new Integer(v));
	}

	private static final HashMap<Integer, GServerStatus> em = new HashMap<Integer, GServerStatus>();
	static {
		for (GServerStatus code : GServerStatus.values()) {
			em.put(code.value, code);
		}
	}
}
