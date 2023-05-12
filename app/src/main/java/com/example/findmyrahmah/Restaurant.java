package com.example.findmyrahmah;

public class Restaurant {
    private final String restaurantName;
    private final String restaurantAddress;
    private final Double restaurantRating;


    public Restaurant(String restaurantName, String restaurantAddress, Double restaurantRating) {
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.restaurantRating = restaurantRating;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public Double getRestaurantRating() {
        return restaurantRating;
    }
}
