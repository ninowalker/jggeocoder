package org.nnio.geocode.google;

import org.nnio.geocode.GeocodeResult;

/**
 * A result object that includes the additional details provided by the Google
 * Geocoding Service.
 */
public class GPlacemark extends GeocodeResult {
	/** The full details of the address. */
	private String mAddressDetails;

	/** Accuracy of the result */
	private GGeoAddressAccuracy mAccuracy;

	public GGeoAddressAccuracy getAccuracy() {
		return mAccuracy;
	}

	public void setAccuracy(GGeoAddressAccuracy accuracy) {
		mAccuracy = accuracy;
	}

	public String getAddressDetails() {
		return mAddressDetails;
	}

	public void setAddressDetails(String addressDetails) {
		mAddressDetails = addressDetails;
	}

}
