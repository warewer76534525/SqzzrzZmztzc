package com.squeromatic.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.squeromatic.model.NearestLocation;
import com.squeromatic.model.Venue;
import com.squeromatic.model.history.VenueHistoryResponse;
import com.squeromatic.model.history.VenueItem;
import com.squeromatic.model.search.LocationGroups;
import com.squeromatic.model.search.VenueSearchResponse;

@Service
public class FourSquareService {
	private Gson gson = new Gson();
	private FourSquareAPI fourSquareApi = new FourSquareAPI();
	
	public List<Venue> getNearestLocations(String token, String langlat) {
		String contents = fourSquareApi.searchVenues(token, langlat);
//		String contents = "";
//		try {
//			contents = FileUtils.readFileToString(new File("foursquare.txt"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		VenueSearchResponse vsResponse = gson.fromJson(contents, VenueSearchResponse.class);
		List<LocationGroups> locGroupsList = vsResponse.getResponse().getGroups();
		
		List<Venue> locations = new ArrayList<Venue>();
		
		for (LocationGroups location : locGroupsList) {
			locations.addAll(location.getItems());
		}
		
		Collections.sort(locations, new NearestComparator());
		
		return locations;
	}
	
	private static class NearestComparator implements Comparator<Venue> {
		@Override
		public int compare(Venue first, Venue second) {
			return (int) (first.getLocation().getDistance() - second.getLocation().getDistance());
		}		
	}

	public void checkinTo(String token, String venueId) {
		fourSquareApi.checkinTo(token, venueId);
	}

	public List<NearestLocation> getNearestHistoricalLocation(String token, String langlat) {
		String contents = fourSquareApi.getHistoricalLocation(token, langlat);
//		String contents = "";
//		try {
//			contents = FileUtils.readFileToString(new File("foursquare-history.txt"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		VenueHistoryResponse vsResponse = gson.fromJson(contents, VenueHistoryResponse.class);
		List<VenueItem> venues = vsResponse.getResponse().getVenues().getItems();
		
		List<NearestLocation> nearestLocation = new ArrayList<NearestLocation>();
		
		for (VenueItem venueItem : venues) {
			NearestLocation location = NearestLocation.from(venueItem, langlat);
			
			if (location.getDistance() > 1000) continue;
			
			nearestLocation.add(location);
		}
		
		Collections.sort(nearestLocation, new NearestComparator2());
		
		return nearestLocation;
	}
	
	private static class NearestComparator2 implements Comparator<NearestLocation> {
		@Override
		public int compare(NearestLocation first, NearestLocation second) {
			return (int) (first.getDistance() - second.getDistance());
		}		
	}
}
