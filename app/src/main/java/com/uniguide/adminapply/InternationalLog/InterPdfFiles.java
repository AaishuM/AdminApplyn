package com.uniguide.adminapply.InternationalLog;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uniguide.adminapply.Helperclass.pdf;
import com.uniguide.adminapply.R;

import java.util.ArrayList;
import java.util.List;

public class InterPdfFiles extends AppCompatActivity {

    ListView listView1;
    DatabaseReference databaseReference;
    List<pdf> pdfList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inter_pdf_files);

        listView1 = findViewById(R.id.listview2);

        pdfList = new ArrayList<>();

        retrievePdfFiles();

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pdf pdf = pdfList.get(i);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("application/pdf");
                intent.setData(Uri.parse(pdf.getUrl()));
                startActivity(intent);
            }
        });
    }

    private void retrievePdfFiles() {

        databaseReference= FirebaseDatabase.getInstance().getReference("MyUploadsInter");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds: snapshot.getChildren()){

                    pdf pdf = ds.getValue(com.uniguide.adminapply.Helperclass.pdf.class);
                    pdfList.add(pdf);
                }

                String[] uploadsName = new String[pdfList.size()];

                for(int i=0;i<uploadsName.length;i++){

                    uploadsName[i] = pdfList.get(i).getName();
                }

                ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, uploadsName){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view=super.getView(position, convertView, parent);
                        TextView textView = (TextView)view
                                .findViewById(android.R.id.text1);

                        textView.setTextColor(Color.BLACK);
                        textView.setTextSize(20);
                        return view;

                    }
                };

                listView1.setAdapter(arrayAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}