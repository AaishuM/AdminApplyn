package com.uniguide.adminapply.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiEnterpriseConfig;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.uniguide.adminapply.Helperclass.pdf;
import com.uniguide.adminapply.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RetrievePdf extends AppCompatActivity {

    ListView listView1;
    DatabaseReference databaseReference;
    List<pdf> pdfList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_pdf);

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

       databaseReference= FirebaseDatabase.getInstance().getReference("MyUploads");
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













/*package com.uniguide.adminapply.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.uniguide.adminapply.Helperclass.pdf;
import com.uniguide.adminapply.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RetrievePdf extends AppCompatActivity {

    ListView listView1;

    DatabaseReference databaseReference;
    StorageReference storageReference;

    List<pdf> pdfList;

    StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_pdf);

        listView1 = findViewById(R.id.listview2);

        pdfList = new ArrayList<>();

        // Get the storage reference
        storageReference = FirebaseStorage.getInstance().getReference();

        retrievePdfFiles();

        // Create a custom adapter to display the list of PDF files
        ListAdapter adapter = new ArrayAdapter<pdf>(this, android.R.layout.simple_list_item_1, pdfList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
                }

                pdf pdfRetrievals = pdfList.get(position);
                TextView textView = convertView.findViewById(android.R.id.text1);
                String fileName = getFileNameFromUrl(pdfRetrievals.getUrl());
                textView.setText(fileName);

                return convertView;
            }
        };

        listView1.setAdapter(adapter);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pdf pdfRetrievals = pdfList.get(i);
                downloadFile(pdfRetrievals.getUrl());
            }
        });
    }

    private String getFileNameFromUrl(String url) {
        String fileName = url.substring(url.lastIndexOf('/') + 1);
        return fileName;
    }

    private void retrievePdfFiles() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Retrieving PDF files");
        progressDialog.setMessage("Please wait while we retrieve the files...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Get the PDF uploads from the Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("pdfUploads/Anita");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pdfList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        pdf pdfRetrievals = new pdf(dataSnapshot1);
                        pdfList.add(pdfRetrievals);
                    }
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
                Toast.makeText(RetrievePdf.this, "Error retrieving PDF files", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void downloadFile(String url) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Downloading PDF");
        progressDialog.setMessage("Please wait while we download the file...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Get the filename from the URL
        String fileName =("1679834094807");

        // Set the file path in the device's external storage directory
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/" + fileName);

        // Get the download URL from the Firebase storage
        storageReference.child("pdfUploads/" + fileName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Download the file using the Firebase storage reference
                storageReference.child("pdfUploads/" + fileName).getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Toast.makeText(RetrievePdf.this, "PDF downloaded successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(RetrievePdf.this, "Error downloading PDF", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull FileDownloadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        progressDialog.setProgress((int) progressPercent);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(RetrievePdf.this, "Error downloading PDF", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
*/

/*package com.uniguide.adminapply.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uniguide.adminapply.Helperclass.pdf;
import com.uniguide.adminapply.R;

import java.util.ArrayList;
import java.util.List;
public class RetrievePdf extends AppCompatActivity {

    ListView listView1;

    DatabaseReference databaseReference;

    List<pdf> pdfList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_pdf);

        listView1 = findViewById(R.id.listview2);

        pdfList = new ArrayList<>();

        retrievePdfFiles();

        // Create a custom adapter to display the list of PDF files
        ListAdapter adapter = new ArrayAdapter<pdf>(this, android.R.layout.simple_list_item_1, pdfList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
                }

                pdf pdfRetrievals = pdfList.get(position);
                TextView textView = convertView.findViewById(R.id.PassedEditName);
               // textView.setText(pdfRetrievals.getUsername());

                return convertView;
            }
        };

        listView1.setAdapter(adapter);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pdf pdfRetrievals = pdfList.get(i);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType(
              intent.setData(Uri.parse(pdfRetrievals.getUrl()));
                startActivity(intent);
            }
        });
    }

    private void retrievePdfFiles() {
        databaseReference = FirebaseDatabase.getInstance().getReference("pdfUploads/pdfUploads ");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){

                    pdf pdf = ds.getValue(com.uniguide.adminapply.Helperclass.pdf.class);
                    pdfList.add(pdf);


                }
                String[]  childsnodeName=

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}*/

   /* private void retrievePdfFiles() {
        // Get a reference to the database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Uploads");

        // Add a listener to retrieve the data
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Loop through each child node of the "Uploads" node
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Get the user ID
                    String userId = dataSnapshot.getKey();

                    // Loop through each child node of the current node (i.e., the user ID node)
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        // Get the file URL from the "url" node
                        String fileUrl = dataSnapshot1.child("url").getValue(String.class);

                        // Add the PDF file URL and user ID to the pdfList
                        pdfList.add(new pdf(fileUrl, userId));
                    }
                }

                // Notify the adapter that the data has changed
                pdfAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
    }

}
*/

    /* private void retrievePdfFiles() {
        // Get a reference to the database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("pdfUploads");

        // Add a listener to retrieve the data
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Loop through each child node of the "pdfUploads" node
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Loop through each child node of the current node
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        // Get the file URL and name from the "pdfUploads" node
                        String fileUrl = dataSnapshot1.child("url").getValue(String.class);

                        // Create a new pdf object and add it to the pdfList list
                        pdf pdfRetrievals = new pdf();
                        pdfRetrievals.setUrl(fileUrl);
                        pdfList.add(pdfRetrievals);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
    }
}*/















