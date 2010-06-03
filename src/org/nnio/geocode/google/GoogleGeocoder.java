package org.nnio.geocode.google;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.nnio.geocode.GeocodeError;
import org.nnio.geocode.GeocodeService;
import org.nnio.geocode.GeocodeServiceResult;
import org.nnio.geocode.Projection;
import org.nnio.geocode.GeocodeError.Code;
import org.nnio.util.URLResolver;


public class GoogleGeocoder implements GeocodeService {
		
	private String mAPIKey;
	private String mDomain = "maps.google.com";
	private String mResource = "maps/geo";
	private String mOutputFormat = "json";
	private String mFormatString = "%s";
	private String mEncoding = "UTF-8";
	private int mPort = 80;
	private URLResolver mURLResolver = null;
	
	public GoogleGeocoder(URLResolver urlr, String apikey) {
		mAPIKey = apikey;
		mURLResolver = urlr;
	}
	
	private String baseurl() {
		return "http://" + mDomain;
	}
	
	private String queryurl(String q) throws UnsupportedEncodingException {
		return "/" + mResource + "?q=" + URLEncoder.encode(q,mEncoding)
			+ "&output=" + mOutputFormat
			+ "&key=" +URLEncoder.encode(mAPIKey,mEncoding); 
	}
	
	public GeocodeServiceResult geocode(String location) {
		String url;
		try {
			url = baseurl() + queryurl(location);
		} catch (UnsupportedEncodingException e) {
			return raiseError(GeocodeError.Code.INTERNAL,"Failed to build URL",e);			
		}
		String data;
		try {
			data = openurl(url);
		} catch (IOException e) {
			return raiseError(GeocodeError.Code.CONNECTION, null,e);
		}
		if (mOutputFormat.equals("json")) {
			GeocodeServiceResult res = parsejson(data);
			res.setQuery(url);
			return res;
		}
		throw new UnsupportedOperationException("Don't know how to parse " + mOutputFormat);
	}

	public GeocodeServiceResult[] geocode(String[] locations) {
		int size = locations.length;
		String[] files = new String[size];
		GeocodeServiceResult[] results = new GeocodeServiceResult[size];
		try {
			for (int i = 0; i < size; i++) {
				files[i] = queryurl(locations[i]);
			}
		} catch (UnsupportedEncodingException e) {
			return new GeocodeServiceResult[]{raiseError(GeocodeError.Code.INTERNAL,"Failed to build URL",e)};			
		}

		String[] data = new String[size];
		try {
			data = mURLResolver.fetch(mDomain, mPort, files);
		} catch (IOException e) {
			return new GeocodeServiceResult[]{raiseError(GeocodeError.Code.CONNECTION, null,e)};
		}
		for (int i = 0; i < size; i++) {
			if (mOutputFormat.equals("json")) {
				results[i] = parsejson(data[i]);
				results[i].setQuery(baseurl() + files[i]);
			} else {
				throw new UnsupportedOperationException("Don't know how to parse " + mOutputFormat);
			}
		}
		return results;
	}
	
	private GeocodeServiceResult parsejson(String page) {
		JSONObject jo;
		try {
			jo = new JSONObject(page);
			
			int status = jo.getJSONObject("Status").getInt("code");
			if (!GServerStatus.G_GEO_SUCCESS.equals(status)) {
				return raiseError(GeocodeError.Code.SERVER_ERROR, "Serverside error: " + status, null);
			}
			JSONArray pms = jo.getJSONArray("Placemark");
			GeocodeServiceResult gsr = new GeocodeServiceResult();
			for (int i = 0; i < pms.length(); i++) {
				JSONObject pm = pms.getJSONObject(i);
				if (pm == null) continue;
				GPlacemark gp = new GPlacemark();
				JSONArray coords = pm.getJSONObject("Point").getJSONArray("coordinates");
				//gp.proj = Projection.GEOGRAPHIC;
				gp.x = coords.getDouble(0);
				gp.y = coords.getDouble(1);
				JSONObject adetails = pm.getJSONObject("AddressDetails");
				gp.setAddressDetails(adetails.toString());
				gp.setLocationName(pm.getString("address"));
				gp.setAccuracy(GGeoAddressAccuracy.map(adetails.getInt("Accuracy")));
				gsr.getListings().add(gp);
			}
			return gsr;
		} catch (JSONException e) {
			return raiseError(GeocodeError.Code.INTERNAL,"Failed to parse JSON; " + page, e);			
		}
	}

	private GeocodeServiceResult raiseError(GeocodeError.Code code, String msg, Exception e) {
		return new GeocodeServiceResult(new GeocodeError(code, msg,e));
	}
	
	private String openurl(String url) throws IOException {
		return this.mURLResolver.fetch(new java.net.URL(url));
		//return "{\"name\":\"1600 Amphitheatre Parkway, Mountain View, CA\",\"Status\":{\"code\":200,\"request\":\"geocode\"},\"Placemark\":[{\"address\":\"1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA\",\"AddressDetails\":{\"Country\":{\"CountryNameCode\":\"US\",\"AdministrativeArea\":{\"AdministrativeAreaName\":\"CA\",\"SubAdministrativeArea\":{\"SubAdministrativeAreaName\":\"Santa Clara\",\"Locality\":{\"LocalityName\":\"Mountain View\",\"Thoroughfare\":{\"ThoroughfareName\":\"1600 Amphitheatre Pkwy\"},\"PostalCode\":{\"PostalCodeNumber\":\"94043\"}}}}},\"Accuracy\": 8},\"Point\":{\"coordinates\":[-122.083739,37.423021,0]}}]}";
	}


}
