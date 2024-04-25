package com.openclassrooms.tajmahal.views;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openclassrooms.tajmahal.databinding.FragmentReviewItemBinding;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.List;

/**
 * Adapter class for the RecyclerView that displays a list of reviews.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private final List<Review> mData;

    /**
     * Constructor for the ReviewAdapter.
     *
     * @param data List of reviews to display in the RecyclerView.
     */
    public ReviewAdapter(List<Review> data) {
        this.mData = data;
    }

    // Create new views (invoked by the layout manager)

    /**
     * Creates a new ViewHolder instance for the given parent ViewGroup.
     * This method is responsible for inflating the layout and creating a new ViewHolder instance.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View. In this implementation, only one view type is used.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentReviewItemBinding binding = FragmentReviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    // Replace the contents of a view (invoked by the layout manager)

    /**
     * Binds the given review data to the ViewHolder.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Review review = mData.get(position);
        holder.bind(review);
    }

    // Return the size of your dataset (invoked by the layout manager)

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in the data set.
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * ViewHolder class that represents each item in the RecyclerView.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final FragmentReviewItemBinding binding;

        /**
         * Constructs a new ViewHolder instance with the given FragmentReviewItemBinding.
         *
         * @param binding The FragmentReviewItemBinding object that contains the views for a single review item.
         */
        public ViewHolder(FragmentReviewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * Binds the review data to the views in the ViewHolder.
         *
         * @param review The review object containing the data to bind.
         */
        public void bind(Review review) {
            Glide.with(binding.getRoot())
                    .load(review.getAvatarUrl())
                    .into(binding.ivItemReviewAvatar);
            binding.tvItemReviewName.setText(review.getAuthor());
            binding.rbItemReviewRate.setRating(review.getRating());
            binding.tvItemReviewComment.setText(review.getContent());
        }
    }
}