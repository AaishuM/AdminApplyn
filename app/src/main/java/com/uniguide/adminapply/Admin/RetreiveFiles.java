/*package com.uniguide.adminapply.Admin;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uniguide.adminapply.Helperclass.User;
import com.uniguide.adminapply.R;

import java.util.ArrayList;
import java.util.List;

public class RetreiveFiles extends AppCompatActivity {

    ListView listView;

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    List<User> registeredUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retreive_files);

        //listView = findViewById(R.id.listview);

        recyclerView = findViewById(R.id.recyclerview);

        registeredUsers = new ArrayList<>();

        retrieveUserForms();
    }

    private void retrieveUserForms() {
        databaseReference = FirebaseDatabase.getInstance().getReference("RegisteredUser");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               // registeredUsers.clear(); // Clear the list before adding new users
                for (DataSnapshot ds : snapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    registeredUsers.add(user);
                }
                displayRegisteredUsers();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void displayRegisteredUsers() {
        SpannableString spannableString;
        String[] uploadName = new String[registeredUsers.size()];
        for (int i = 0; i < registeredUsers.size(); i++) {
            String fullName = registeredUsers.get(i).getFullName();
            spannableString = new SpannableString(fullName);
            spannableString.setSpan(new ForegroundColorSpan(Color.GREEN), 0, fullName.length(), 0);
            uploadName[i] = spannableString + "\n"
                    + "Date of Birth: " + registeredUsers.get(i).getDateOfBirth() + "\n"
                    + "Country of Birth: " + registeredUsers.get(i).getCountryOfBirth() + "\n"
                    + "Place of Birth: " + registeredUsers.get(i).getPlaceOfBirth() + "\n"
                    + "Country of Residence: " + registeredUsers.get(i).getCountryOfResidence() + "\n"
                    + "Province: " + registeredUsers.get(i).getProvince() + "\n"
                    + "Email: " + registeredUsers.get(i).getEmail() + "\n"
                    + "Country: " + registeredUsers.get(i).getCountry() + "\n"
                    + "Contact: " + registeredUsers.get(i).getContact() + "\n"
                    + "School: " + registeredUsers.get(i).getSchool() + "\n"
                    + "School Location: " + registeredUsers.get(i).getSchoolLocation() + "\n"
                    + "School Course: " + registeredUsers.get(i).getSchoolCourse() + "\n"
                    + "School GPA: " + registeredUsers.get(i).getSchoolGPA() + "\n"
                    + "College: " + registeredUsers.get(i).getCollege() + "\n"
                    + "College Location: " + registeredUsers.get(i).getCollegeLocation() + "\n"
                    + "College Course: " + registeredUsers.get(i).getCollegeCourse() + "\n"
                    + "College GPA: " + registeredUsers.get(i).getCollegeGPA() + "\n"
                    + "University: " + registeredUsers.get(i).getUni() + "\n"
                    + "University Location: " + registeredUsers.get(i).getUniLocation() + "\n"
                    + "University Course: " + registeredUsers.get(i).getUniCourse() + "\n"
                    + "University GPA: " + registeredUsers.get(i).getUniGPA()+"\n\n\n";
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, uploadName) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.BLACK);
                return view;
            }
        };
        //recyclerView.setAdapter(adapter);
        //listView.setAdapter(adapter);
    }
}*/

package com.uniguide.adminapply.Admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uniguide.adminapply.R;

import java.util.ArrayList;

public class RetreiveFiles extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ItemAdapter itemAdapter;
    ArrayList<items> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retreive_files);

        recyclerView = findViewById(R.id.recyclerview);
        databaseReference = FirebaseDatabase.getInstance().getReference("RegisteredUser");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        itemAdapter = new ItemAdapter(this,list);
        recyclerView.setAdapter(itemAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    items items = dataSnapshot.getValue(items.class);
                    list.add(items);
                }
                itemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}