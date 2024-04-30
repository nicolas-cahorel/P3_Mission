package com.openclassrooms.tajmahal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import androidx.lifecycle.Observer;

import com.openclassrooms.tajmahal.data.repository.ReviewRepository;
import com.openclassrooms.tajmahal.data.service.RestaurantApi;
import com.openclassrooms.tajmahal.data.service.RestaurantFakeApi;
import com.openclassrooms.tajmahal.domain.model.Review;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a unit test for the AddReview functionality.
 * It executes on the development machine (host) and uses a mock API to simulate the server.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
@Config(application = TajMahalApplication.class,manifest = "src/main/AndroidManifest.xml")
public class AddReviewUnitTest {
    @Mock
    private RestaurantFakeApi fakeApi;
    private ReviewRepository reviewRepository;

    /**
     * Set up the test environment.
     * This method is called before each test method is executed.
     * It initializes a new ReviewRepository with the mock API.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        reviewRepository = new ReviewRepository(fakeApi);

        //List<Review> mockReviews = new ArrayList<>();
        // Ajoutez des données simulées à votre liste mockReviews
        //mockReviews.add(new Review("Ranjit Singh", "https://xsgames.co/randomusers/assets/avatars/male/71.jpg", "Service très rapide et nourriture délicieuse, nous mangeons ici chaque week-end, c'est très rapide et savoureux. Continuez ainsi!", 5));
        //mockReviews.add(new Review("Martyna Siddeswara", "https://xsgames.co/randomusers/assets/avatars/female/31.jpg", "Un service excellent et des plats incroyablement savoureux. Nous sommes vraiment satisfaits de notre expérience au restaurant.", 4));
        //mockReviews.add(new Review("Komala Alanazi", "https://xsgames.co/randomusers/assets/avatars/male/46.jpg", "La cuisine est délicieuse et le service est également excellent. Le propriétaire est très sympathique et veille toujours à ce que votre repas soit satisfaisant. Cet endroit est un choix sûr!", 5));
        //mockReviews.add(new Review("David John", "https://xsgames.co/randomusers/assets/avatars/male/67.jpg", "Les currys manquaient de diversité de saveurs et semblaient tous à base de tomates. Malgré les évaluations élevées que nous avons vues et nos attentes, nous avons été déçus.", 2));
        //mockReviews.add(new Review("Emilie Hood", "https://xsgames.co/randomusers/assets/avatars/female/20.jpg", "Très bon restaurant Indien ! Je recommande.", 4));
        // Initialisez votre ReviewRepository avec les données simulées
        //reviewRepository = new ReviewRepository(new RestaurantFakeApi(mockReviews));

    }

    /**
     * Creates a new instance of a Review object with the given parameters.
     *
     * @param author    The author of the review
     * @param avatarUrl The URL of the author's avatar
     * @param content   The content of the review
     * @param rating    The rating of the review
     * @return A new instance of a Review object
     */
    private Review createReview(String author, String avatarUrl, String content, Integer rating) {
        return new Review(author, avatarUrl, content, rating);
    }


    /**
     * This method checks if a the review list  is correctly updated with api data.
     */
    @Test
    public void isReviewListUpToDate() {

        // TEST 2
        //Créez une liste de Review pour le test
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review("Ranjit Singh", "https://xsgames.co/randomusers/assets/avatars/male/71.jpg", "Service très rapide et nourriture délicieuse, nous mangeons ici chaque week-end, c'est très rapide et savoureux. Continuez ainsi!", 5));
        reviews.add(new Review("Martyna Siddeswara", "https://xsgames.co/randomusers/assets/avatars/female/31.jpg", "Un service excellent et des plats incroyablement savoureux. Nous sommes vraiment satisfaits de notre expérience au restaurant.", 4));
        reviews.add(new Review("Komala Alanazi", "https://xsgames.co/randomusers/assets/avatars/male/46.jpg", "La cuisine est délicieuse et le service est également excellent. Le propriétaire est très sympathique et veille toujours à ce que votre repas soit satisfaisant. Cet endroit est un choix sûr!", 5));
        reviews.add(new Review("David John", "https://xsgames.co/randomusers/assets/avatars/male/67.jpg", "Les currys manquaient de diversité de saveurs et semblaient tous à base de tomates. Malgré les évaluations élevées que nous avons vues et nos attentes, nous avons été déçus.", 2));
        reviews.add(new Review("Emilie Hood", "https://xsgames.co/randomusers/assets/avatars/female/20.jpg", "Très bon restaurant Indien ! Je recommande.", 4));

        // Mock RestaurantApi pour qu'il renvoie la liste de Review lorsque getReviews() est appelé
        RestaurantApi restaurantApi = Mockito.mock(RestaurantApi.class);
        Mockito.when(restaurantApi.getReviews()).thenReturn(reviews);

        // Créez ReviewRepository avec le RestaurantApi mocké
        ReviewRepository reviewRepository = new ReviewRepository(restaurantApi);

