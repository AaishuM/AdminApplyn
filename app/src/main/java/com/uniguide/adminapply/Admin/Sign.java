package com.uniguide.adminapply.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uniguide.adminapply.Helperclass.HelperMain;
import com.uniguide.adminapply.R;

public class Sign extends AppCompatActivity {

    FirebaseDatabase database;

    DatabaseReference reference;
    EditText signupEmail,signupPassword,signUpUsername,signUpName;
    Button signupButton;
    TextView loginRedirectArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signupEmail = findViewById(R.id.signUp_email);
        signupPassword = findViewById(R.id.signUp_Password);
        signUpName= findViewById(R.id.signUp_Name);
        signUpUsername= findViewById(R.id.signUp_Username);
        signupButton = findViewById(R.id.signUp_button);
        loginRedirectArrow = findViewById(R.id.login_redirect);

        loginRedirectArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sign.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database = FirebaseDatabase.getInstance();
                reference = database.getReference("admin");

                String email = signupEmail.getText().toString();
                String password= signupPassword.getText().toString();
                String username= signUpUsername.getText().toString();
                String name= signUpName.getText().toString();

                HelperMain helperMain = new HelperMain(name,email,password,username);

                reference.child(name).setValue(helperMain);

                Toast.makeText(Sign.this,"You have signed Up successfully",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Sign.this,LoginActivity.class));
            }
        });




    }
}