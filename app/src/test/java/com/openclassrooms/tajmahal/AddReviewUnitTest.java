package com.openclassrooms.tajmahal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.openclassrooms.tajmahal.data.repository.ReviewRepository;
import com.openclassrooms.tajmahal.data.service.RestaurantFakeApi;
import com.openclassrooms.tajmahal.domain.model.Review;

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
import java.util.Objects;

/**
 * This class represents a unit test for the AddReview functionality.
 * It executes on the development machine (host) and uses a mock API to simulate the server.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
@Config(application = TajMahalApplication.class, manifest = "src/main/AndroidManifest.xml")
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

        //Créez une liste de Review pour le test
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review("Ranjit Singh", "https://xsgames.co/randomusers/assets/avatars/male/71.jpg", "Service très rapide et nourriture délicieuse, nous mangeons ici chaque week-end, c'est très rapide et savoureux. Continuez ainsi!", 5));
        reviews.add(new Review("Martyna Siddeswara", "https://xsgames.co/randomusers/assets/avatars/female/31.jpg", "Un service excellent et des plats incroyablement savoureux. Nous sommes vraiment satisfaits de notre expérience au restaurant.", 4));
        reviews.add(new Review("Komala Alanazi", "https://xsgames.co/randomusers/assets/avatars/male/46.jpg", "La cuisine est délicieuse et le service est également excellent. Le propriétaire est très sympathique et veille toujours à ce que votre repas soit satisfaisant. Cet endroit est un choix sûr!", 5));
        reviews.add(new Review("David John", "https://xsgames.co/randomusers/assets/avatars/male/67.jpg", "Les currys manquaient de diversité de saveurs et semblaient tous à base de tomates. Malgré les évaluations élevées que nous avons vues et nos attentes, nous avons été déçus.", 2));
        reviews.add(new Review("Emilie Hood", "https://xsgames.co/randomusers/assets/avatars/female/20.jpg", "Très bon restaurant Indien ! Je recommande.", 4));

        Mockito.when(fakeApi.getReviews()).thenReturn(reviews);
        reviewRepository = new ReviewRepository(fakeApi);
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

        // The assert method will throw an AssertionError if the review list is null.
        assert reviews != null;

        // The assertEquals() method will throw an AssertionError if the review has not been added to the top of the list.
        assertEquals("The new review should be added at the beginning of the list", newReview, reviews.get(0));

    }


    /**
     * This method checks if a new review already exists in the list.
     */
    @Test
    public void newReviewAlreadyExist() {

        // Create a new review
        Review newReview = createReview("Ranjit Singh", "https://xsgames.co/randomusers/assets/avatars/male/71.jpg", "Service très rapide et nourriture délicieuse, nous mangeons ici chaque week-end, c'est très rapide et savoureux. Continuez ainsi!", 5);

        // Add the existing review to the repository.
        reviewRepository.addReview(newReview);

        // Retrieves the review objects from the reviewRepository and stores them in the reviews variable.
        List<Review> reviews = reviewRepository.getReviews().getValue();

        // The assert method will throw an AssertionError if the review list is null.
        assert reviews != null;

        // Verify if the new review already exists in the list
        for (int i = 1; i < Objects.requireNonNull(reviews).size(); i++) {
            if (reviews.get(0).equals(reviews.get(i))) {
                fail("The new review already exist in the list, it should not have been added");
                break;
            }
        }
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
        String content = reviews.get(0).getContent();
        if (content == null || content.isEmpty()) {
            fail("The new review without content should not be added");
        }
    }

    /**
     * This method checks if the new review can be added with the value rating<1.
     */
    @Test
    public void newReviewRateUnder1() {
        // Create a new review with rate = 0.
        Review review = createReview("John Tester", "https://xsgames.co/randomusers/assets/avatars/male/2.jpg", "This is the content of the new review.", 0);

        // Add the review to the repository.
        reviewRepository.addReview(review);

        // Retrieves the review objects from the reviewRepository and stores them in the reviews variable.
        List<Review> reviews = reviewRepository.getReviews().getValue();

        // The assert method will throw an AssertionError if the review list is null.
        assert reviews != null;

        // The assertEquals() method will throw an AssertionError if the review has been added.
        assertTrue("The rating should be 1 or greater for the new review to be added", reviews.get(0).getRating() >= 1);
    }

    /**
     * This method checks if the new review can be added with the value rating>5
     */
    @Test
    public void newReviewRateOver5() {
        // Create a new review with rate = 6.
        Review review = createReview("John Tester", "https://xsgames.co/randomusers/assets/avatars/male/2.jpg", "This is the content of the new review.", 6);

        // Add the review to the repository.
        reviewRepository.addReview(review);

        // Retrieves the review objects from the reviewRepository and stores them in the reviews variable.
        List<Review> reviews = reviewRepository.getReviews().getValue();

        // The assert method will throw an AssertionError if the review list is null.
        assert reviews != null;

        // The assertEquals() method will throw an AssertionError if the review has been added.
        assertTrue("The rating should be 5 or lower for the new review to be added", reviews.get(0).getRating() <= 5);
    }
}
