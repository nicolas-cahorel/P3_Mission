package com.openclassrooms.tajmahal.views;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openclassrooms.tajmahal.databinding.FragmentReviewItemBinding;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private List<Review> mData;

    // Constructor
    public ReviewAdapter(List<Review> data) {
        this.mData = data;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FragmentReviewItemBinding binding = FragmentReviewItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Review review = mData.get(position);
        holder.bind(review);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder {
        private FragmentReviewItemBinding binding;

        public ViewHolder(FragmentReviewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

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