package com.openclassrooms.tajmahal.domain.model;

/**
 * Represents a review and its various attributes.
 * <p>
 * This class models a review with its author, url of the author's avatar, content and rating
 * </p>
 * Example:
 * <pre>
 * Review Ranjit Singh = new Review("Ranjit Singh", "https://xsgames.co/randomusers/assets/avatars/male/71.jpg",
 * "Service très rapide et nourriture délicieuse, nous mangeons ici chaque week-end, c'est très rapide et savoureux. Continuez ainsi!", 5);
 * </pre>
 */

import java.util.Objects;

/**
 * Represents a review for the restaurant.
 */
public class Review {
    // Member variables representing attributes of a review.
    private final String author;
    private final String avatarUrl;
    private final String content;
    private final int rating;

    /**
     * Constructor for the review class.
     *
     * @param author    The author of the review
     * @param avatarUrl The URL of the author's avatar
     * @param content   The content of the review
     * @param rating    The rating of the review, between 1 and 5
     */
    public Review(String author, String avatarUrl, String content, int rating) {
        this.author = author;
        this.avatarUrl = avatarUrl;
        this.content = content;
        this.rating = rating;
    }

    /**
     * Returns the author of the review.
     *
     * @return The author of the review
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns the URL of the author's avatar.
     *
     * @return The URL of the author's avatar
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * Returns the content of the review.
     *
     * @return The content of the review
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the rating of the review.
     *
     * @return The rating of the review
     */
    public int getRating() {
        return rating;
    }

    /**
     * Compares this review to the specified object.
     * The result is true if and only if the argument is not null and is a Review object that contains the same author, avatar URL, content and rating as this review.
     *
     * @param obj The object to compare with this review
     * @return true if the review objects are the same; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Review)) {
            return false;
        }
        Review other = (Review) obj;
        return getRating() == other.getRating() &&
                Objects.equals(getAuthor(), other.getAuthor()) &&
                Objects.equals(getAvatarUrl(), other.getAvatarUrl()) &&
                Objects.equals(getContent(), other.getContent());
    }
}