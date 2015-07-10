package com.addcontact.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.addcontact.R;
import com.addcontact.adapter.ApplicationAdapter;
import com.addcontact.adapter.ApplicationAdapter2;
import com.addcontact.adapter.MyRecyclerViewAdapter;
import com.addcontact.application.cAapplication;
import com.addcontact.itemanimator.CustomItemAnimator;
import com.addcontact.models.DataObject;

import java.util.ArrayList;

public class PersonSearchActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewFragement";
    private ArrayList<DataObject> _list;
    private ApplicationAdapter2 mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_search);
        // Creating The Toolbar and setting it as the Toolbar for the activity

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
         if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(PersonSearchActivity.this);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search
            Log.e("ContactAdder", "Search text: " + query);

            _list= cAapplication.getInstance().getDBAdapter().getAllContact(query);
            if(_list.size()==0){
                final Toast toast = Toast.makeText(PersonSearchActivity.this, "No Person Added", Toast.LENGTH_SHORT);
                toast.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 3000);
            }

            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new CustomItemAnimator());
            //_list= cAapplication.getInstance().getDBAdapter().getAllContact(query);
            mAdapter = new ApplicationAdapter2(_list, R.layout.card_view_row,PersonSearchActivity.this);
            mRecyclerView.setAdapter(mAdapter);

            ((ApplicationAdapter2) mAdapter).setOnItemClickListener(new ApplicationAdapter2.MyClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Log.i(LOG_TAG, " Clicked on Item " + position);
                    Intent i = new Intent(PersonSearchActivity.this, DetailActivity.class);
                    i.putExtra("UserId", _list.get(position).getId());
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_activity_in_right, R.anim.slide_activity_out_right);
                }
            });
        }
    }

}
