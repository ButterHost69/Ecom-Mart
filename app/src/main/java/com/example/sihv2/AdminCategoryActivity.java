package com.example.sihv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AdminCategoryActivity extends AppCompatActivity {

    private TextView constructionMaterialView;
    private TextView electronicsView;
    private TextView machineryView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        constructionMaterialView = (TextView) findViewById(R.id.constructinMaterialView);
        electronicsView = (TextView) findViewById(R.id.electronicsView);
        machineryView = (TextView) findViewById(R.id.machineryView);

        constructionMaterialView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "constructionMaterial");
                startActivity(intent);
            }
        });

        electronicsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "electronics");
                startActivity(intent);
            }
        });

        machineryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "machinery");
                startActivity(intent);
            }
        });
    }
}