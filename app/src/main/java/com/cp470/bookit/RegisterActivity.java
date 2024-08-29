package com.cp470.bookit;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

	private FirebaseAuth mAuth;
	private EditText editEmailAddress;
	private EditText editTextPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		//initialize firebase auth
		mAuth = FirebaseAuth.getInstance();
		editEmailAddress = findViewById(R.id.editEmailAddress);
		editTextPassword = findViewById(R.id.editTextPassword);
		Button registerButton = findViewById(R.id.registerButton);

		registerButton.setOnClickListener(view -> {
			String email = String.valueOf(editEmailAddress.getText());
			String password = String.valueOf(editTextPassword.getText());

			if (email.isEmpty() || password.isEmpty()) {
				Toast.makeText(RegisterActivity.this, "Please enter your email and password", Toast.LENGTH_SHORT).show();
				return;
			}

			mAuth.createUserWithEmailAndPassword(email, password)
					.addOnCompleteListener(task -> {
						if (task.isSuccessful()) {
							// Sign in success, update UI with the signed-in user's information
							Log.d(TAG, "createUserWithEmail:success");
							FirebaseUser user = mAuth.getCurrentUser();
							Log.d(TAG, "User: " + user);
							Toast.makeText(RegisterActivity.this, "Authentication success.",
									Toast.LENGTH_SHORT).show();

							// Go to the main activity
							Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
							startActivity(intent);
							finish();
						} else {
							// If sign in fails, display a message to the user.
							Log.w(TAG, "createUserWithEmail:failure", task.getException());
							Toast.makeText(RegisterActivity.this, "Authentication failed.",
									Toast.LENGTH_SHORT).show();
						}
					});

		});


	}
}