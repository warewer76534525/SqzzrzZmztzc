package com.squeromatic.model;

import java.io.Serializable;

public class CheckinCommand implements Serializable {
	
	private static final long serialVersionUID = -4611235156672770287L;
	private String token;
	private String venueId;

	public CheckinCommand(String token, String venueId) {
		this.token = token;
		this.venueId = venueId;
	}

	public CheckinCommand() {

	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setVenueId(String venueId) {
		this.venueId = venueId;
	}

	public String getToken() {
		return token;
	}

	public String getVenueId() {
		return venueId;
	}

	@Override
	public String toString() {
		return "CheckinCommand [token=" + token + ", venueId=" + venueId + "]";
	}

}
