package com.uniguide.adminapply.Both;

import static com.google.firebase.messaging.Constants.MessageNotificationKeys.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.uniguide.adminapply.Admin.LoginActivity;
import com.uniguide.adminapply.Admin.StatisticsGraph;
import com.uniguide.adminapply.InternationalLog.InternationalLogin;
import com.uniguide.adminapply.R;

public class CombinationLogin extends AppCompatActivity {

    Button retrieveButton,login_button;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combination_login);

        CardView adminLoginCard = findViewById(R.id.admin_login_card);
        CardView internationalLoginCard = findViewById(R.id.international_login_card);
        retrieveButton = findViewById(R.id.retrieveButton);
        login_button = findViewById(R.id.login_button);
        ratingBar = findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) {
                    // User has rated the app, enable the loginButton
                    login_button.setEnabled(true);
                    // Hide the myRateText TextView
                    //ratingBar.setVisibility(View.GONE);
                    String msg = "Thank you for rating our app "; //getString(R.string.msg_token_fmt, userName);
                    Log.d(TAG, msg);
                    Toast.makeText(CombinationLogin.this, msg, Toast.LENGTH_SHORT).show();
                }
            }
        });

        adminLoginCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CombinationLogin.this, LoginActivity.class);
                startActivity(intent);
                // Open Admin Login page here
            }
        });

        internationalLoginCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open International Login page here
                Intent intent = new Intent(CombinationLogin.this, InternationalLogin.class);
                startActivity(intent);
            }
        });

        retrieveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CombinationLogin.this, StatisticsGraph.class);
                startActivity(intent);
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float rating = ratingBar.getRating();
                if (rating <= 0) {
                    // The user has not rated the app, display a message
                    String msg = "Please rate the app before logging in";
                    Log.d(TAG, msg);
                    Toast.makeText(CombinationLogin.this, msg, Toast.LENGTH_SHORT).show();
                } else{
                    String msg = "Thank  You !";
                    Log.d(TAG, msg);
                    Toast.makeText(CombinationLogin.this, msg, Toast.LENGTH_SHORT).show();
                    }

            }
        });

        float rating = ratingBar.getRating();
        if (rating > 0) {
                String msg = "Please rate our app"; //getString(R.string.msg_token_fmt, userName);
                Log.d(TAG, msg);
                Toast.makeText(CombinationLogin.this, msg, Toast.LENGTH_SHORT).show();
                login_button.setEnabled(true);
        } else {
                String msg = "Please rate our app before logging in";
                Log.d(TAG, msg);
                Toast.makeText(CombinationLogin.this, msg, Toast.LENGTH_SHORT).show();
                login_button.setEnabled(false);
        }

    }

    }