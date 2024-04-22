package com.openclassrooms.tajmahal.ui.restaurant;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.databinding.FragmentReviewBinding;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.openclassrooms.tajmahal.views.ReviewAdapter;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Fragment that displays a form for users to submit a review for a restaurant.
 * This fragment is responsible for handling user input, validating the input, and adding the new review to the local list of reviews.
 */
@AndroidEntryPoint
public class ReviewFragment extends Fragment {

    private FragmentReviewBinding binding;
    private DetailsViewModel detailsViewModel;
    private Review newReview;
    private String avatarUrl;

    /**
     * Initializes the ViewModel for this activity.
     */
    private void setupViewModel() {
        detailsViewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
    }

    /**
     * Factory method for creating a new instance of ReviewFragment with the given ReviewRepository.
     *
     * @param reviewRepository the ReviewRepository to be used by this fragment
     * @return a new instance of ReviewFragment
     */
//    public static ReviewFragment newInstance(ReviewRepository reviewRepository) {
//        ReviewFragment fragment = new ReviewFragment();
//        fragment.reviewRepository = reviewRepository;
//        return fragment;
//    }
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
        setupViewModel(); // Prepares the ViewModel for the fragment.
        setupUI(); // Sets up user interface components.
        detailsViewModel.getTajMahalReviews().observe(requireActivity(), this::updateUIWithReviews); // Observes changes in the reviews data and updates the UI accordingly.
        binding.tvRestaurantNameInReview.setText("Taj Mahal");
        binding.buttonBack.setEnabled(true);
        binding.tvNewReviewName.setText("Manon Garcia");
        this.avatarUrl  = "https://s3-alpha-sig.figma.com/img/02e6/6d63/e35d4fc4ab41421bdc4ea8ec50940749?Expires=1714348800&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=kMBgvHNZs3pb4gMB8uACW-mXV-Wbo2gfLwtfkCN~8LNpAGpafP5DSMu76ucdJ5B6OJkx8C5bxGKJESzwpnk7pKAqbAiUqdJVFm7kDCg5lMRFXt1Wf2U9EVonpsMUiY2-C2QGHMUJwQGGDFdov3RWDH2HV0gJIMM7-OK4Iag0e0sijV0qmGve8Uo1arI6IV-yLBrfkYUxOpy23swcUmY85EcaW1hNpv1RoMvQYlwtlsrGBysgQuq0K48saCS94gYFSAH8jv2KACACo1pXFhWVWMQ5yOXPY6CCnH4JZXvLDl~NU9xRhmkKXkwIPSrkbjhtA4-D9MPS7JSDQ1PG6Kx1Fw__";
        Glide.with(binding.getRoot())
                .load(avatarUrl)
                .into(binding.ivNewReviewAvatar);
        binding.buttonValidate.setEnabled(false);
        binding.buttonValidate.setText(getString(R.string.button_validate));
        binding.buttonValidate.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.grey)));

        binding.buttonValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewReview();
                detailsViewModel.addReview(newReview);
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
        binding.tietNewReviewComment.addTextChangedListener(new TextWatcher() {
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
        String tietNewReviewComment = binding.tietNewReviewComment.getText().toString();

        if (rbNewReviewRate == 0 && tietNewReviewComment.isEmpty()) {
            binding.buttonValidate.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.grey)));
            binding.buttonValidate.setEnabled(false);
        } else if (rbNewReviewRate == 0) {
            binding.buttonValidate.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.grey)));
            binding.buttonValidate.setEnabled(false);

        } else if (tietNewReviewComment.isEmpty()) {
            binding.buttonValidate.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.grey)));
            binding.buttonValidate.setEnabled(false);

        } else {
            binding.buttonValidate.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.redButton)));
            binding.buttonValidate.setEnabled(true);

        }
    }

    private void createNewReview() {
        // Recupere l'auteur de la nouvelle review
        TextView tvNewReviewName = binding.tvNewReviewName;
        String author = tvNewReviewName.getText().toString();
        // Recupere l'url de l'avatar de l'auteur de la nouvelle review
        //ImageView ivNewReviewAvatar = binding.ivNewReviewAvatar;
        //Drawable drawable = ivNewReviewAvatar.getDrawable();
       // String avatarUrl = "";
        //if (drawable != null) {
         //   int resId = drawable.getConstantState().getChangingConfigurations();
           // int resourceId = getResources().getIdentifier("avatar_manon_garcia", "drawable", requireContext().getPackageName());
         //   avatarUrl = "@drawable/" + getResources().getResourceEntryName(resourceId);
       // }
        // Recupere la note de la nouvelle review
        RatingBar rbNewReviewRate = binding.rbNewReviewRate;
        float ratingFloat = rbNewReviewRate.getRating();
        int rating = (int) ratingFloat;
        // Recupere le commentaire de la nouvelle review
        TextInputEditText tietNewReviewComent = binding.tietNewReviewComment;
        String content = tietNewReviewComent.getText().toString();

        // Cree une nouvelle instance de l'objet à ajouter
        newReview = new Review(author, avatarUrl, content, rating);
    }

    private void updateUIWithReviews(List<Review> reviews) {
        Log.d("ReviewFragment", "Number of reviews: " + reviews.size());
        binding.fragmentReviewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ReviewAdapter adapter = new ReviewAdapter(reviews);
        binding.fragmentReviewRecyclerView.setAdapter(adapter);
    }

    /**
     * Sets up the UI-specific properties, such as system UI flags and status bar color.
     */
    private void setupUI() {
        Window window = requireActivity().getWindow();
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        // Change the status bar color to white
        window.setStatusBarColor(Color.WHITE);
        // Use dark mode for the status bar text
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        // Change the navigation bar color to white
        window.setNavigationBarColor(Color.WHITE);
    }
}
