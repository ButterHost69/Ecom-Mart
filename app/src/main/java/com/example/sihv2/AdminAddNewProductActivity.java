package com.example.sihv2;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private String categoryName;
    private String s_productName, s_productDescription, s_price, saveCurrentDate, saveCurrentTime;
    private String productRandomKey, downloadImageUrl;
    private ProgressDialog loadingBar;

    private DatabaseReference ProductRef;
    private StorageReference productImageRef;
    ImageView productImage;

    EditText productName;
    EditText productDescription;
    EditText price;
    Button addProductButton;

    private Uri ImageUri;

    private static final int GALLERY_PICK = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        categoryName = getIntent().getExtras().get("category").toString();
        productImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");
        this.loadingBar = new ProgressDialog(AdminAddNewProductActivity.this);

        productImage = (ImageView) findViewById(R.id.productImage);

        productName = (EditText) findViewById(R.id.productName);
        productDescription = (EditText) findViewById(R.id.productDescription);
        price = (EditText) findViewById(R.id.price);

        addProductButton = (Button) findViewById(R.id.addProductButton);

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }
        });

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ValidateProductData();
            }
        });

    }

    private void ValidateProductData()
    {
        s_productName = productName.getText().toString();
        s_productDescription = productDescription.getText().toString();
        s_price = price.getText().toString();

        if(ImageUri == null)
        {
            Toast.makeText(AdminAddNewProductActivity.this, "Please Provide an Image !" , Toast.LENGTH_SHORT).show();
        }

        else if(s_productName == null)
        {
            Toast.makeText(AdminAddNewProductActivity.this, "Please Provide a Product Name !" , Toast.LENGTH_SHORT).show();
        }

        else if(s_productDescription == null)
        {
            Toast.makeText(AdminAddNewProductActivity.this, "Please Provide a Description !" , Toast.LENGTH_SHORT).show();
        }

        else if(s_price == null)
        {
            Toast.makeText(AdminAddNewProductActivity.this, "Please Provide a Price !" , Toast.LENGTH_SHORT).show();
        }

        else
        {
            StoreProductInformation();
        }
    }

    private void StoreProductInformation()
    {
        loadingBar.setTitle("Adding Your Product");
        loadingBar.setMessage("Please Wait Adding Your Prodcut");
        loadingBar.setCanceledOnTouchOutside(false);

        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy ");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        StorageReference filePath = productImageRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String fMessage = e.getMessage().toString();
                Toast.makeText(AdminAddNewProductActivity.this, "Error Occured: " +fMessage, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Toast.makeText(AdminAddNewProductActivity.this, "Image Added Successfully", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if(!task.isSuccessful())
                        {

                            throw task.getException();
                        }
                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return  filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if(task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();
                            //Toast.makeText(AdminAddNewProductActivity.this, "Got Image URL...", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void SaveProductInfoToDatabase()
    {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("description", s_productDescription);
        productMap.put("image", downloadImageUrl);
        productMap.put("category", categoryName);
        productMap.put("price", s_price);
        productMap.put("pname", s_productName);

        ProductRef.child(productRandomKey).updateChildren(productMap).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            Intent intent = new Intent(AdminAddNewProductActivity.this, AdminCategoryActivity.class);
                            Toast.makeText(AdminAddNewProductActivity.this, "Product Added Successfully", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            startActivity(intent);
                        }
                        else
                        {
                            String message = task.getException().toString();
                            Toast.makeText(AdminAddNewProductActivity.this, "Error Occurred: " + message, Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                });
    }

    private void OpenGallery()
    {
        Intent galleryIntent  = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_PICK && resultCode==RESULT_OK && data != null)
        {
            ImageUri = data.getData();
            productImage.setImageURI(ImageUri);
        }
    }
}