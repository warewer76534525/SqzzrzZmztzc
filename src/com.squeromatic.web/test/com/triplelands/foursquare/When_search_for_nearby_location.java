package com.triplelands.foursquare;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

import com.squeromatic.model.LocationCalculator;
import com.squeromatic.model.NearestLocation;
import com.squeromatic.model.Venue;
import com.squeromatic.service.FourSquareService;
import com.squeromatic.utils.HttpUtils;

public class When_search_for_nearby_location {
	
	@Test
	public void Should_get_nearby_location() throws IOException {
		FourSquareService api = new FourSquareService();
		String token = "GK4GD5UQ5VL4XH2Q5EGA4LCGIWOPS0UMJATIJCSLLNNFM041";
		String langlat = "-6.242864,106.856353";
		
		List<Venue> locations = api.getNearestLocations(token, langlat);
		
		for (Venue location : locations) {
			System.out.println(location.getId());
			System.out.println(location.getName());
			System.out.println(location.getLocation().getDistance());
			System.out.println(location.getLocation().getLat() + " " + location.getLocation().getLng());
			System.out.println();
		}
	}
	
	@Test
	public void Should_get_response_from_server() throws ClientProtocolException, IOException {
		HttpGet getRequest = new HttpGet("http://localhost");
		HttpClient httpclient = new DefaultHttpClient();
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String content = httpclient.execute(getRequest, responseHandler);
		System.out.println(content);
	}
	
	@Test
	public void Should_checkin_to_location() {
		FourSquareService api = new FourSquareService();
		String token = "GK4GD5UQ5VL4XH2Q5EGA4LCGIWOPS0UMJATIJCSLLNNFM041";
		String venueId = "4dd5333cfa7645a53ca9ebd1";
		
		api.checkinTo(token, venueId);
	}
	
	@Test
	public void Should_get_distance_between_location() {
		LocationCalculator firstLocation = new LocationCalculator();
		firstLocation.setLatitude(-6.242864);
		firstLocation.setLongitude(106.856353);
		
		LocationCalculator secondLocation = new LocationCalculator();
		secondLocation.setLatitude(-6.243348);
		secondLocation.setLongitude(106.856358);
		 
		System.out.println(firstLocation.distanceTo(secondLocation)); //53
	}
	
	@Test
	public void Should_get_user_history() {
		FourSquareService api = new FourSquareService();
		String token = "GK4GD5UQ5VL4XH2Q5EGA4LCGIWOPS0UMJATIJCSLLNNFM041";
		String langlat = "-6.242864,106.856353";
		
		List<NearestLocation> locations = api.getNearestHistoricalLocation(token, langlat);
		
		for (NearestLocation nearestLocation : locations) {
			System.out.println(nearestLocation.getId());
			System.out.println(nearestLocation.getName());
			System.out.println(nearestLocation.getDistance());
			System.out.println();
		}
	}
	
	@Test
	public void Should_get_response_from_url() {
		String token = "GK4GD5UQ5VL4XH2Q5EGA4LCGIWOPS0UMJATIJCSLLNNFM041";
		String venueHistoryUrl = String.format("https://api.foursquare.com/v2/users/self/venuehistory?oauth_token=%s", token);
		HttpUtils utils = new HttpUtils();
		String response = utils.get(venueHistoryUrl);
		System.out.println(response);
	}
	
	@Test
	public void Should_post_request_to() {
		String token = "GK4GD5UQ5VL4XH2Q5EGA4LCGIWOPS0UMJATIJCSLLNNFM041";
		String venueId = "4dd5377cd22d8e00fcdb4a6b";
		
		String checkinUrl = String.format("https://api.foursquare.com/v2/checkins/add?venueId=%s&broadcast=public,facebook,twitter&oauth_token=%s", venueId, token);
		
		HttpUtils utils = new HttpUtils();
		String response = utils.post(checkinUrl);
		System.out.println(response);
	}
}
