package com.uniguide.adminapply.Admin;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.uniguide.adminapply.R;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String CHANNEL_ID = "CHANNEL_ID";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Play sound and vibrate on message received
        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notificationSound);
        r.play();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            r.setLooping(false);
        }

        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {100, 300, 300, 300};
        vibrator.vibrate(pattern, -1);

        // Get icon resource id
        int iconResourceId = getResources().getIdentifier(remoteMessage.getNotification().getIcon(), "drawable", getPackageName());

        // Create notification builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setSmallIcon(iconResourceId);
        } else {
            builder.setSmallIcon(R.mipmap.ic_launcher);
        }

        // Set intent for notification click
        Intent resultIntent = new Intent();
        String packageName = getString(R.string.student_app_package_name);
        resultIntent.setClassName(packageName, packageName + ".User.Notifiations");
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        // Set notification content
        builder.setContentTitle(remoteMessage.getNotification().getTitle());
        builder.setContentText(remoteMessage.getNotification().getBody());
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getNotification().getBody()));
        builder.setAutoCancel(true);
        builder.setPriority(Notification.PRIORITY_MAX);

        // Create notification channel for Android Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Channel Name", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Show notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }
}









   /* private static final String TAG = "MyFirebaseMessagingSvc";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Check if the message contains data payload
        if (remoteMessage.getData().size() > 0) {
            // Process data payload
            String message = remoteMessage.getData().get("message");
            Log.d(TAG, "Received message with data payload: " + message);
        }

        // Check if the message contains notification payload
        if (remoteMessage.getNotification() != null) {
            // Process notification payload
            String title = remoteMessage.getNotification().getTitle();
            String message = remoteMessage.getNotification().getBody();
            Log.d(TAG, "Received message with notification payload: " + title + " - " + message);
        }
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        // Send the new token to the server
        Log.d(TAG, "Received new token: " + token);
    }*/