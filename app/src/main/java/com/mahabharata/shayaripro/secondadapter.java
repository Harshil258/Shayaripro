package com.mahabharata.shayaripro;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.InterstitialAd;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static com.mahabharata.shayaripro.likedactivity.isinlike;

public class secondadapter extends RecyclerView.Adapter<secondadapter.Viewholder> {

    List<mainmodel> mainmodelList;
    Context context;
    private InterstitialAd interstitialAd;
    List<mainmodel> favoriteLists = MainActivity.mydatabase.maindao().getalldata();
    List<mainmodel> favlists;
    private int STORAGE_PERMISSION_CODE = 1;




    public secondadapter(List<mainmodel> mainmodelList, Context context) {
        this.mainmodelList = mainmodelList;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mainrow, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {





        Log.d("TqewrfeAG", mainmodelList.get(position).getShayari());

        String s = mainmodelList.get(position).getShayari();


        if (mainmodelList.get(position).isLike()) {
            holder.likee.setImageResource(R.drawable.ic_favorite_red);
            Log.d("Arherher", "aerherh");

        } else {
            holder.likee.setImageResource(R.drawable.ic_baseline_favorite_24);
        }


        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mainmodel mainList = new mainmodel();
                mainList.setCategory(mainmodelList.get(position).getCategory());
                mainList.setId(mainmodelList.get(position).getId());
                mainList.setShayari(s);
//                mainList.add(mainmodelList.get(position).getId(),mainmodelList.get(position)
//                        .getCategory(),mainmodelList.get(position).getShayari());


//                if (MainActivity.mydatabase.maindao().isFavorite(mainmodelList.get(position).getId()) != 1) {

                if (mainmodelList.get(position).isLike) {

                    try {
                        Toast.makeText(context, "unliked", Toast.LENGTH_SHORT).show();
                        holder.likee.setImageResource(R.drawable.ic_baseline_favorite_24);
//                    MainActivity.mydatabase.maindao().addData(mainList);

                        MainActivity.mydatabase.maindao().delete(mainList);
                        mainmodelList.get(position).isLike = false;
                    }catch (Exception e){
//                        Toast.makeText(context, String.valueOf(e), Toast.LENGTH_SHORT).show();


                    }

                    if (isinlike == true) {
                        mainmodelList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mainmodelList.size());
                        Toast.makeText(context, "liked", Toast.LENGTH_SHORT).show();


                    }


                } else {

                    MainActivity.mydatabase.maindao().addData(mainList);
                    holder.likee.setImageResource(R.drawable.ic_favorite_red);
                    mainmodelList.get(position).isLike = true;

                }

            }
        });
        holder.imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.imageView2.setBackgroundColor(getRandomColor());
            }
        });


        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    holder.tv_quotes_watermark.setVisibility(View.VISIBLE);
                    Bitmap bitmap = Bitmap.createBitmap(holder.downloadlayout.getWidth(), holder.downloadlayout.getHeight(),
                            Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    holder.downloadlayout.draw(canvas);

                    OutputStream fos;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        ContentResolver resolver = context.getContentResolver();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, System.currentTimeMillis() + ".jpg");
                        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
                        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
                        Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

                        Toast.makeText(context, "File Saved", Toast.LENGTH_SHORT).show();
//                        holder.tv_save_quote.setText("Saved");
//                        holder.iv_save_quote.setImageResource(R.drawable.ic_menu_check);

                        holder.saved.setText("Saved");
                        holder.isaved.setImageResource(R.drawable.ic_baseline_check_24);

                        try {
                            fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

                            fos.flush();
                            fos.close();
                            if (common.intercount < 30) {
                                if (common.intercount % 3 == 0) {
                                    loadinter();
                                }
                            }
                            common.intercount++;
                            Log.d("counterrrr", String.valueOf(common.intercount));


                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        holder.tv_quotes_watermark.setVisibility(View.INVISIBLE);
                    } else {

                        FileOutputStream outputStream = null;

                        File sdCard = Environment.getExternalStorageDirectory();

                        File directory = new File(sdCard.getAbsolutePath() + "/Latest Quotes");
                        directory.mkdir();

                        String filename = String.format("%d.jpg", System.currentTimeMillis());

                        File outFile = new File(directory, filename);

                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
                        holder.saved.setText("Saved");
                        holder.isaved.setImageResource(R.drawable.ic_baseline_check_24);


                        try {
                            outputStream = new FileOutputStream(outFile);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

                            outputStream.flush();
                            outputStream.close();

                            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            intent.setData(Uri.fromFile(outFile));
                            context.sendBroadcast(intent);

                            //////////////////////////////////////////////////////////

                            if (common.intercount < 30) {
                                if ( common.intercount % 3 == 0) {
                                    loadinter();
                                }

                            }
                            common.intercount++;
                            Log.d("counterrrr", String.valueOf(common.intercount));


//////////////////////////////////////////////////////////

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        holder.tv_quotes_watermark.setVisibility(View.INVISIBLE);

                    }

                } else {
                    //show permission popup
                    requestStoragePermission();
                }

            }
        });

        holder.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (common.intercount < 30 ) {

                    if ( common.intercount % 3 == 0) {
                        loadinter();
                    }

                }
                common.intercount++;
                Log.d("counterrrr", String.valueOf(common.intercount));


                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                CharSequence label;
                ClipData clipData = ClipData.newPlainText("This shayari is copied from  https://play.google.com/store/apps/details?id=com.mahabharata.shayaripro", s + "\n\n This shayari is copied from \n https://play.google.com/store/apps/details?id=com.mahabharata.shayaripro");
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(context, "Shayari copied", Toast.LENGTH_SHORT).show();

                Log.d("rgergerbg", String.valueOf(common.intercount));
                holder.copy.setEnabled(false);

            }
        });


        holder.maintv.setText('"' + " " + s + " " + '"');


    }


    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(context)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();

        } else {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public int getItemCount() {
        return mainmodelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView maintv,saved;
        LinearLayout like, download, copy;
        ImageView likee,isaved,imageView2;
        TextView tv_quotes_watermark;
        ConstraintLayout downloadlayout;


        public Viewholder(@NonNull View itemView) {
            super(itemView);

            maintv = itemView.findViewById(R.id.maintv);
            like = itemView.findViewById(R.id.like);
            likee = itemView.findViewById(R.id.likee);
            download = itemView.findViewById(R.id.download);
            copy = itemView.findViewById(R.id.copy);
            tv_quotes_watermark = itemView.findViewById(R.id.tv_quotes_watermark);
            downloadlayout = itemView.findViewById(R.id.downloadlayout);
            saved = itemView.findViewById(R.id.saved);
            isaved = itemView.findViewById(R.id.isaved);
            imageView2 = itemView.findViewById(R.id.imageView2);


        }
    }

    public int getRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    public void loadinter(){

        interstitialAd = new InterstitialAd(context, common.interstatial);
        // Create listeners for the Interstitial Ad
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e("interstatilalads", "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e("interstatilalads", "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e("counterrrr", "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d("counterrrr", "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d("interstatilalads", "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d("interstatilalads", "Interstitial ad impression logged!");
            }
        };

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }

}

