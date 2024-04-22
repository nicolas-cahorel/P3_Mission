package com.openclassrooms.tajmahal.ui.restaurant;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.data.repository.ReviewRepository;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * MainViewModel is responsible for preparing and managing the data for the {@link DetailsFragment}.
 * It communicates with the {@link RestaurantRepository} to fetch restaurant details and provides
 * utility methods related to the restaurant UI.
 * <p>
 * This ViewModel is integrated with Hilt for dependency injection.
 */
@HiltViewModel
public class DetailsViewModel extends ViewModel {

    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;

    /**
     * Constructor that Hilt will use to create an instance of MainViewModel.
     *
     * @param restaurantRepository The repository which will provide restaurant data.
     * @param reviewRepository     The repository which will provide review data.
     */
    @Inject
    public DetailsViewModel(RestaurantRepository restaurantRepository, ReviewRepository reviewRepository) {
        this.restaurantRepository = restaurantRepository;
        this.reviewRepository = reviewRepository;
    }

    /**
     * Fetches the details of the Taj Mahal restaurant.
     *
     * @return LiveData object containing the details of the Taj Mahal restaurant.
     */
    public LiveData<Restaurant> getTajMahalRestaurant() {
        return restaurantRepository.getRestaurant();
    }

    public LiveData<List<Review>> getTajMahalReviews() {
        return reviewRepository.getReviews();
    }


    /**
     * Retrieves the current day of the week in French.
     *
     * @return A string representing the current day of the week in French.
     */

    /**
     * Fetches the details of the Taj Mahal total ratings.
     *
     * @return LiveData object containing the details of the Taj Mahal total ratings.
     */
    public MutableLiveData<Integer> getTajMahalTotalRatings() {
        MutableLiveData<Integer> totalRatingsLiveData = new MutableLiveData<>();
        List<Review> reviews = reviewRepository.getReviews().getValue();
        int totalRatings = 0;
        // The total number of reviews is retrieved
        if (reviews != null) {
            totalRatings = reviews.size();
        }
        totalRatingsLiveData.setValue(totalRatings);
        return totalRatingsLiveData;
    }

    /**
     * Fetches the details of the Taj Mahal rating.
     *
     * @return LiveData object containing the details of the Taj Mahal rating.
     */
    public MutableLiveData<List<Integer>> getTajMahalRatingCount() {
        MutableLiveData<List<Integer>> ratingCountLiveData = new MutableLiveData<>();
        List<Review> reviews = reviewRepository.getReviews().getValue();
        List<Integer> ratingCount = new ArrayList<>(Collections.nCopies(5, 0));
        Integer totalRatings = getTajMahalTotalRatings().getValue();

        if (reviews != null) {
            // The list is iterated through
            for (Review review : reviews) {
                int rating = review.getRating();
                // For ratings between 1 and 5 stars, the corresponding counter is increased by 1
                if (rating >= 1 && rating <= 5) {
                    ratingCount.set(rating - 1, ratingCount.get(rating - 1) + 1);
                }
            }
        }
        // The list is iterated through
        for (int i = 0; i < ratingCount.size(); i++) {
            // The value is converted to a percentage for display purposes in the progress bars
            if (ratingCount.get(i) != 0 && totalRatings != null) {
                ratingCount.set(i, Math.round((float) ratingCount.get(i) / totalRatings * 100));
            }
        }
        ratingCountLiveData.setValue(ratingCount);
        return ratingCountLiveData;
    }

    /**
     * Fetches the details of the Taj Mahal average rating.
     *
     * @return LiveData object containing the details of the Taj Mahal average rating.
     */
    public MutableLiveData<Float> getTajMahalAverageRating() {
        MutableLiveData<Float> averageRatingLiveData = new MutableLiveData<>();
        List<Integer> ratingCount = getTajMahalRatingCount().getValue();
        float averageRating = 0;
        // The list is iterated through
        if (ratingCount != null) {
            for (int i = 0; i < ratingCount.size(); i++) {
                averageRating += (float) (i + 1) * ratingCount.get(i);
            }
            // Calculation of the weighted average of the reviews
            averageRating = (float) Math.round(averageRating / 100 * 10) / 10;
            averageRatingLiveData.setValue(averageRating);
        }
        return averageRatingLiveData;
    }

    /**
     * A MODIFIER
     * Fetches the details of the Taj Mahal restaurant.
     *
     * @return LiveData object containing the details of the Taj Mahal restaurant.
     */

    public String getCurrentDay(Context context) {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String dayString;

        switch (dayOfWeek) {
            case Calendar.MONDAY:
                dayString = context.getString(R.string.monday);
                break;
            case Calendar.TUESDAY:
                dayString = context.getString(R.string.tuesday);
                break;
            case Calendar.WEDNESDAY:
                dayString = context.getString(R.string.wednesday);
                break;
            case Calendar.THURSDAY:
                dayString = context.getString(R.string.thursday);
                break;
            case Calendar.FRIDAY:
                dayString = context.getString(R.string.friday);
                break;
            case Calendar.SATURDAY:
                dayString = context.getString(R.string.saturday);
                break;
            case Calendar.SUNDAY:
                dayString = context.getString(R.string.sunday);
                break;
            default:
                dayString = "";
        }
        return dayString;
    }

    public void addReview(Review review) {
        reviewRepository.addReview(review);
    }

}