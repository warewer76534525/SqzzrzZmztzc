package com.squeromatic.model.history;

import com.squeromatic.model.LocationCalculator;
import com.squeromatic.model.Venue;

public class VenueItem {
	private Venue venue;
	
	public Venue getVenue() {
		return venue;
	}

	public double distanceFrom(String langlat) {
		String[] component = langlat.split(",");
		double startLatitude = Double.parseDouble(component[0]);
		double startLongitude = Double.parseDouble(component[1]);
		return LocationCalculator.distance(startLatitude, startLongitude, venue.getLocation().getLat(), venue.getLocation().getLng());
	}
}
