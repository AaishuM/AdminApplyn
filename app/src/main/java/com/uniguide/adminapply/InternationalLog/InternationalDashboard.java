package com.uniguide.adminapply.InternationalLog;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uniguide.adminapply.Admin.FcmNotificationsSender;
import com.uniguide.adminapply.Both.ApplicantsQuery;
import com.uniguide.adminapply.R;

public class InternationalDashboard extends AppCompatActivity {

    EditText title, message;

    Button btn1,SendFinancialDoc,CheckStudentquery,SendNotificationDone;
    private String CHANNEL_ID = "My Notification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_international_dashboard);

        title = findViewById(R.id.editName);
        message = findViewById(R.id.editEmail);

        btn1 = findViewById(R.id.retrieveAdminFiles);
        SendFinancialDoc = findViewById(R.id.SendFinancialDoc);
        CheckStudentquery = findViewById(R.id.CheckStudentquery);
        SendNotificationDone = findViewById(R.id.SendNotificationDone);


        SendFinancialDoc.setOnClickListener(new View.OnClickListener() {
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
                            Toast.makeText(InternationalDashboard.this, "Fill the name text and Message to send", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            Toast.makeText(InternationalDashboard.this, "Sending the Message........", Toast.LENGTH_LONG).show();
                        }
                        // use userToken for sending push notification
                        if (!title.getText().toString().isEmpty() && !message.getText().toString().isEmpty()/*&&!userToken.isEmpty()*/) {
                            FcmNotificationsSender notificationsSender = new FcmNotificationsSender(userToken, title.getText().toString(), message.getText().toString(), getApplicationContext(), InternationalDashboard.this);
                            notificationsSender.SendNotifications();
                            Toast.makeText(InternationalDashboard.this, "Message Sent \uD83E\uDD13", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(InternationalDashboard.this, "Error!!!", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle error
                        Toast.makeText(InternationalDashboard.this, "Database error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        CheckStudentquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InternationalDashboard.this, ApplicantsQuery.class);
                startActivity(intent);
            }
        });

        SendNotificationDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InternationalDashboard.this, SendInter20Doc.class);
                startActivity(intent);
            }
        });




        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(InternationalDashboard.this, InterPdfFiles.class);
                startActivity(intent);

            }
        });



    }

    public void triggerNotification(){
        Intent intentNotification = new Intent(this, InternationalDashboard.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intentNotification, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builderNotificationCompat = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setContentTitle("My Notification")
                .setContentText("My Notification text here.")
                .setSmallIcon(android.R.drawable.btn_star_big_on);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channelId", "channelName", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        } else {
            // For older versions of Android, use the pre-26 methods to create notifications
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channelId")
                    .setContentTitle("My notification")
                    .setContentText("Hello World!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);

            notificationManager.notify(0, builder.build());
        }

    }
}


