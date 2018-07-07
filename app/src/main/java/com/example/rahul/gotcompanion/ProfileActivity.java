package com.example.rahul.gotcompanion;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahul.gotcompanion.Database.ProfileContract;
import com.example.rahul.gotcompanion.Rest.ApiClient;
import com.example.rahul.gotcompanion.Rest.ApiInterface;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.rahul.gotcompanion.R.drawable.ic_favorite_black;

public class ProfileActivity extends AppCompatActivity {

    String name,title, culture, house, spouse, books, imgLink, gender, slug;
    String api_id;
    Double rank;
    String favourite = "false";
    ImageView image;
    TextView genderField, titleField, houseField, spouseField, booksField, cultureField, rankField;
    FloatingActionButton fab;
    private Context mContext = ProfileActivity.this;
    private static final int REQUEST = 112;
    ProgressDialog mProgressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        image = findViewById(R.id.image);
        setSupportActionBar(toolbar);
        houseField = findViewById(R.id.houseTextView);
        genderField = findViewById(R.id.genderTextView);
        titleField = findViewById(R.id.titleTextView);
        spouseField = findViewById(R.id.spouseTextView);
        booksField = findViewById(R.id.booksTextView);
        cultureField = findViewById(R.id.cultureTextView);
        rankField = findViewById(R.id.rankTextView);
        fab = findViewById(R.id.fab);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Please wait");
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();

        Bundle b =  getIntent().getExtras();
        name = b.getString("current");
        setTitle(name);

        Uri uri = ProfileContract.ProfileEntry.CONTENT_URI;
        String where = "name = ?";
        String[] whereArgs = new String[] {
                name };

        Cursor cursor = getContentResolver().query(uri,null, where,whereArgs,null );
        if( cursor.getCount() != 0 ){
            cursor.moveToFirst();
            name = cursor.getString(cursor.getColumnIndex(ProfileContract.ProfileEntry.NAME));
            title = cursor.getString(cursor.getColumnIndex(ProfileContract.ProfileEntry.TITLES));
            house = cursor.getString(cursor.getColumnIndex(ProfileContract.ProfileEntry.HOUSE));
            culture = cursor.getString(cursor.getColumnIndex(ProfileContract.ProfileEntry.CULTURE));
            spouse = cursor.getString(cursor.getColumnIndex(ProfileContract.ProfileEntry.SPOUSE));
            books = cursor.getString(cursor.getColumnIndex(ProfileContract.ProfileEntry.BOOKS));
            api_id = cursor.getString(cursor.getColumnIndex(ProfileContract.ProfileEntry.APIID));
            rank = cursor.getDouble(cursor.getColumnIndex(ProfileContract.ProfileEntry.RANK));
            gender = cursor.getString(cursor.getColumnIndex(ProfileContract.ProfileEntry.MALE));
            imgLink = cursor.getString(cursor.getColumnIndex(ProfileContract.ProfileEntry.IMAGELINK));
            slug = cursor.getString(cursor.getColumnIndex(ProfileContract.ProfileEntry.SLUG));
            favourite = cursor.getString(cursor.getColumnIndex(ProfileContract.ProfileEntry.FAVOURITE));

            Picasso.get().load(imgLink).into(image);

            if(title != null)
                titleField.setText(title);
            else
                titleField.setText("N/A");

            if(gender != null)
            {
                if(gender.equals("1"))
                    genderField.setText("male");
                else
                    genderField.setText("female");
            }
            else
                genderField.setText("N/A");

            if(culture != null)
                cultureField.setText(culture);
            else
                cultureField.setText("N/A");

            if(house != null)
                houseField.setText(house);
            else
                houseField.setText("N/A");

            if(rank != 0)
                rankField.setText(String.valueOf(rank));
            else
                rankField.setText("N/A");

            if(books != null)
                booksField.setText(books);
            else
                booksField.setText("N/A");

            if(spouse != null)
                spouseField.setText(spouse);
            else
                spouseField.setText("N/A");
            mProgressDialog.dismiss();
        }

