package com.cp470.bookit;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cp470.bookit.databinding.ActivityMainBinding;
import com.cp470.bookit.ui.home.Restaurant;
import com.cp470.bookit.ui.home.RestaurantAdapter;
import com.cp470.bookit.ui.home.RestaurantDetail;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

	public static final ArrayList<Restaurant> restaurantList = new ArrayList<>();
	private RestaurantAdapter adapter;
	private final ArrayList<Restaurant> filteredList = new ArrayList<>();
	private FirebaseAuth mAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mAuth = FirebaseAuth.getInstance();

		com.cp470.bookit.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		BottomNavigationView navView = findViewById(R.id.nav_view);

		// Passing each menu ID as a set of Ids because each
		// menu should be considered as top level destinations.
		AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_map,
				R.id.navigation_home, R.id.navigation_notifications, R.id.navigation_dashboard)
				.build();
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
		//NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
		NavigationUI.setupWithNavController(binding.navView, navController);

		// Call your setup methods within the onCreate method
		setUpRecyclerView(); // Call setUpRecyclerView first
		initSearchWidgets();
		filterRestaurants(null);
	}

	@Override
	public void onStart() {
		super.onStart();
		// Check if user is signed in (non-null) and update UI accordingly.
		FirebaseUser currentUser = mAuth.getCurrentUser();
		if (currentUser != null) {
			// User is already signed in, proceed to the main part of your app
			Log.d(TAG, "Already logged in: " + currentUser.getEmail());
			// Here you can start a new activity or update your UI as needed
		} else {
			// No user is signed in, show login or registration activity
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
		}
	}

	private void initSearchWidgets() {
		SearchView searchView = findViewById(R.id.RestaurantSearchView);
		//searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				filterRestaurants(newText);
				return true;
			}
		});
	}


	@SuppressLint("NotifyDataSetChanged")
	private void filterRestaurants(String query) {
		filteredList.clear();

		if (query == null || query.trim().isEmpty()) {
			// If the query is empty or blank, show all restaurants
			filteredList.addAll(restaurantList);
		} else {
			String lowerQuery = query.toLowerCase();
			for (Restaurant restaurant : restaurantList) {
				if (restaurant.getTitle().toLowerCase().contains(lowerQuery)
						|| restaurant.getCategory().toLowerCase().contains(lowerQuery)) {
					filteredList.add(restaurant);
				}
			}
		}

		adapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
	}


	private void setUpRecyclerView() {
		RecyclerView recyclerView = findViewById(R.id.myRecyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new RestaurantAdapter(this, filteredList, position -> {
			Restaurant selectedRestaurant = filteredList.get(position);
			Intent showDetail = new Intent(getApplicationContext(), RestaurantDetail.class);

			// Pass additional data to the Detail activity
			showDetail.putExtra("title", selectedRestaurant.getTitle());
			showDetail.putExtra("address", selectedRestaurant.getAddress());
			showDetail.putExtra("distance", selectedRestaurant.getDistance());
			showDetail.putExtra("priceIcon", selectedRestaurant.getPriceIcon());
			showDetail.putExtra("priceRange", selectedRestaurant.getPriceRange());
			showDetail.putExtra("description", selectedRestaurant.getDescription());
			showDetail.putExtra("category", selectedRestaurant.getCategory());
			showDetail.putExtra("available_times", selectedRestaurant.getAvailableTimes());
			showDetail.putExtra("image", selectedRestaurant.getImage());

			startActivity(showDetail);
		});
		recyclerView.setAdapter(adapter);
	}

	public void onCategoryButtonClick(View view) {
		if (view instanceof ImageView) {
			String category = getCategoryNameForView(view.getId());
			if (category != null && !category.isEmpty()) {
				SearchView searchView = findViewById(R.id.RestaurantSearchView);
				searchView.setQuery(category, true); // Set the query to the category name and submit it
			}
		}
	}

	private String getCategoryNameForView(int viewId) {
		if (viewId == R.id.CafeCategoryButton) {
			return "Cafe";
		} else if (viewId == R.id.AmericanCategoryButton) {
			return "American";
		} else if (viewId == R.id.ItalianCategoryButton) {
			return "Italian";
		} else if (viewId == R.id.JapaneseCategoryButton) {
			return "Japanese";
		} else {
			return null;
		}
	}

}
