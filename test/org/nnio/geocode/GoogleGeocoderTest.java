package org.nnio.geocode;

import org.nnio.geocode.GeocodeServiceResult;
import org.nnio.geocode.google.GGeoAddressAccuracy;
import org.nnio.geocode.google.GPlacemark;
import org.nnio.geocode.google.GoogleGeocoder;
import org.nnio.util.HttpCoreURLResolver;


import junit.framework.TestCase;

public class GoogleGeocoderTest extends TestCase {
	public GoogleGeocoderTest() {
		super();
	}
	
	private String myAPIKey = "ABQIAAAAQTYYFizKoRRLtN2WJKDAZBQ3hC7SPmelr57LoQ9v4g63WDeAzhRTSru0n6yKloFcXqKRLggcU";
	public void testGeocode() throws Exception {
		HttpCoreURLResolver resolver = new HttpCoreURLResolver();
		resolver.init();
		GoogleGeocoder geo = new GoogleGeocoder(resolver, myAPIKey);
		String addr = "1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA";
		GeocodeServiceResult r = geo.geocode(addr);
		assertTrue(r.toString(),!r.isError());
		assertTrue("One result " + r, r.getListings().size() == 1);
		GPlacemark p = (GPlacemark)r.getListings().get(0);
		assertEquals("X does not match", -122.083739, p.x);
		assertEquals("Y does not match", 37.423021, p.y);
		assertEquals("Accuracy <>", p.getAccuracy(), GGeoAddressAccuracy.ADDRESS);
		assertEquals("Address",p.getLocationName(),addr);
		assertNotNull(r.getQuery());
		
	}
	
	
}