        // Créez un Observer pour vérifier la valeur de LiveData
        Observer<List<Review>> observer = new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                Assert.assertEquals(5, reviews.size());
            }
        };

        // Observez liveDataReviews avec l'Observer
        reviewRepository.getReviews().observeForever(observer);

        // Vérifiez que la valeur initiale de liveDataReviews est correcte
        Assert.assertEquals(5, reviewRepository.getReviews().getValue().size());

        // Arrêtez d'observer liveDataReviews à la fin du test
        reviewRepository.getReviews().removeObserver(observer);


        //TEST 1
        // Verify that the review list is not null and not empty
        assertNotNull("The review list should not be null", reviews);
        assertFalse("The review list should not be empty", reviews.isEmpty());

        // Verify the size of the review list
        int reviewListSize = reviews.size();
        assertTrue("The review list should contain at least 2 objects", reviewListSize >= 2);




    }

    /**
     * This method checks if a new review is correctly added at the beginning of the list.
     */
    @Test
    public void newReviewFirst() {
        // Create a new review
        Review newReview = createReview("John Tester", "https://xsgames.co/randomusers/assets/avatars/male/2.jpg", "This is the content of the new review.", 1);

        // Add the review to the repository.
        reviewRepository.addReview(newReview);

        // Retrieves the review objects from the reviewRepository and stores them in the reviews variable.
        List<Review> reviews = reviewRepository.getReviews().getValue();



        // The assertEquals() method will throw an AssertionError if the review has not been added to the top of the list.
        assertEquals("The new review should be added at the beginning of the list", review, reviews.get(0));

    }


    /**
     * This method checks if a new review already exists in the list.
     */
    @Test
    public void newReviewAlreadyExist() {

        // Create a new review
        Review newReview = createReview("Ranjit Singh", "https://xsgames.co/randomusers/assets/avatars/male/71.jpg", "Service très rapide et nourriture délicieuse, nous mangeons ici chaque week-end, c'est très rapide et savoureux. Continuez ainsi!", 5);



        // Add the review to the repository.
        //reviewRepository.addReview(newReview);

        // Create a new review that already exists in the list
        //Review existingReview = reviews.get(0);

        // Verify that the review list is not null and not empty
        //assertNotNull("The review should not be null", existingReview);

        // Add the existing review to the repository.
        reviewRepository.addReview(newReview);

        // Retrieves the review objects from the reviewRepository and stores them in the reviews variable.
        //List<Review> firstAddReviews = reviewRepository.getReviews().getValue();

        // Add the existing review to the repository.
        reviewRepository.addReview(newReview);


        // Update the list of reviews from the reviewRepository.
        //List<Review> updatedReviews = reviewRepository.getReviews().getValue();

        // Retrieves the review objects from the reviewRepository and stores them in the reviews variable.
        List<Review> reviews = reviewRepository.getReviews().getValue();

        assert reviews != null;
        int numberOfReviews = reviews.size();
        // Verify that the review list is not null and not empty
        assertNotNull("The review list should not be null", reviews);
        assertFalse("The review list should not be empty", reviews.isEmpty());

        // Récupérer les objets Review aux index 0 et 1
        Review reviewAtIndex0 = reviews.get(0);
        //Review reviewAtIndex1 = reviews.get(1);

        // Verify that the review list is not null
        assertNotNull("The review list should not be null", reviewAtIndex0);

        // Verify that the review list is not null
        //assertNotNull("The review list should not be null", reviewAtIndex1);





        // Verify that the size of the list has not changed because the existing review should not have been added again.
        //assertNotEquals("The new review should not have been added to the list because it already exists.", reviewAtIndex0, reviewAtIndex1);
        //assertEquals("The new review should not have been added to the list because it already exists.", reviews.size(), updatedReviews.size());
    }

    /**
     * This method checks if the new review can be added without comment.
     */
    @Test
    public void newReviewCommentIsEmpty() {
        // Create a new review without content
        Review review = createReview("John Tester", "https://xsgames.co/randomusers/assets/avatars/male/2.jpg", "", 1);

        // Add the review to the repository.
        reviewRepository.addReview(review);

        // Retrieves the review objects from the reviewRepository and stores them in the reviews variable.
        List<Review> reviews = reviewRepository.getReviews().getValue();

        // The assert method will throw an AssertionError if the review list is null.
        assert reviews != null;

        // The assertEquals() method will throw an AssertionError if the review has been added.
        assertEquals("The new review without content should not be added", 5, reviews.size());
    }


    /**
     * This method checks if the new review can be added with the value rating<1.
     */
    @Test
    public void newReviewRate_isLessThan1() {
        // Create a new review with rate = 0.
        Review review = createReview("John Tester", "https://xsgames.co/randomusers/assets/avatars/male/2.jpg", "This is the content of the new review.", 0);

        // Add the review to the repository.
        reviewRepository.addReview(review);

        // Retrieves the review objects from the reviewRepository and stores them in the reviews variable.
        List<Review> reviews = reviewRepository.getReviews().getValue();

        // The assert method will throw an AssertionError if the review list is null.
        assert reviews != null;

        // The assertEquals() method will throw an AssertionError if the review has been added.
        Review firstReview = reviews.get(0);
        assertTrue("The rating should be 1  or greater for the new review to be added", firstReview.getRating() >= 1);
    }

    /**
     * This method checks if the new review can be added with the value rating>5
     */
    @Test
    public void newReviewRate_isMoreThan5() {
        // Create a new review with rate = 6.
        Review review = createReview("John Tester", "https://xsgames.co/randomusers/assets/avatars/male/2.jpg", "This is the content of the new review.", 6);

        // Add the review to the repository.
        reviewRepository.addReview(review);

        // Retrieves the review objects from the reviewRepository and stores them in the reviews variable.
        List<Review> reviews = reviewRepository.getReviews().getValue();

        // The assert method will throw an AssertionError if the review list is null.
        assert reviews != null;

        // The assertEquals() method will throw an AssertionError if the review has been added.
        Review firstReview = reviews.get(0);
        assertTrue("The rating should be 5 or lower for the new review to be added", firstReview.getRating() <= 5);
    }

}
