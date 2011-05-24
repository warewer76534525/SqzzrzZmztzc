package com.squeromatic.model;

import com.squeromatic.model.history.VenueItem;

public class NearestLocation {
	private String id;
	private String name;
	private double distance;
	
	public NearestLocation(String id, String name, double distance) {	
		this.id = id;
		this.name = name;
		this.distance = distance;
	}

	public static NearestLocation from(VenueItem venueItem, String langlat) {
		return new NearestLocation(venueItem.getVenue().getId(), venueItem.getVenue().getName(), venueItem.distanceFrom(langlat));
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getDistance() {
		return distance;
	}

	@Override
	public String toString() {
		return "NearestLocation [id=" + id + ", name=" + name + ", distance="
				+ distance + "]";
	}

	
	
	
	
}
