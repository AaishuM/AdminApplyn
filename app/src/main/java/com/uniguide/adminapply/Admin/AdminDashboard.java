package com.uniguide.adminapply.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.uniguide.adminapply.Both.ApplicantsQuery;
import com.uniguide.adminapply.Both.SendAcceptedStud;
import com.uniguide.adminapply.R;

public class AdminDashboard extends AppCompatActivity {

    EditText title, message;
    Button sendNotificationButton, SendNotificationButton2;
    String userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        title = findViewById(R.id.editName);
        message = findViewById(R.id.editMessage);
        sendNotificationButton = findViewById(R.id.SendNotification);
        SendNotificationButton2 = findViewById(R.id.SendNotification2);
        FirebaseMessaging.getInstance().subscribeToTopic("all");


        SendNotificationButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if title exists in users node
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                final String name = title.getText().toString().trim();
                usersRef.child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // create message node
                            DatabaseReference messageRef = usersRef.child(name).child("message").push();
                            messageRef.child("content").setValue(message.getText().toString());
                            Toast.makeText(AdminDashboard.this, "Message sent!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(AdminDashboard.this, "User not found", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(AdminDashboard.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        sendNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get token from database
                DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
                final String name = title.getText().toString().trim();
                usersRef.child(name).child("token").child("result").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String userToken = dataSnapshot.getValue(String.class);
                        if (userToken == null) {
                            Toast.makeText(AdminDashboard.this, "Token not retrieved", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            Toast.makeText(AdminDashboard.this, "Sending the Message........", Toast.LENGTH_LONG).show();
                        }
                        // use userToken for sending push notification
                        if (!title.getText().toString().isEmpty() && !message.getText().toString().isEmpty()/*&&!userToken.isEmpty()*/) {
                            FcmNotificationsSender notificationsSender = new FcmNotificationsSender(userToken, title.getText().toString(), message.getText().toString(), getApplicationContext(), AdminDashboard.this);
                            notificationsSender.SendNotifications();
                            Toast.makeText(AdminDashboard.this, "Message Sent \uD83E\uDD13", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(AdminDashboard.this, "Error!!!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle error
                        Toast.makeText(AdminDashboard.this, "Database error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });



    }

    public void retrieveFiles(View view) {
        startActivity(new Intent(getApplicationContext(), RetreiveFiles.class));
    }

    public void retrievePdfFiles(View view) {
        startActivity(new Intent(getApplicationContext(), RetrievePdf.class));
    }

    public void sendInternational(View view) {
            startActivity(new Intent(getApplicationContext(), SendAcceptedStud.class));

    }

    public void ApplicantsQueries(View view) {
            startActivity(new Intent(getApplicationContext(), ApplicantsQuery.class));
    }



    private boolean checkfirst() {
        String val = title.getText().toString().trim();
        String val1 = message.getText().toString().trim();

        if (val.isEmpty() && val1.isEmpty()) {
            Toast.makeText(AdminDashboard.this, "Enter the Applicant Name", Toast.LENGTH_LONG).show();
            return false;
        } else {
            Toast.makeText(AdminDashboard.this, "Sending Approved to International Office", Toast.LENGTH_LONG).show();
            return true;
        }
    }
}







