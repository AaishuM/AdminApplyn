package com.uniguide.adminapply.InternationalLog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.uniguide.adminapply.R;

public class InternationalLogin extends AppCompatActivity {

    EditText loginEmailId, loginPassword ;
    Button login_button;

    final String EMAIL = "internationaltamucc@gmail.com";
    final String PASSWORD = "12345@tamucc";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_international_login);


        loginEmailId = findViewById(R.id.login_Username);
        loginPassword = findViewById(R.id.login_password);
        login_button = findViewById(R.id.login_button);


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateEmail() | !validatePassword()) {
                    return;
                }
                String email = loginEmailId.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();
                if (authenticate(email, password)) {
                    // Login successful, proceed to the next screen
                    Intent intent = new Intent(InternationalLogin.this, InternationalDashboard.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Incorrect email or password
                    Toast.makeText(InternationalLogin.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private Boolean validateEmail() {
        String val = loginEmailId.getText().toString().trim();
        if (val.isEmpty()) {
            loginEmailId.setError("Email is required");
            return false;
        } else {
            loginEmailId.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String pass = loginPassword.getText().toString().trim();
        if (pass.isEmpty()) {
            loginPassword.setError("Password is required");
            return false;
        } else {
            loginPassword.setError(null);
            return true;
        }
    }

    // Function to check if the entered email and password are correct
    public boolean authenticate(String email, String password) {
        if (email.equals(EMAIL) && password.equals(PASSWORD)) {
            return true;
        } else {
            return false;
        }
    }

   /* private void checkUser() {
        String userName = login_Username.getText().toString().trim();
        String userPassword = login_password.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Internationals");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userName);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    login_Username.setError(null);
                    String passwordFromDB = snapshot.child(userName).child("password").getValue(String.class);
                    if (!Objects.equals(passwordFromDB, userPassword)) {
                        login_password.setError(null);
                        Intent intent = new Intent(InternationalLogin.this, InternationalDashboard.class);
                        startActivity(intent);
                        finish();
                    } else {
                        login_password.setError("Please Try again,Invalid Password");
                        login_password.requestFocus();
                    }
                } else {
                    login_Username.setError("International Login does not exist");
                    login_Username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/

}