/*public class RetrievePdf extends AppCompatActivity {

    ListView listView1;

    StorageReference storageReference;

    TextView rootName;

    DatabaseReference databaseReference;

    List<pdf> pdfList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_pdf);

        listView1 = findViewById(R.id.listview2);
        //rootName = findViewById(R.id.PassedEditName);

        pdfList = new ArrayList<>();

        retrievePdfFiles();

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pdf pdfRetrievals = pdfList.get(i);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType(
                intent.setData(Uri.parse(pdfRetrievals.getUrl()));
                startActivity(intent);
            }
        });
    }

    private void retrievePdfFiles() {
        // Get a reference to the database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("pdfUploads");

        // Add a listener to retrieve the data
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Loop through each child node of the "pdfUploads" node
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Loop through each child node of the current node
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        // Get the file URL from the "myadapter" node
                        String fileUrl = dataSnapshot1.child("pdfUploads").getValue(String.class);

                        // Download the file using the URL
                        downloadFileFromUrl(fileUrl);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
    }

    private void downloadFileFromUrl(String fileUrl) {
        if (fileUrl != null && !fileUrl.isEmpty()) {
            // Get a reference to the storage
            StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(fileUrl);

            // Download the file
            storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                // Use the downloaded file URI
                // ...
            }).addOnFailureListener(exception -> {
                // Handle any errors
            });
        }
    }
}
*/


   /* private void retrievePdfFiles() {
        databaseReference = FirebaseDatabase.getInstance().getReference("pdfUploads");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postsnapshot : snapshot.getChildren()) {
                    pdf pdf= postsnapshot.getValue(com.uniguide.adminapply.Helperclass.pdf.class);
                    pdfList.add(pdf);
                }
                String[] Uploads = new String[pdfList.size()];
                for (int i = 0; i < Uploads.length; i++) {
                    Uploads[i] = pdfList.get(i).getUsername();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, Uploads);
                listView1.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}*/
   /* private void retrievePdfFiles() {
        storageReference = FirebaseStorage.getInstance().getReference().child("pdfs");
        storageReference.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                PdfRetrieve.clear();
                for (StorageReference item : listResult.getItems()) {
                    item.getMetadata().getCustomMetadata().addOnSuccessListener(new OnSuccessListener<Map<String, String>>() {
                        @Override
                        public void onSuccess(Map<String, String> customMetadata) {
                            if (customMetadata.containsKey("Content-Type") && customMetadata.get("Content-Type").equals("application/pdf")) {
                                PdfRetrieve.add(new pdf(item.getName(), item.getDownloadUrl().toString()));
                            }
                        }
                    });
                }

                String[] filename = new String[PdfRetrieve.size()];
                for (int i = 0; i < filename.length; i++) {
                    filename[i] = PdfRetrieve.get(i).getName();
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, filename) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                        View view = super.getView(position, convertView, parent);
                        TextView textView = (TextView) view.findViewById(android.R.id.text1);
                        textView.setTextColor(Color.BLACK);
                        textView.setTextSize(20);
                        return view;
                    }
                };

                listView1.setAdapter(arrayAdapter);
            }
        });
    }*/

 /*  private void retrievePdfFiles() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Name");

        storageReference.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                pdfList.clear();
                for (StorageReference item : listResult.getItems()) {
                    item.getMetadata().addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                        @Override
                        public void onSuccess(StorageMetadata storageMetadata) {
                            if (storageMetadata.getContentType().equals("application/pdf")) {
                                pdfList.add(new pdf(item.getName(), item.getDownloadUrl().toString()));
                            }

                            String[] pdfNames = new String[pdfList.size()];

                            for (int i = 0; i < pdfNames.length; i++) {
                                pdfNames[i] = pdfList.get(i).getName();
                            }

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, pdfNames);

                            listView1.setAdapter(arrayAdapter);
                        }
                    });
                }
            }
        });
    }

}
*/

/*package com.uniguide.adminapply;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class RetrievePdf extends AppCompatActivity {

    ListView listView1;

    StorageReference storageReference;

    List<pdf> PdfRetrieve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_pdf);

        listView1 = findViewById(R.id.listview2);

        PdfRetrieve = new ArrayList<>();

        retrievePdfFiles();
    }


    private void retrievePdfFiles() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("pdfs");

        storageReference.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                List<pdf> pdfList = new ArrayList<>();

                for (StorageReference item : listResult.getItems()) {
                    if (item.getMetadata().getContentType().equals("application/pdf")) {
                        pdfList.add(new pdf(item.getName(), item.getDownloadUrl().toString()));
                    }
                }

                String[] pdfNames = new String[pdfList.size()];

                for (int i = 0; i < pdfNames.length; i++) {
                    pdfNames[i] = pdfList.get(i).getName();
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, pdfNames);

                listView1.setAdapter(arrayAdapter);
            }
        });
    }

}
*/
  /*
}
*/
 /*   private void retrievePdfFiles() {
        storageReference = FirebaseStorage.getInstance().getReference().child("pdfs");
        storageReference.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                PdfRetrieve.clear();
                for (StorageReference item : listResult.getItems()) {
                    if (item.getMetadata().getContentType().equals("application/pdf")) {
                        PdfRetrieve.add(new pdf(item.getName(), item.getDownloadUrl().toString()));
                    }

                }

                String[] filename = new String[PdfRetrieve.size()];
                for (int i = 0; i < filename.length; i++) {
                    filename[i] = PdfRetrieve.get(i).getName();
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, filename) {
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                        View view = super.getView(position, convertView, parent);
                        TextView textView = (TextView) view.findViewById(android.R.id.text1);
                        textView.setTextColor(Color.BLACK);
                        textView.setTextSize(20);
                        return view;
                    }
                };

                listView1.setAdapter(arrayAdapter);
            }
        });
    }
}
*/