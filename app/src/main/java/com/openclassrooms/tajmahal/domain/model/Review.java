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

/**
 * Represents a review for the restaurant.
 */
public class Review {
    // Member variables representing attributes of a review.
    private String author;
    private String avatarUrl;
    private String content;
    private int rating;

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


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAvatarUrl() { return avatarUrl; }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}