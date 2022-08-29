package com.mahabharata.shayaripro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Random;

public class add extends AppCompatActivity {

    EditText key,shayari;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Random rand = new Random();
        int key1 = rand.nextInt(1000000000);



        key = findViewById(R.id.key);
//        key.setText(key1);
        shayari = findViewById(R.id.shayari);
        submit = findViewById(R.id.submitbtn);

        String category = common.category;


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shayari.getText().toString().isEmpty()){

                    Toast.makeText(add.this, "enter text", Toast.LENGTH_SHORT).show();
                }else {

                    DatabaseReference myRef = database.getReference("mainnode").child(common.categoryName);
//                    DatabaseReference myRef = database.getReference("mainnode").child("Angry");


                    HashMap map = new HashMap();
                    map.put(key.getText().toString(), shayari.getText().toString());
//
                    myRef.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(add.this, "added", Toast.LENGTH_SHORT).show();
                                finish();
                                Log.d("srhrhswerthsetr", "done");

                            } else {
                                Log.d("srhrhswerthsetr", "error");

                            }
                        }
                    });

                }
            }
        });

        Log.d("egwrgwer",String.valueOf(key1));

        key.setText(String.valueOf(key1));



    }
}