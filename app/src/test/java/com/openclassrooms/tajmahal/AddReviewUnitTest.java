package com.openclassrooms.tajmahal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.openclassrooms.tajmahal.data.repository.ReviewRepository;
import com.openclassrooms.tajmahal.data.service.RestaurantFakeApi;
import com.openclassrooms.tajmahal.domain.model.Review;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

/**
 * This class represents a unit test for the AddReview functionality.
 * It executes on the development machine (host) and uses a mock API to simulate the server.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml")
public class AddReviewUnitTest {
    @Mock
    private RestaurantFakeApi api;

    private ReviewRepository reviewRepository;

    /**
     * Set up the test environment.
     * This method is called before each test method is executed.
     * It initializes a new ReviewRepository with the mock API.
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        reviewRepository = new ReviewRepository(api);
    }

    /**
     * Creates a new instance of a Review object with the given parameters.
     *
     * @param author The author of the review
     * @param avatarUrl The URL of the author's avatar
     * @param content The content of the review
     * @param rating The rating of the review
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
        Review review = createReview("John Tester", "https://xsgames.co/randomusers/assets/avatars/male/2.jpg", "This is the content of the new review.", 1 );

        // Add the review to the repository.
        reviewRepository.addReview(review);

        // Retrieves the review objects from the reviewRepository and stores them in the reviews variable.
        List<Review> reviews = reviewRepository.getReviews().getValue();

        // The assert method will throw an AssertionError if the review list is null.
        assert reviews != null;

        // The assertEquals() method will throw an AssertionError if the review has not been added to the top of the list.
        assertEquals("The new review should be added at the beginning of the list", review, reviews.get(0));

    }


    /**
     * This method checks if a new review already exists in the list.
     */
    @Test
    public void newReviewAlreadyExist() {
        // Create a new review that already exists
        Review review = createReview("Ranjit Singh", "https://xsgames.co/randomusers/assets/avatars/male/71.jpg", "Service très rapide et nourriture délicieuse, nous mangeons ici chaque week-end, c'est très rapide et savoureux. Continuez ainsi!", 5 );

        // Add the review to the repository.
        reviewRepository.addReview(review);

        // Retrieves the review objects from the reviewRepository and stores them in the reviews variable.
        List<Review> reviews = reviewRepository.getReviews().getValue();

        // The assert method will throw an AssertionError if the review list is null.
        assert reviews != null;

        // The assert method will throw an AssertionError if the new review already exist in the list.
        for (int currentIndex = 1; currentIndex < reviews.size(); currentIndex++) {
                assertNotEquals("The new review should be different than the existing reviews of the list", review, reviews.get(currentIndex));
        }
    }

    /**
     * This method checks if the new review can be added without comment.
     */
    @Test
    public void newReviewCommentIsEmpty() {
        // Create a new review without content
        Review review = createReview("John Tester", "https://xsgames.co/randomusers/assets/avatars/male/2.jpg", "", 1 );

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
        Review review = createReview("John Tester", "https://xsgames.co/randomusers/assets/avatars/male/2.jpg", "This is the content of the new review.", 0 );

        // Add the review to the repository.
        reviewRepository.addReview(review);

        // Retrieves the review objects from the reviewRepository and stores them in the reviews variable.
        List<Review> reviews = reviewRepository.getReviews().getValue();

        // The assert method will throw an AssertionError if the review list is null.
        assert reviews != null;

        // The assertEquals() method will throw an AssertionError if the review has been added.
        Review firstReview = reviews.get(0);
        assertTrue("The rating should be 1  or greater for the new review to be added", firstReview.getRating()>=1);
    }

    /**
     * This method checks if the new review can be added with the value rating>5
     */
    @Test
    public void newReviewRate_isMoreThan5() {
        // Create a new review with rate = 6.
        Review review = createReview("John Tester", "https://xsgames.co/randomusers/assets/avatars/male/2.jpg", "This is the content of the new review.", 6 );

        // Add the review to the repository.
        reviewRepository.addReview(review);

        // Retrieves the review objects from the reviewRepository and stores them in the reviews variable.
        List<Review> reviews = reviewRepository.getReviews().getValue();

        // The assert method will throw an AssertionError if the review list is null.
        assert reviews != null;

        // The assertEquals() method will throw an AssertionError if the review has been added.
        Review firstReview = reviews.get(0);
        assertTrue("The rating should be 5 or lower for the new review to be added", firstReview.getRating()<=5);
    }

}
