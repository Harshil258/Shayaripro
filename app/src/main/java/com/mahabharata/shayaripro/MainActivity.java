package com.mahabharata.shayaripro;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    ArrayList<mainmodel> mainmodelList;
    private DrawerLayout drawer;
    NavigationView navigationView;
    public static mydatabase mydatabase;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress);

        mydatabase = Room.databaseBuilder(getApplicationContext(), mydatabase.class, "mydatabase").allowMainThreadQueries().build();

        Toolbar toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setOnMenuItemClickListener(this);
        TextView header = findViewById(R.id.header);
        header.setText("Shayari for 2022");

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("mainnode");

        mainmodelList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        mainAdapter = new MainAdapter(mainmodelList, this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(mainAdapter);



        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                progressBar.setVisibility(View.GONE);

                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        Log.d("agwrgaewr", String.valueOf(dataSnapshot));
                        String category = dataSnapshot.getKey();
                        Log.d("categoryfssf", category);


                        DataSnapshot s = snapshot.child("category");
                        mainmodelList.add(new mainmodel(category));
                        Log.d("categoryfssf", category);

                    }
                    mainAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.Home) {
                    drawer.closeDrawer(GravityCompat.START);
                    Toast.makeText(MainActivity.this, "This is Home", Toast.LENGTH_SHORT).show();

                } else if (id == R.id.Share) {
                    drawer.closeDrawer(GravityCompat.START);
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Hey check out this Application: \n");
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                } else if (id == R.id.policy) {
                    drawer.closeDrawer(GravityCompat.START);
                    makedialog();
                }
                return true;
            }
        });

    }

    private void makedialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle("DISCLAIMER");
        alertDialogBuilder.setMessage("This app is made by Ayush Vaishnav.  \n \n   Thank you");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(MainActivity.this, "Thanks For Read DISCLAIMER", Toast.LENGTH_LONG).show();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            showdialog();
        }
    }

    private void showdialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.exitdialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        Button yes = dialog.findViewById(R.id.yesbtn);
        Button cancel = dialog.findViewById(R.id.nobtn);
        Button submit = dialog.findViewById(R.id.submitbtn);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                MainActivity.this.finish();
                System.exit(0);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Uri uri = Uri.parse("market://details?id=" + getPackageName() + "");
                    Intent goMarket = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(goMarket);
                } catch (ActivityNotFoundException e) {
                    Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName() + "");
                    Intent goMarket = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(goMarket);
                }
            }
        });
        dialog.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.liked:
                startActivity(new Intent(MainActivity.this, likedactivity.class));
                return true;
        }
        return false;
    }
}