package com.addcontact.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import com.addcontact.R;
import com.addcontact.Util.Util;
import com.addcontact.data.PreferencesHelper;
/**
 * Created by Manish on 2/28/2015.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;


public class SplashMain extends Activity{

    private final int SPLASH_DISPLAY_LENGHT = 2000;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        pd = new ProgressDialog(SplashMain.this, R.style.MyTheme);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.setCancelable(false);
        pd.show();

        if(PreferencesHelper.getLoginCheck()){

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pd.dismiss();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);

                    finish();
                    Util.setActivityAnimation(SplashMain.this,
                            R.anim.fade_in, R.anim.fade_out);

                }
            }, SPLASH_DISPLAY_LENGHT);


        }else{

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pd.dismiss();
                    Intent i = new Intent(getApplicationContext(), Login.class);
                    startActivity(i);

                    finish();
                    Util.setActivityAnimation(SplashMain.this,
                            R.anim.fade_in, R.anim.fade_out);

                }
            }, SPLASH_DISPLAY_LENGHT);

        }

    }

    @Override
    public void onStop(){
        if(pd!=null && pd.isShowing()){
            pd.dismiss();
            pd=null;
        }
        super.onStop();
    }

}
