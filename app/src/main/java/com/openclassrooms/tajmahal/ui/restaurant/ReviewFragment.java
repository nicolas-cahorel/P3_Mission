package com.openclassrooms.tajmahal.ui.restaurant;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.databinding.FragmentReviewBinding;

public class ReviewFragment extends Fragment {

    private FragmentReviewBinding binding;
    private DetailsViewModel detailsViewModel;

    public static ReviewFragment newInstance() {
        ReviewFragment fragment = new ReviewFragment();
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
        binding.tvRestaurantNameInReview.setText("Taj Mahal");
        binding.buttonBack.setEnabled(true);
        binding.tvNewReviewName.setText("Manon Garcia");
        binding.buttonValidate.setEnabled(false);
        binding.buttonValidate.setText(getString(R.string.button_validate));
        binding.buttonValidate.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.grey)));
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

}