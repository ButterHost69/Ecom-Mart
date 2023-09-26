package com.example.sihv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sihv2.Model.Users;
import com.example.sihv2.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

//import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private String parentDBName = "Users";

    private EditText phoneNumber;
    private EditText password;
    private Button loginButton;
    private Button registerButton;

    private TextView ifSeller;
    private TextView ifBuyer;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);
        ifSeller = (TextView) findViewById(R.id.ifSeller);
        ifBuyer = (TextView) findViewById(R.id.ifBuyer);

        this.loadingBar = new ProgressDialog(MainActivity.this);
        //rememberMeCheckBox = (CheckBox) findViewById(R.id.rememberMeCheckBox);
//
//
//        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
//        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);
//
//        if(UserPhoneKey != "" && UserPasswordKey != "")
//        {
//            if(!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey))
//            {
//                allowAccessToAccount(UserPhoneKey,UserPasswordKey);
//            }
//        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        password = (EditText) findViewById(R.id.password);
        //rememberMeCheckBox = (CheckBox) findViewById(R.id.rememberMeCheckBox);

        ifSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.setText("Login As Seller");
                ifSeller.setVisibility(View.INVISIBLE);
                ifBuyer.setVisibility(View.VISIBLE);
                parentDBName = "Admins";
            }
        });

        ifBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.setText("Login");
                ifSeller.setVisibility(View.VISIBLE);
                ifBuyer.setVisibility(View.INVISIBLE);
                parentDBName = "Users";
            }
        });
        //Paper.init(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String s_phoneNumber = phoneNumber.getText().toString();
        String s_password = password.getText().toString();

        if(TextUtils.isEmpty(s_phoneNumber))
        {
            Toast.makeText(this, "Phone Number is Empty", Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(s_password))
        {
            Toast.makeText(this, "Password is Empty", Toast.LENGTH_SHORT).show();

        }
        else
        {
            loadingBar.setTitle("Signing In");
            loadingBar.setMessage("Checking Your Credentials");
            loadingBar.setCanceledOnTouchOutside(false);

            loadingBar.show();

            allowAccessToAccount(s_phoneNumber, s_password);
        }

    }

    private void allowAccessToAccount(String s_phoneNumber, String s_password) {

//        if(remeberMeCheckBox.isChecked())
//        {
//            Paper.book().write(Prevalent.UserPhoneKey, s_phoneNumber);
//            Paper.book().write(Prevalent.UserPasswordKey, s_password);
//        }

        DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.child(parentDBName).child(s_phoneNumber).exists())
                {
                    Users usersData = snapshot.child(parentDBName).child(s_phoneNumber).getValue(Users.class);
                    if(usersData.getPhone().equals(s_phoneNumber))
                    {
                        //Toast.makeText(MainActivity.this, "Logi In Successfully", Toast.LENGTH_SHORT).show();
                        if(usersData.getPassword().equals(s_password))
                        {
                            if(parentDBName.equals("Admins"))
                            {
                                Toast.makeText(MainActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(MainActivity.this, AdminCategoryActivity.class);
                                startActivity(intent);
                            }
                            else if(parentDBName.equals("Users")){
                                Toast.makeText(MainActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(MainActivity.this, UserHomeActivity.class);
                                startActivity(intent);
                            }
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "This Phone Does Not Exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}