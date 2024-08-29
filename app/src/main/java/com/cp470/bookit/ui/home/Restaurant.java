package com.cp470.bookit.ui.home;

public class Restaurant {
	private String title;
	private String address;
	private String distance;
	private String priceIcon;
	private String priceRange;
	private String description;
	private String category;
	private int image;
	private String availableTimes;

	public Restaurant(String title, String address, String distance, String priceIcon, String priceRange, String description, String category, int image, String availableTimes) {
		this.title = title;
		this.address = address;
		this.distance = distance;
		this.priceIcon = priceIcon;
		this.priceRange = priceRange;
		this.description = description;
		this.category = category;
		this.image = image;
		this.availableTimes = availableTimes;
	}

	public String getTitle() {
		return title;
	}

	public String getAddress() {
		return address;
	}

	public String getDistance() {
		return distance;
	}

	public String getPriceIcon() {
		return priceIcon;
	}

	public String getPriceRange() {
		return priceRange;
	}

	public String getDescription() {
		return description;
	}

	public String getCategory() {
		return category;
	}

	public int getImage() {
		return image;
	}

	public String getAvailableTimes() {
		return availableTimes;
	}
}
