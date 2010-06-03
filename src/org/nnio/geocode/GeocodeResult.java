package org.nnio.geocode;

/**
 * A coordinate with a location name associated.
 */
public class GeocodeResult extends Coordinate {
	private String mLocationName;
	
	public String getLocationName() {
		return mLocationName;
	}
	public void setLocationName(String address) {
		mLocationName = address;
	}
}
