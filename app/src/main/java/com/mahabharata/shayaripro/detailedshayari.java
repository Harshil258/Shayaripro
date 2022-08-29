package com.mahabharata.shayaripro;

import static com.mahabharata.shayaripro.likedactivity.isinlike;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class detailedshayari extends AppCompatActivity {

    RecyclerView recyclerview1;
    List<mainmodel> mainmodelList;
    secondadapter secondadapter;
    String category;
    NavigationView navigationView;

    List<mainmodel> favoriteLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailedshayari);
        recyclerview1 = findViewById(R.id.recyclerview1);
        category = common.categoryName;

        favoriteLists = MainActivity.mydatabase.maindao().getalldata();


        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView imageView = findViewById(R.id.backk);
        imageView.setVisibility(View.VISIBLE);
        TextView header = findViewById(R.id.header);
        header.setText(category);
        navigationView = findViewById(R.id.navigation_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        isinlike = false;


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance();

        mainmodelList = new ArrayList<>();
        secondadapter = new secondadapter(mainmodelList, this);
        recyclerview1.setLayoutManager(new LinearLayoutManager(this));
        recyclerview1.setAdapter(secondadapter);

        DatabaseReference myRef = database.getReference("mainnode").child(common.categoryName);
        Toast.makeText(this, category, Toast.LENGTH_LONG).show();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot != null) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        String s = dataSnapshot.getValue().toString();

                        String[] key = s.split("=");
                        key[0].replace("{", " ");
                        key[1].replace("}", "");

                        mainmodel mainmodel = null;
                        if (favoriteLists.size() == 0) {
                            mainmodel = new mainmodel(key[0].substring(1), category, key[1].substring(0, key[1].length() - 1), false);

                        } else {
                            for (int i = 0; i < favoriteLists.size(); i++) {
                                if (favoriteLists.get(i).getId().equalsIgnoreCase(key[0].substring(1))) {
                                    mainmodel = new mainmodel(key[0].substring(1), category, key[1].substring(0, key[1].length() - 1), true);
                                } else {
                                    mainmodel = new mainmodel(key[0].substring(1), category, key[1].substring(0, key[1].length() - 1), false);
                                }
                            }
                        }

                        mainmodelList.add(mainmodel);
                        Collections.shuffle(mainmodelList);
                    }
                    secondadapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}