package com.openclassrooms.tajmahal.data.repository;


import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.tajmahal.TajMahalApplication;
import com.openclassrooms.tajmahal.data.service.RestaurantApi;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * This is the repository class for managing review data. Repositories are responsible
 * for coordinating data operations from data sources such as network APIs, databases, etc.
 * <p>
 * In an Android app built with architecture components, the repository handles the logic for fetching, caching, and updating data.
 * This repository class manages the list of reviews for a restaurant, fetches the initial list of reviews from the provided {@link RestaurantApi},
 * and provides methods for adding new reviews to the list.
 *
 * @see Restaurant
 * @see RestaurantApi
 */
@Singleton
public class ReviewRepository {

    private final List<Review> localReviews;
    private final MutableLiveData<List<Review>> reviewsLiveData;

    /**
     * Constructs a new instance of {@link RestaurantRepository} with the given {@link RestaurantApi}.
     * <p>
     * This constructor initializes the local list of reviews and the LiveData object holding the list of reviews.
     * It also fetches the initial list of reviews from the provided {@link RestaurantApi} and stores it in the local list.
     *
     * @param restaurantApi The network API interface for fetching restaurant data.
     */
    @Inject
    public ReviewRepository(RestaurantApi restaurantApi) {
        if (restaurantApi == null) {
            throw new IllegalArgumentException("restaurantApi cannot be null");
        }
        // The API interface instance that will be used for network requests related to restaurant data.
        this.localReviews = new ArrayList<>();
        this.reviewsLiveData = new MutableLiveData<>();
        List<Review> reviewsFromApi = restaurantApi.getReviews();
        if (reviewsFromApi != null) {
            this.localReviews.addAll(reviewsFromApi);
            this.reviewsLiveData.setValue(this.localReviews);
        }
    }


    /**
     * Returns the list of reviews.
     * <p>
     * If the list of reviews has not been fetched yet, this method will make a network call
     * using the provided {@link RestaurantApi} instance to fetch review data and store it in a local list.
     * If the list of reviews has already been fetched, this method will return the local list.
     * Note that error handling and any transformations on the data would need to be managed.
     *
     * @return LiveData holding the list of reviews.
     */
    public LiveData<List<Review>> getReviews() {
        if (reviewsLiveData.getValue() == null) {
            reviewsLiveData.setValue(this.localReviews);
        }
        return reviewsLiveData;
    }

    /**
     * Adds a new review to the local list of reviews.
     *
     * @param review The new review to be added to the list.
     */
    public void addReview(Review review) {

        // obtain a valid reference to the Context object for the display of the Toast
        Context context = TajMahalApplication.getAppContext();

        // Check if the local list do not contain the new review
        if (!localReviews.contains(review)) {
            // Check if the new review contains a comment
            if (!review.getContent().isEmpty()) {
                // Check if the new reviews rate is between 1 and 5
                if (review.getRating() >= 1 && review.getRating() <= 5) {
                    // Add the new review to the top of the local list of reviews
                    this.localReviews.add(0, review);
                    // Update the LiveData object with the new list of reviews
                    reviewsLiveData.setValue(this.localReviews);
                } else {
                    Toast.makeText(context, "App Error : the new reviews rate should be between 1 and 5", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "App Error : the new reviews comment should not be empty", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "App Error : the new review already exists", Toast.LENGTH_SHORT).show();
        }
    }
}