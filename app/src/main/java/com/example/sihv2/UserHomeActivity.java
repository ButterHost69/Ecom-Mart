package com.example.sihv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class UserHomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationViews);
        toolbar = findViewById(R.id.tool_bar);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.user_home_home)
                {
                    Toast.makeText(UserHomeActivity.this, "Home Open", Toast.LENGTH_SHORT).show();
                }

                else if(item.getItemId() == R.id.user_cart_home)
                {
                    Toast.makeText(UserHomeActivity.this, "Cart Open", Toast.LENGTH_SHORT).show();
                }

                else if(item.getItemId() == R.id.user_order_home)
                {
                    Toast.makeText(UserHomeActivity.this, "Order Open", Toast.LENGTH_SHORT).show();
                }

                else if(item.getItemId() == R.id.user_categories_home)
                {
                    Toast.makeText(UserHomeActivity.this, "Categories Open", Toast.LENGTH_SHORT).show();
                }

                else if(item.getItemId() == R.id.user_settings_home)
                {
                    Toast.makeText(UserHomeActivity.this, "Settings Open", Toast.LENGTH_SHORT).show();
                }

                else {
                    Toast.makeText(UserHomeActivity.this, "Nothing Working", Toast.LENGTH_SHORT).show();
                    return false;
                }
                return false;
            }
        });
    }
}