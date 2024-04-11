package com.openclassrooms.tajmahal.ui.restaurant;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.data.repository.ReviewRepository;
import com.openclassrooms.tajmahal.domain.model.Restaurant;

import java.util.Calendar;
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



    /**
     * Fetches the list of details of the reviews that can be modified.
     */
    /**
    public LiveData<List<Review>> getReviews() {
        return reviewRepository.getReviews();
    }
    public LiveData<List<Integer>> getStarsCount() {
        return reviewRepository.getStarsCount();
    }
    public LiveData<float> getAverageRating() {
        return reviewRepository.getAverageRating();
    }
  */


    /**
     * Retrieves the current day of the week in French.
     *
     * @return A string representing the current day of the week in French.
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
    // starsCount contient une liste avec le nombre de review par étoiles
    MutableLiveData<List<Integer>> starsCount = new MutableLiveData<List<Integer>>();

    // averageRating contient le nombre moyen d'étoile arrondi au dixieme
    MutableLiveData<Float> averageRating = new MutableLiveData<Float>();



    public List<Integer> getTajMahalStarsCount(){
        // extraire les données de reviews et retourner les valeurs voulues
    }
    public Float getTajMahalAverageRating(){

    }


    /**
    public int getStarsCount() {
        List<Integer> StarsCount = null;
        return StarsCount;
    }
    

    public static class StarsCount {
        public StarsCount getStarsCount() {
            List<Review> reviews = reviewRepository.getReviews().getValue();
            List<Integer> starsCount = new ArrayList<>(Collections.nCopies(6, 0));
            if (reviews != null) {
                for (Review review : reviews) {
                    int rating = review.getRating();
                    if (rating >= 1 && rating <= 5) {
                        starsCount.set(rating - 1, starsCount.get(rating - 1) + 1);
                    }
                }
            }

            int totalRatings = 0;
            float totalWeight = 0;
            for (int i = 0; i < starsCount.size(); i++) {
                totalRatings += (i + 1) * starsCount.get(i);
                totalWeight += starsCount.get(i);
            }

            float averageRating;
            if (totalWeight == 0) {
                averageRating = 0;
            } else {
                averageRating = (float) totalRatings / totalWeight;
                averageRating = (float) Math.round(averageRating * 10) /10;

            }

            MutableLiveData<List<Integer>> starsCountLiveData = new MutableLiveData<>();
            starsCountLiveData.setValue(starsCount);

            MutableLiveData<Float> averageRatingLiveData = new MutableLiveData<>();
            averageRatingLiveData.setValue(averageRating);

        }

    }
    public static class AverageRating {

    }
     */




    /**
     * Initialization of a MutableLiveData array to store the number of reviews for each star rating.
     *     MutableLiveData<int[]> starsCount = new MutableLiveData<>(new int[6]);
     */


}


// MAJ de la valeur contenue dans le LiveData
// currentReview.postValue(reviews.get(1));

// Lecture du contenu du LiveData
// currentReview.getValue();