package com.addcontact.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.addcontact.R;
import com.addcontact.Util.Util;

/**
 * Created by Manish on 11/06/2015.
 */
public class Aboutus extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView versionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
        // Creating The Toolbar and setting it as the Toolbar for the activity

        versionText=(TextView)findViewById(R.id.version);
        System.out.println("version: " + Util.getApplicationVersionName(this));
        versionText.setText("v" + Util.getApplicationVersionName(this));

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.activity_slide_in_left, R.anim.activity_slide_out_left);
            }
        });

    }

}
