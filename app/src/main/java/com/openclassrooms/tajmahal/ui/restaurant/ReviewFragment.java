package com.openclassrooms.tajmahal.ui.restaurant;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.data.repository.ReviewRepository;
import com.openclassrooms.tajmahal.databinding.FragmentReviewBinding;
import com.openclassrooms.tajmahal.domain.model.Review;

/**
 * Fragment that displays a form for users to submit a review for a restaurant.
 * This fragment is responsible for handling user input, validating the input, and adding the new review to the local list of reviews.
 */
public class ReviewFragment extends Fragment {

    private FragmentReviewBinding binding;
    private DetailsViewModel detailsViewModel;
    public ReviewRepository reviewRepository;
    private Review newReview;

    /**
     * Factory method for creating a new instance of ReviewFragment with the given ReviewRepository.
     *
     * @param reviewRepository the ReviewRepository to be used by this fragment
     * @return a new instance of ReviewFragment
     */
    public static ReviewFragment newInstance(ReviewRepository reviewRepository) {
        ReviewFragment fragment = new ReviewFragment();
        fragment.reviewRepository = reviewRepository;
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReviewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailsViewModel.getTajMahalReviews().observe(requireActivity(), this::updateUIWithReviews); // Observes changes in the reviews data and updates the UI accordingly.
        binding.tvRestaurantNameInReview.setText("Taj Mahal");
        binding.buttonBack.setEnabled(true);
        binding.tvNewReviewName.setText("Manon Garcia");
        binding.buttonValidate.setEnabled(false);
        binding.buttonValidate.setText(getString(R.string.button_validate));
        binding.buttonValidate.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.grey)));
        binding.buttonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewReview();
                AddNewReviewToLocalList();
            }

        });
        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DetailsFragment detailsFragment = new DetailsFragment();
                fragmentTransaction.replace(R.id.container, detailsFragment);
                fragmentTransaction.commit();
            }
        });
        binding.tietNewReviewComent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateButtonState();
            }
        });
        binding.rbNewReviewRate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                updateButtonState();
            }
        });
    }

    private void updateButtonState() {
        float rbNewReviewRate = binding.rbNewReviewRate.getRating();
        String tietNewReviewComent = binding.tietNewReviewComent.getText().toString();

        if (rbNewReviewRate == 0 && tietNewReviewComent.isEmpty()) {
            binding.buttonValidate.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.grey)));
            binding.buttonValidate.setEnabled(false);
        } else if (rbNewReviewRate == 0) {
            binding.buttonValidate.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.grey)));
            binding.buttonValidate.setEnabled(false);

        } else if (tietNewReviewComent.isEmpty()) {
            binding.buttonValidate.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.grey)));
            binding.buttonValidate.setEnabled(false);

        } else {
            binding.buttonValidate.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.redButton)));
            binding.buttonValidate.setEnabled(true);

        }
    }

    private void CreateNewReview() {
        // Recupere l'auteur de la nouvelle review
        TextView tvNewReviewName = binding.tvNewReviewName;
        String author = tvNewReviewName.getText().toString();
        // Recupere l'url de l'avatar de l'auteur de la nouvelle review
        ImageView ivNewReviewAvatar = binding.ivNewReviewAvatar;
        Drawable drawable = ivNewReviewAvatar.getDrawable();
        String avatarUrl = "";
        if (drawable != null) {
            int resId = drawable.getConstantState().getChangingConfigurations();
            int resourceId = getResources().getIdentifier("avatar_manon_garcia", "drawable", requireContext().getPackageName());
            avatarUrl = "@drawable/" + getResources().getResourceEntryName(resourceId);
        }
        // Recupere la note de la nouvelle review
        RatingBar rbNewReviewRate = binding.rbNewReviewRate;
        int rating = rbNewReviewRate.getNumStars();
        // Recupere le commentaire de la nouvelle review
        TextInputEditText tietNewReviewComent = binding.tietNewReviewComent;
        String content = tietNewReviewComent.getText().toString();


        // Cree une nouvelle instance de l'objet à ajouter
        newReview = new Review(author,avatarUrl, content, rating);
    }

    private void AddNewReviewToLocalList() {
        ReviewRepository.addReview(newReview);
    }

    private void updateUIWithReviews() {
        // implementer ici la maj de la liste des reviews avec la recycleview
    }

}