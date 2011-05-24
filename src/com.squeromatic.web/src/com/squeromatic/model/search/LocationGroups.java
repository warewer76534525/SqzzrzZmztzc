package com.squeromatic.model.search;

import java.util.List;

import com.squeromatic.model.Venue;

public class LocationGroups {
	private String type;
	private String name;
	private List<Venue> items;

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public List<Venue> getItems() {
		return items;
	}

}
