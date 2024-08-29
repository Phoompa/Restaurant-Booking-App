package com.cp470.bookit.ui.home;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cp470.bookit.R;
import com.cp470.bookit.reserve;

public class RestaurantDetail extends AppCompatActivity {
	private Restaurant selectedRestaurant;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.restaurant_detail);

		getSelectedRestaurant();
		setValues();

		// Set the book now button
		Button bookNowButton = findViewById(R.id.bookNowButton);

		//Start the booking activity when the book now button is clicked

		bookNowButton.setOnClickListener(view -> {
			Intent intent = new Intent(RestaurantDetail.this, reserve.class);
			startActivity(intent);
		});
	}

	private void getSelectedRestaurant() {
		Intent previousIntent = getIntent();

		String parsedStringTitle = previousIntent.getStringExtra("title");
		String parsedStringAddress = previousIntent.getStringExtra("address");
		String parsedStringDistance = previousIntent.getStringExtra("distance");
		String parsedStringPriceIcon = previousIntent.getStringExtra("priceIcon");
		String parsedStringPriceRange = previousIntent.getStringExtra("priceRange");
		String parsedStringDescription = previousIntent.getStringExtra("description");
		String parsedStringCategory = previousIntent.getStringExtra("category");
		int parsedStringImage = previousIntent.getIntExtra("image", 0);
		String parsedStringAvailableTimes = previousIntent.getStringExtra("available_times");

		// Create a Restaurant object
		selectedRestaurant = new Restaurant(
				parsedStringTitle != null ? parsedStringTitle : "",
				parsedStringAddress != null ? parsedStringAddress : "",
				parsedStringDistance != null ? parsedStringDistance : "",
				parsedStringPriceIcon != null ? parsedStringPriceIcon : "",
				parsedStringPriceRange != null ? parsedStringPriceRange : "",
				parsedStringDescription != null ? parsedStringDescription : "",
				parsedStringCategory != null ? parsedStringCategory : "",
				parsedStringImage,
				parsedStringAvailableTimes != null ? parsedStringAvailableTimes : ""
		);
	}

	private void setValues() {
		TextView titleTextView = findViewById(R.id.titleTextView);
		TextView addressTextView = findViewById(R.id.addressTextView);
		TextView distanceTextView = findViewById(R.id.distanceTextView);
		TextView priceIconTextView = findViewById(R.id.priceIconTextView);
		TextView priceRangeTextView = findViewById(R.id.PriceRangeTextView);
		TextView categoryTextView = findViewById(R.id.categoryTextView);
		ImageView imageImageView = findViewById(R.id.imageImageView);
		TextView availableTimesTextView = findViewById(R.id.availableTimesTextView);
		TextView descriptionTextView = findViewById(R.id.DescriptionTextView);

		// Check if the selectedRestaurant is not null and set values
		if (selectedRestaurant != null) {
			titleTextView.setText(selectedRestaurant.getTitle());
			addressTextView.setText(selectedRestaurant.getAddress());
			distanceTextView.setText(selectedRestaurant.getDistance());
			priceIconTextView.setText(selectedRestaurant.getPriceIcon());
			priceRangeTextView.setText(selectedRestaurant.getPriceRange());
			categoryTextView.setText(selectedRestaurant.getCategory());
			availableTimesTextView.setText(selectedRestaurant.getAvailableTimes());
			descriptionTextView.setText(selectedRestaurant.getDescription());
			imageImageView.setImageResource(selectedRestaurant.getImage());
		}
	}
}
