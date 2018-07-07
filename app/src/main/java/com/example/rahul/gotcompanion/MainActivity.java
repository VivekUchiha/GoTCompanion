package com.example.rahul.gotcompanion;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rahul.gotcompanion.Database.DatabaseHelper;
import com.example.rahul.gotcompanion.Rest.ApiClient;
import com.example.rahul.gotcompanion.Rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Displays a {@link ViewPager} where each page shows a different day of the week.
 */
public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity);


        db = new DatabaseHelper(this);

        final ArrayList<String> list = db.getList();

        if (list.size() == 0) {
            ConnectivityManager cm =
                    (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
            if (isConnected) {
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setTitle("Please wait");
                mProgressDialog.setMessage("Initialising...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.show();
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<List<Data>> exampleCall = apiService.getAll();
                exampleCall.enqueue(new Callback<List<Data>>() {
                    @Override
                    public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                        List<Data> list1 = response.body();
                        int size = list1.size();
                        for (int i = 0 ; i < size ; i++ ){
                            db.insertData(list1.get(i).getName());
                            list.add(list1.get(i).getName());
                        }
                        Log.i("Tag","six] is "+size);
                        mProgressDialog.cancel();
                        Toast.makeText(getApplicationContext(),"Restart Application", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<List<Data>> call, Throwable t) {

                    }
                });
            }
        }
        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        // Connect the tab layout with the view pager.

        tabLayout.setupWithViewPager(viewPager);
    }
}
