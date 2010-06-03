package org.nnio.util;

import junit.framework.TestCase;
import java.net.URL;

public class HttpCoreURLResolverTest extends TestCase {
	public void testGoogleMapResolver() throws Exception {
		HttpCoreURLResolver r = new HttpCoreURLResolver();
		r.init();
		String baseurl = "http://maps.google.com";
		String file1 = "/maps/geo?q=1600+Amphitheatre+Parkway,+Mountain+View,+CA&output=json&key=ABQIAAAAQTYYFizKoRRLtN2WJKDAZBT2yXp_ZAY8_ufC3CFXhHIE1NvwkxQwlaVq_F-QZIzIYlcngHI5QrLW_g";
		String body1 = "{\"name\":\"1600 Amphitheatre Parkway, Mountain View, CA\",\"Status\":{\"code\":200,\"request\":\"geocode\"},\"Placemark\":[{\"address\":\"1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA\",\"AddressDetails\":{\"Country\":{\"CountryNameCode\":\"US\",\"AdministrativeArea\":{\"AdministrativeAreaName\":\"CA\",\"SubAdministrativeArea\":{\"SubAdministrativeAreaName\":\"Santa Clara\",\"Locality\":{\"LocalityName\":\"Mountain View\",\"Thoroughfare\":{\"ThoroughfareName\":\"1600 Amphitheatre Pkwy\"},\"PostalCode\":{\"PostalCodeNumber\":\"94043\"}}}}},\"Accuracy\": 8},\"Point\":{\"coordinates\":[-122.083739,37.423021,0]}}]}";
		assertEquals("Text should equal", body1, r.fetch(new URL(baseurl+ file1)));
		String file2 = "/maps/geo?q=21+Winfield+St,San+Francisco,+CA&output=json&key=ABQIAAAAQTYYFizKoRRLtN2WJKDAZBT2yXp_ZAY8_ufC3CFXhHIE1NvwkxQwlaVq_F-QZIzIYlcngHI5QrLW_g";
		String body2 = "{\"name\":\"21 Winfield St,San Francisco, CA\",\"Status\":{\"code\":200,\"request\":\"geocode\"},\"Placemark\":[{\"address\":\"21 Winfield St, San Francisco, CA 94110, USA\",\"AddressDetails\":{\"Country\":{\"CountryNameCode\":\"US\",\"AdministrativeArea\":{\"AdministrativeAreaName\":\"CA\",\"SubAdministrativeArea\":{\"SubAdministrativeAreaName\":\"San Francisco\",\"Locality\":{\"LocalityName\":\"San Francisco\",\"Thoroughfare\":{\"ThoroughfareName\":\"21 Winfield St\"},\"PostalCode\":{\"PostalCodeNumber\":\"94110\"}}}}},\"Accuracy\": 8},\"Point\":{\"coordinates\":[-122.416979,37.745047,0]}}]}";
		assertEquals("Text should equal", body2, r.fetch(new URL(baseurl+file2)));		
		String[] batch = r.fetch("maps.google.com", 80, new String[]{file1,file2});
		assertNotNull(batch);
		assertEquals("Body1 not equal", body1, batch[0]);
		assertEquals("Body2 not equal", body2, batch[1]);
	}
}
