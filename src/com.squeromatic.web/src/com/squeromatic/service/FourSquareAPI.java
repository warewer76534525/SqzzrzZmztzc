package com.squeromatic.service;

import com.squeromatic.utils.HttpUtils;

public class FourSquareAPI {
	private HttpUtils httpUtils = new HttpUtils();
	
	public String searchVenues(String token, String langlat) {
		String searchVenuesUrl = String.format("https://api.foursquare.com/v2/venues/search?ll=%s&oauth_token=%s", langlat, token);
		return httpUtils.get(searchVenuesUrl);
	}

	public void checkinTo(String token, String venueId) {
		String checkinUrl = String.format("https://api.foursquare.com/v2/checkins/add?venueId=%s&broadcast=public,facebook,twitter&oauth_token=%s", venueId, token);
		httpUtils.post(checkinUrl);
	}

	public String getHistoricalLocation(String token, String langlat) {
		String venueHistoryUrl = String.format("https://api.foursquare.com/v2/users/self/venuehistory?oauth_token=%s", token);
		return httpUtils.get(venueHistoryUrl);
	}
}
