package com.uniguide.adminapply.Both;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.uniguide.adminapply.Admin.AdminDashboard;
import com.uniguide.adminapply.Helperclass.PdfUploading;
import com.uniguide.adminapply.R;

public class SendAcceptedStud extends AppCompatActivity {

    EditText editText;
    Button button, btn, btnSave;
    Uri selectedPdfUri;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_accepted_stud);


        editText = findViewById(R.id.giveUploadName);
        button = findViewById(R.id.btnSubmit);
        btn = findViewById(R.id.btnDone);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setVisibility(View.GONE);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("MyUploadsInter");
        button.setEnabled(false);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPdf();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = editText.getText().toString().trim();
                if (!newName.isEmpty() && selectedPdfUri != null) {
                    uploadFile(selectedPdfUri, newName);
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSave.setVisibility(View.VISIBLE);
                button.setVisibility(View.GONE);
                editText.setEnabled(true);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SendAcceptedStud.this, AdminDashboard.class);
                startActivity(intent);
            }
        });
    }


    private void selectPdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "PDF FILE SELECT "), 12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==12 && resultCode==RESULT_OK && data !=null && data.getData() !=null){
            button.setEnabled(true);
            editText.setText(data.getDataString()
                    .substring(data.getDataString().lastIndexOf("/")+1));
            String selectedFileName = editText.getText().toString();
            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Allow the user to edit the file name
                    editText.setFocusableInTouchMode(true);
                    editText.requestFocus();
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    uploadFile(data.getData(), selectedFileName);
                }
            });
        }
    }

    private void uploadFile(Uri data, String newName) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File......");
        progressDialog.show();

        StorageReference reference = storageReference.child("upload" + System.currentTimeMillis() + ".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri uri = uriTask.getResult();

                        String fileName = editText.getText().toString(); // get the file name from the EditText
                        PdfUploading pdfUploading = new PdfUploading(fileName,uri.toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(pdfUploading);
                        Toast.makeText(SendAcceptedStud.this,"Uploading Files ",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                        double progress=(100.0* snapshot.getBytesTransferred())/ snapshot.getTotalByteCount();
                        progressDialog.setMessage("File Uploading ..."+(int) progress+"%");

                    }
                });
    }
}