package org.nnio.geocode;

import java.util.LinkedList;
import java.util.List;

/**
 * The result of a call to a Geocoding Service.
 */
public class GeocodeServiceResult {
	/** The original query string. */
	private String mQuery = null;
	
	/** The error, if generated. */
	private GeocodeError mError = null;
	
	/** The list of results (potentially ambiguous). */
	private LinkedList<GeocodeResult> mListings = new LinkedList<GeocodeResult>();

	///////////////////// Constructors
	
	public GeocodeServiceResult() {}
	
	public GeocodeServiceResult(GeocodeError error) {
		mError = error;
	}
	
	///////////////////// Methods

	public boolean isError() { return mError != null; }
	
	public GeocodeError getError() {
		return mError;
	}
	public void setError(GeocodeError error) {
		mError = error;
	}
	public List<GeocodeResult> getListings() {
		return mListings;
	}
	
	public GeocodeResult getFirst() {
		return mListings.getFirst();
	}

	public String getQuery() {
		return mQuery;
	}

	public void setQuery(String query) {
		mQuery = query;
	}

	public String toString() {
		return "Error: {" + mError + "} Query: {" + mQuery 
		+ "} Listings: {" + mListings + "}";
	}
	
}
