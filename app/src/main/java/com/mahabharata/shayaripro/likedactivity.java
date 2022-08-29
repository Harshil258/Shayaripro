package com.mahabharata.shayaripro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class likedactivity extends AppCompatActivity {

    RecyclerView recyclerview1;
    List<mainmodel> mainmodelList;
    secondadapter secondadapter;
    List<mainmodel> favoriteLists;
    public static boolean isinlike  = false;
    TextView noshayari;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likedactivity);
        isinlike = true;
        noshayari = findViewById(R.id.noshayari);

        recyclerview1 = findViewById(R.id.recyclerview2);
        favoriteLists = MainActivity.mydatabase.maindao().getalldata();
        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView imageView = findViewById(R.id.backk);
        imageView.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        TextView header = findViewById(R.id.header);
        header.setText("Favourite shayari");

        mainmodelList = new ArrayList<>();
        secondadapter = new secondadapter(mainmodelList,this);
        recyclerview1.setLayoutManager(new LinearLayoutManager(this));
        recyclerview1.setAdapter(secondadapter);
        Log.d("rshgerhger",String.valueOf(mainmodelList.size()));

        List<mainmodel> favoriteLists= MainActivity.mydatabase.maindao().getalldata();



       for (int i = 0; i<= favoriteLists.size()-1; i++){
           noshayari.setVisibility(View.INVISIBLE);
           mainmodelList.add(new mainmodel(favoriteLists.get(i).getId(),favoriteLists.get(i).getCategory(),favoriteLists.get(i).getShayari(),true));
       }


//        if(favoriteLists.size()==0){
//            mainmodel = new mainmodel(key[0].substring(1),category,key[1].substring(0, key[1].length() -1 ),false);
//
//        }else {
//            for (int i = 0; i <favoriteLists.size() ; i++) {
//                if(favoriteLists.get(i).getId().equalsIgnoreCase(key[0].substring(1))){
//                    mainmodel = new mainmodel(key[0].substring(1),category,key[1].substring(0, key[1].length() -1 ),true);
//                }else {
//                    mainmodel = new mainmodel(key[0].substring(1),category,key[1].substring(0, key[1].length() -1 ),false);
//                }
//            }
//        }


        secondadapter.notifyDataSetChanged();
    }
}