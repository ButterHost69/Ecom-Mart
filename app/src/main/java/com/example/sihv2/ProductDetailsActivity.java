package com.example.sihv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sihv2.Model.Products;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductDetailsActivity extends AppCompatActivity {

    private ImageView product_details_image;
    private TextView product_details_name, product_details_price, product_details_categories, product_details_description;
    private String productId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);


        product_details_image = findViewById(R.id.product_details_image);
        product_details_name = findViewById(R.id.product_details_name);
        product_details_price = findViewById(R.id.product_details_price);
        product_details_categories = findViewById(R.id.product_details_categories);
        product_details_description = findViewById(R.id.product_details_description);

        productId = getIntent().getStringExtra("pid");
        getProductDetails(productId);
    }

    private void getProductDetails(String productId) {

        DatabaseReference productsInfoRef = FirebaseDatabase.getInstance().getReference().child("Products");
        productsInfoRef.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Products products = snapshot.getValue(Products.class);

                    Picasso.get().load(products.getImage()).into(product_details_image);
                    product_details_name.setText(products.getPname());
                    product_details_price.setText(products.getPrice());
                    product_details_categories.setText(products.getCategory());
                    product_details_description.setText(products.getDescription());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}