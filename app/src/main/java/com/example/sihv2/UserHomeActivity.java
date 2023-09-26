package com.example.sihv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sihv2.Model.Products;
import com.example.sihv2.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;
import com.squareup.picasso.Picasso;

public class UserHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DatabaseReference ProductsRef;
    private RecyclerView recycler_view;
    RecyclerView.LayoutManager layoutManager;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        recycler_view = findViewById(R.id.recycler_view);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationViews);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar = findViewById(R.id.tool_bar);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        recycler_view.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Your code here

        Toast.makeText(UserHomeActivity.this, "123 Success We Have Come So Far", Toast.LENGTH_SHORT).show();
        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>().
                        setQuery(ProductsRef, Products.class).
                        build();


        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model)
                    {
                        Toast.makeText(UserHomeActivity.this, "Test2 Success We Have Come So Far", Toast.LENGTH_SHORT).show();
                        holder.product_name.setText(model.getPname());
                        holder.product_description.setText(model.getDescription());
                        holder.product_price.setText("Price: "+ model.getPrice());
                        Toast.makeText(UserHomeActivity.this, "Success We Have Come So Far", Toast.LENGTH_SHORT).show();
                        //holder.product_price.setText(model.getImage());
                        //Picasso.get().load(model.getImage()).into(holder.getImage());
                    }


                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        Toast.makeText(UserHomeActivity.this, "Test3 Success We Have Come So Far", Toast.LENGTH_SHORT).show();
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };

        recycler_view.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public boolean onNavigationItemSelected (@NonNull MenuItem item)
    {
        int id = item.getItemId();
        Toast.makeText(UserHomeActivity.this, "Test4 Success We Have Come So Far", Toast.LENGTH_SHORT).show();
        if (id == R.id.user_cart_home)
        {
            Toast.makeText(UserHomeActivity.this, "Test4 Success We Have Come So Far", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.user_order_home)
        {
            Toast.makeText(UserHomeActivity.this, "Test4 Success We Have Come So Far", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.user_categories_home)
        {
            Toast.makeText(UserHomeActivity.this, "Test4 Success We Have Come So Far", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.user_settings_home)
        {
                //Intent intent = new Intent(UserHomeActivity.this, SettinsActivity.class);
                //startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
        }
    }

