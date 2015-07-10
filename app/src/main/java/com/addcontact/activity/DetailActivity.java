package com.addcontact.activity;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.addcontact.R;
import com.addcontact.application.cAapplication;


import java.util.Date;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final int SCALE_DELAY = 30;
    private LinearLayout rowContainer;
    private int UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle b=getIntent().getExtras();
        UserId=b.getInt("UserId");

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Person Details");
        // Handle Back Navigation :D
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.this.onBackPressed();
                overridePendingTransition(R.anim.activity_slide_in_left, R.anim.activity_slide_out_left);
            }
        });

        List<String> _list= cAapplication.getInstance().getDBAdapter().getPersonDetail(UserId);

        // Row Container
        rowContainer = (LinearLayout) findViewById(R.id.row_container);

       //getWindow().getEnterTransition().removeListener(this);

        for (int i = 0; i < rowContainer.getChildCount(); i++) {
            View rowView = rowContainer.getChildAt(i);
            rowView.animate().setStartDelay(100 + i * SCALE_DELAY).scaleX(1).scaleY(1);
        }

        if (_list != null) {

            String Name=_list.get(0)+" "+_list.get(1)+" "+_list.get(2);
            View view = rowContainer.findViewById(R.id.row_name);
            fillRow(view, "Name", Name);

            view = rowContainer.findViewById(R.id.row_slipno);
            String slipNo=TextUtils.isEmpty(_list.get(10))? "N/A" : _list.get(10);
            fillRow(view, "SlipNo", slipNo);

            view = rowContainer.findViewById(R.id.row_customerid);
            String CusId=TextUtils.isEmpty(_list.get(11))? "N/A" : _list.get(11);
            fillRow(view, "CustomerId", CusId);

            view = rowContainer.findViewById(R.id.row_category);
            String Category=TextUtils.isEmpty(_list.get(12))? "N/A" : _list.get(12);
            fillRow(view, "Category", Category);

            view = rowContainer.findViewById(R.id.row_year);
            String year=TextUtils.isEmpty(_list.get(13))? "N/A" : _list.get(13);
            fillRow(view, "Year", year);

            view = rowContainer.findViewById(R.id.row_price);
            String price=TextUtils.isEmpty(_list.get(14))? "N/A" : _list.get(14);
            fillRow(view, "Price", price);

            view = rowContainer.findViewById(R.id.row_mobile);
            fillRow(view, "Mobile",TextUtils.isEmpty(_list.get(3))? "N/A" : _list.get(3));

            view = rowContainer.findViewById(R.id.row_address);
            String full_address=_list.get(4)+" "+_list.get(5)+" "+_list.get(6)+" "+_list.get(7)+" "+_list.get(8);
            fillRow(view, "Address", full_address);

            view = rowContainer.findViewById(R.id.row_authoperson);
            fillRow(view, "AuthorisePerson",TextUtils.isEmpty(_list.get(15))? "N/A" : _list.get(15));

            view = rowContainer.findViewById(R.id.row_datetime);
            String dateTime=_list.get(9);
            fillRow(view, "DateTime", dateTime);

         }
    }



    public void fillRow(View view, final String title, final String description) {
        TextView titleView = (TextView) view.findViewById(R.id.title);
        titleView.setText(title);

        TextView descriptionView = (TextView) view.findViewById(R.id.description);
        descriptionView.setText(description);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("AppInfo", description);
                clipboard.setPrimaryClip(clip);

                Snackbar.with(getApplicationContext()).dismiss();
                Snackbar.with(getApplicationContext()) // context
                        .text("Copied " + title) // text to display
                        .show(DetailActivity.this);*/
            }
        });
    }

    @Override
    public void onBackPressed() {

        for (int i = rowContainer.getChildCount()-1; i >= 0; i--) {

            View rowView = rowContainer.getChildAt(i);
            ViewPropertyAnimator propertyAnimator = rowView.animate().setStartDelay((rowContainer.getChildCount() - 1 - i) * SCALE_DELAY)
                    .scaleX(0).scaleY(0);

            propertyAnimator.setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        finishAfterTransition();
                    } else {
                        finish();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
        }
    }
}
