package com.uniguide.adminapply.Admin;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.uniguide.adminapply.Helperclass.SessionManager;
import com.uniguide.adminapply.R;

public class LoginActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    EditText loginEmailId, loginPassword;
    Button loginButton;
    TextView createAccount;

    // Constant email and password for authentication
    final String EMAIL = "admissiontamucc@gmail.com";
    final String PASSWORD = "12345@tamucc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        loginEmailId = findViewById(R.id.login_emailId);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        //createAccount = findViewById(R.id.signUp_redirect);
        sessionManager = new SessionManager(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateEmail() | !validatePassword()) {
                    return;
                }

                String email = loginEmailId.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();
                if (authenticate(email, password)) {
                    // Login successful, proceed to the next screen
                    Intent intent = new Intent(LoginActivity.this, AdminDashboard.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Incorrect email or password
                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }

                FirebaseMessaging.getInstance().getToken()
                        .addOnCompleteListener(new OnCompleteListener<String>() {
                            @Override
                            public void onComplete(@NonNull Task<String> task) {
                                if (!task.isSuccessful()) {
                                    Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                    return;
                                }

                                // Get new FCM registration token
                                String token = task.getResult();

                                // Save the device token to the user's record in the database
                               // String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                DatabaseReference myTokenRef = FirebaseDatabase.getInstance().getReference("admin").child("myToken");
                                myTokenRef.setValue(token);

                                // Log and toast
                                String msg = "Welcome";//getString(R.string.msg_token_fmt, token);
                                Log.d(TAG, msg);
                                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


       /* createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, InternationalLogin.class);
                startActivity(intent);
            }
        });*/
    }

    private Boolean validateEmail() {
        String val = loginEmailId.getText().toString().trim();
        if (val.isEmpty()) {
            loginEmailId.setError("Admin email is required");
            return false;
        } else {
            loginEmailId.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String pass = loginPassword.getText().toString().trim();
        if (pass.isEmpty()) {
            loginPassword.setError("Admin password is required");
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
}


    /*private void checkUser() {
        String userName = loginEmailId.getText().toString().trim();
        String userPassword = loginPassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("admin");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userName);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    loginEmailId.setError(null);
                    String passwordFromDB = snapshot.child(userName).child("password").getValue(String.class);
                    if (!Objects.equals(passwordFromDB, userPassword)) {
                        loginPassword.setError(null);
                        Intent intent = new Intent(LoginActivity.this, AdminDashboard.class);
                        startActivity(intent);
                        finish();
                    } else {
                        loginPassword.setError("Invalid Password");
                        loginPassword.requestFocus();
                    }
                } else {
                    loginEmailId.setError("Admin does not exist");
                    loginEmailId.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/
