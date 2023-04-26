package com.uniguide.adminapply.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.uniguide.adminapply.R;
import com.uniguide.adminapply.SendNotificationPack.APIService;
import com.uniguide.adminapply.SendNotificationPack.Client;
import com.uniguide.adminapply.SendNotificationPack.Data;
import com.uniguide.adminapply.SendNotificationPack.MyResponse;
import com.uniguide.adminapply.SendNotificationPack.NotificationSender;
import com.uniguide.adminapply.SendNotificationPack.Token;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendNotif extends AppCompatActivity {
    EditText UserTB,Title,Message;
    Button send;
    private APIService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notif);
        UserTB=findViewById(R.id.UserID);
        Title=findViewById(R.id.Title);
        Message=findViewById(R.id.Message);
        send=findViewById(R.id.button);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Tokens").child(UserTB.getText().toString().trim()).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String usertoken=dataSnapshot.getValue(String.class);
                        sendNotifications(usertoken, Title.getText().toString().trim(),Message.getText().toString().trim());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        updateToken();
    }
    private void updateToken(){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token -> {
            Token newToken = new Token(token);
            FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(newToken);
        });
    }

    public void sendNotifications(String usertoken, String title, String message) {
        Data data = new Data(title, message);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(SendNotif.this, "Failed ", Toast.LENGTH_LONG);
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }

}







/*import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.Response;
import com.google.firebase.database.FirebaseDatabase;
import com.uniguide.adminapply.R;
import com.uniguide.adminapply.SendNotificationPack.APIService;
import com.uniguide.adminapply.SendNotificationPack.Token;


public class SendNotif extends AppCompatActivity {

    EditText UserTB,Title,Message;
    Button send;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notif);


        UserTB = findViewById();
        Title = findViewById();
        Message = findViewById();
        send = findViewById();

        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("tokens").child(UserTB.getText().toString().trim(){

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String usertoken = datasnapshot.getValue(String.class);
                        sendNotifications(usertoken, Title.getText().toString().trim(), Message.getText().toString().trim())
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
            }
        });

        updateToken();


    }
    private void updateToken() {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance().getUser();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Token token = new Token(refreshToken);
        FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseDatabase.getInstance().getUser).getUid()).setvalue(token);
    }

    public void sendNotifications(String usertoken, String title, String message) {

        Data data = new Data (title, message);
        NotificationSender sender =  New Notifications(data, usertoken);
        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>(){

            @Override
            public void onResponse(Callback<MyResponse> call, Response<MyResponse> response){
                if (response.code()==200{
                    if(response.body().success !=1) {
                        Toast.makeText(SendNotif.this, "Failed", Toast.LENGTH_LONG);
                    }

                }

            }

            @Override
            public void OnFailure(Callback<MyResponse> call, Throwable t){

            }

        }

    }
}*/