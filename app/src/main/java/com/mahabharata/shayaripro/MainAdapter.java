package com.mahabharata.shayaripro;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.mahabharata.shayaripro.common.categoryName;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.Viewholder> {

    List<mainmodel> mainmodelList;
    Context context;

    public MainAdapter(List<mainmodel> mainmodelList, Context context) {
        this.mainmodelList = mainmodelList;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.onegrid, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryName = mainmodelList.get(position).category;
                Intent intent = new Intent(context, detailedshayari.class);
                Toast.makeText(context, "Tap on the shayari to change background", Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });

        holder.tv.setText(mainmodelList.get(position).getCategory());

        switch (mainmodelList.get(position).category) {

            case "Attitude":
                holder.imageView.setPadding(10,10,10,10);
                holder.imageView.setImageResource(R.drawable.ic_attitude);
                break;

            case "Angry":
                holder.imageView.setPadding(10,10,10,10);
                holder.imageView.setImageResource(R.drawable.ic_angry);
                break;

            case "sad":
                holder.imageView.setPadding(10,10,10,10);
                holder.imageView.setImageResource(R.drawable.ic_girl);
                break;

            case "Heart Touching Shayari":
                holder.imageView.setPadding(10,10,10,10);
                holder.imageView.setImageResource(R.drawable.ic_add_to_favourites);
                break;

            case "Bevafa":
                holder.imageView.setPadding(10,10,10,10);
                holder.imageView.setImageResource(R.drawable.ic_broken_heart);
                break;

            case "Bollywood Shayari":
                holder.imageView.setPadding(10,10,10,10);
                holder.imageView.setImageResource(R.drawable.ic_clapperboard);
                break;

            case "Brithday":
                holder.imageView.setPadding(10,10,10,10);
                holder.imageView.setImageResource(R.drawable.ic_birthday_cake);
                break;

            case "Broken Heart":
                holder.imageView.setPadding(10,10,10,10);
                holder.imageView.setImageResource(R.drawable.ic_broken_heart);
                break;

            case "Desh Bhakti Shayari":
                holder.imageView.setPadding(10,10,10,10);
                holder.imageView.setImageResource(R.drawable.ic_india);
                break;

            case "Dua":
                holder.imageView.setPadding(10,10,10,10);
                holder.imageView.setImageResource(R.drawable.ic_praying);
                break;

            case "Inspirational Shayari":
                holder.imageView.setPadding(10,10,10,10);
                holder.imageView.setImageResource(R.drawable.ic_lightbulb);
                break;

            case "Maa Shayari":
                holder.imageView.setPadding(10,10,10,10);
                holder.imageView.setImageResource(R.drawable.ic_maternity);
                break;

            case "Sharab Shayari":
                holder.imageView.setPadding(10,10,10,10);
                holder.imageView.setImageResource(R.drawable.ic_wine);
                break;


            case "इश्क शायरी":
                holder.imageView.setPadding(10,10,10,10);
                holder.imageView.setImageResource(R.drawable.ic_love);
                break;


            case "Friendship Shayari":
                holder.imageView.setPadding(10,10,10,10);
                holder.imageView.setImageResource(R.drawable.ic_friend);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mainmodelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imageView;
        TextView tv;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardview);
            imageView = itemView.findViewById(R.id.imageView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}

