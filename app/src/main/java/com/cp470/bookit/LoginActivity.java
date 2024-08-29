package com.cp470.bookit;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

	private FirebaseAuth mAuth;
	private EditText editEmailAddress;
	private EditText editTextPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// Hide the action bar
		//Objects.requireNonNull(getSupportActionBar()).hide();

		// Initialize Firebase Auth
		mAuth = FirebaseAuth.getInstance();
		editEmailAddress = findViewById(R.id.editEmailAddress);
		editTextPassword = findViewById(R.id.editTextPassword);
		Button loginButton = findViewById(R.id.loginButton);

		loginButton.setOnClickListener(view -> {
			String email = String.valueOf(editEmailAddress.getText());
			String password = String.valueOf(editTextPassword.getText());

			if (email.isEmpty() || password.isEmpty()) {
				Toast.makeText(LoginActivity.this, "Please enter your email and password", Toast.LENGTH_SHORT).show();
				return;
			}

			mAuth.signInWithEmailAndPassword(email, password)
					.addOnCompleteListener(task -> {
						if (task.isSuccessful()) {
							// Sign in success, update UI with the signed-in user's information
							Log.d(TAG, "signInWithEmail:success");
							FirebaseUser user = mAuth.getCurrentUser();
							Log.d(TAG, "User: " + user);
							Toast.makeText(LoginActivity.this, "Authentication success.",
									Toast.LENGTH_SHORT).show();

							// Go to the main activity
							Intent intent = new Intent(LoginActivity.this, MainActivity.class);
							startActivity(intent);
							//finish();


						} else {
							// If sign in fails, display a message to the user.
							Log.w(TAG, "signInWithEmail:failure", task.getException());
							Toast.makeText(LoginActivity.this, "Authentication failed.",
									Toast.LENGTH_SHORT).show();

						}
					});
		});


	}

	@Override
	public void onStart() {
		super.onStart();
		// Check if user is signed in (non-null) and update UI accordingly.
		FirebaseUser currentUser = mAuth.getCurrentUser();
		if (currentUser != null) {
			currentUser.reload();
		}
	}

}