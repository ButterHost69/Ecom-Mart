package com.example.sihv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText name;
    private EditText phoneNumber;
    private EditText password;

    private ProgressDialog loadingBar;

    private Button register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name  = (EditText) findViewById(R.id.name);
        phoneNumber  = (EditText) findViewById(R.id.phoneNumber);
        password  = (EditText) findViewById(R.id.password);
        loadingBar = new ProgressDialog(this);

        register_button = (Button) findViewById(R.id.register_button);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
                //Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                //startActivity(intent);

            }
        });
    }

    private void createAccount()
    {
        String s_name = (name.getText()).toString();
        String s_phoneNumber = (phoneNumber.getText()).toString();
        String s_password = (password.getText()).toString();

        if(TextUtils.isEmpty(s_name))
        {
            Toast.makeText(this, "Username is Empty", Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(s_phoneNumber))
        {
            Toast.makeText(this, "Phone Number is Empty", Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(s_password))
        {
            Toast.makeText(this, "Password is Empty", Toast.LENGTH_SHORT).show();

        }
        else
        {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Creating Account! Might Take a While");
            loadingBar.setCanceledOnTouchOutside(false);

            loadingBar.show();
            ValidatePhoneNumber(s_name, s_phoneNumber, s_password);
        }
    }

    private void ValidatePhoneNumber(String s_name, String s_phoneNumber, String s_password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(!(snapshot.child("Users").child(s_phoneNumber).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("phone", s_phoneNumber);
                    userdataMap.put("name", s_name);
                    userdataMap.put("password", s_password);

                    RootRef.child("Users").child(s_phoneNumber).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this, "Regitered Successfully", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Toast.makeText(RegisterActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(RegisterActivity.this , "This Phone Number Already Exists", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}