        else {
            ConnectivityManager cm =
                    (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            if (isConnected) {
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<Example> exampleCall = apiService.getCharacter(name);
                exampleCall.enqueue(new Callback<Example>()

                {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {
                        Data data = response.body().getData();

                        name = data.getName();
                        culture = data.getCulture();
                        spouse = data.getSpouse();
                        house = data.getHouse();
                        imgLink = data.getImageLink();
                        slug = data.getSlug();
                        title = get(data.getTitles());
                        books = get(data.getBooks());
                        api_id = data.getId();
                        rank = data.getPageRank();
                        if(data.getMale())
                            gender = "male";
                        else
                            gender = "female";

                        favourite = "false";

                        if(title != null)
                            titleField.setText(title);
                        else
                            titleField.setText("N/A");

                        if(gender != null)
                        {
                            if(gender.equals("1"))
                                genderField.setText("male");
                            else
                                genderField.setText("female");
                        }
                        else
                            genderField.setText("N/A");

                        if(culture != null)
                            cultureField.setText(culture);
                        else
                            cultureField.setText("N/A");

                        if(house != null)
                            houseField.setText(house);
                        else
                            houseField.setText("N/A");

                        if(rank != 0)
                            rankField.setText(String.valueOf(rank));
                        else
                            rankField.setText("N/A");

                        if(books != null)
                            booksField.setText(books);
                        else
                            booksField.setText("N/A");

                        if(spouse != null)
                            spouseField.setText(spouse);
                        else
                            spouseField.setText("N/A");

                        if(imgLink!=null){
                            if (Build.VERSION.SDK_INT >= 23) {
                                String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
                                if (!hasPermissions(mContext, PERMISSIONS)) {
                                    ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS, REQUEST );
                                } else {
                                    imageDownload imaaage = new imageDownload(getApplicationContext(), image);
                                    imaaage.execute("https://api.got.show" + imgLink);
                                }
                            } else {
                                imageDownload imaaage = new imageDownload(getApplicationContext(), image);
                                imaaage.execute("https://api.got.show" + imgLink);
                            }
                        }
                        else {mProgressDialog.cancel();

                            ContentValues values = new ContentValues();
                            values.put(ProfileContract.ProfileEntry.NAME, name);
                            values.put(ProfileContract.ProfileEntry.APIID, api_id);
                            values.put(ProfileContract.ProfileEntry.RANK, rank);
                            values.put(ProfileContract.ProfileEntry.TITLES, title);
                            values.put(ProfileContract.ProfileEntry.CULTURE, culture);
                            values.put(ProfileContract.ProfileEntry.MALE, gender);
                            values.put(ProfileContract.ProfileEntry.HOUSE, house);
                            values.put(ProfileContract.ProfileEntry.SPOUSE, spouse);
                            values.put(ProfileContract.ProfileEntry.BOOKS, books);
                            values.put(ProfileContract.ProfileEntry.IMAGELINK, (String) null);
                            values.put(ProfileContract.ProfileEntry.SLUG, slug);
                            values.put(ProfileContract.ProfileEntry.FAVOURITE, favourite);

                            Uri newUri = getContentResolver().insert(ProfileContract.ProfileEntry.CONTENT_URI, values);
                            // Show a toast message depending on whether or not the insertion was successful
                            if (newUri == null) {
                                // If the new content URI is null, then there was an error with insertion.
                                Toast.makeText(getApplicationContext(), "Error saving data.", Toast.LENGTH_SHORT).show();
                            } else {
                                // Otherwise, the insertion was successful and we can display a toast.
                                Toast.makeText(getApplicationContext(), "Data Saved.", Toast.LENGTH_SHORT).show();
                            }
                        }}


                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {
                        Log.i("Failed", t.getMessage());
                    }
                });
            } else {
                Toast.makeText(this, "Not connected to internet", Toast.LENGTH_SHORT).show();
            }
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String where = "name = ?";
                String[] whereClause = {name};

                if(favourite.equals("true")){
                    favourite = "false";
                }
                else favourite = "true";

                ContentValues values = new ContentValues();
                values.put(ProfileContract.ProfileEntry.FAVOURITE,favourite);
                long no = getContentResolver().update(ProfileContract.ProfileEntry.CONTENT_URI, values, where, whereClause);
                if (no == 0) {
                    // If the new content URI is null, then there was an error with insertion.
                    Toast.makeText(getApplicationContext(), "Error with updating favourites", Toast.LENGTH_SHORT).show();
                } else {
                    // Otherwise, the insertion was successful and we can display a toast.
                    Toast.makeText(getApplicationContext(), "Favourites List updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String get(List<String> list) {
        String result ="";
        if(list.size() != 0){
            for (int i = 0; i<list.size(); i++)
                result = result + list.get(i) +", ";
            return result.substring(0,result.length()-2);
        }
        else
            return result;
    }

    class imageDownload extends AsyncTask<String, Integer, Bitmap> {
        Context context;
        ImageView imageView;
        InputStream in = null;
        //constructor.
        public imageDownload(Context context, ImageView imageView) {
            this.context = context;
            this.imageView = imageView;
        }
        @Override
        protected void onPreExecute() {


        }
        @Override
        protected Bitmap doInBackground(String... params) {

            try {
                java.net.URL uuuu = new java.net.URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) uuuu
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(Bitmap data) {
            URI a =storeImage(data);
            Picasso.get().load(a.toString()).into(image);
            mProgressDialog.dismiss();


            ContentValues values = new ContentValues();
            values.put(ProfileContract.ProfileEntry.NAME, name);
            values.put(ProfileContract.ProfileEntry.APIID, api_id);
            values.put(ProfileContract.ProfileEntry.RANK, rank);
            values.put(ProfileContract.ProfileEntry.TITLES, title);
            values.put(ProfileContract.ProfileEntry.CULTURE, culture);
            values.put(ProfileContract.ProfileEntry.MALE, gender);
            values.put(ProfileContract.ProfileEntry.HOUSE, house);
            values.put(ProfileContract.ProfileEntry.SPOUSE, spouse);
            values.put(ProfileContract.ProfileEntry.BOOKS, books);
            values.put(ProfileContract.ProfileEntry.IMAGELINK, a.toString());
            values.put(ProfileContract.ProfileEntry.SLUG, slug);
            values.put(ProfileContract.ProfileEntry.FAVOURITE, favourite);

            Uri newUri = getContentResolver().insert(ProfileContract.ProfileEntry.CONTENT_URI, values);
            // Show a toast message depending on whether or not the insertion was successful
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(getApplicationContext(), "Error saving data.", Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(getApplicationContext(), "Data Saved.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private URI storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d("TAG",
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return null;
        }
        try {
            if(image != null){
                FileOutputStream fos = new FileOutputStream(pictureFile);
                image.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.close();
            }
        } catch (FileNotFoundException e) {
            Log.d("TAG", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("TAG", "Error accessing file: " + e.getMessage());
        }
        return pictureFile.toURI();
    }

    /** Create a File for saving an image or video */
    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.i("Directory doest","exists");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    imageDownload imaaage = new imageDownload(getApplicationContext(), image);
                    imaaage.execute("https://api.got.show" + imgLink);
                } else {
                    Toast.makeText(mContext, "The app was not allowed to write in your storage", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}
