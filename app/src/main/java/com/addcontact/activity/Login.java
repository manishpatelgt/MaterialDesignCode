package com.addcontact.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.addcontact.R;
import com.addcontact.Util.Util;
import com.addcontact.data.PreferencesHelper;

public class Login extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText userName,passWord;
    private TextView versionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        versionText=(TextView)findViewById(R.id.version);
        System.out.println("version: " + Util.getApplicationVersionName(this));
        versionText.setText("v" + Util.getApplicationVersionName(this));

        /*Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbarTop.findViewById(R.id.toolbar_title);
        mTitle.setText("Login");*/

        userName=(EditText)findViewById(R.id.editTextLoginUser);
        passWord=(EditText)findViewById(R.id.editTextLoginPassword);
        //userName.setText("Admin");
        //passWord.setText("Admin123");

        passWord.setImeOptions(passWord.getImeOptions() | EditorInfo.IME_ACTION_DONE);
        passWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView pView, int pActionId, KeyEvent pEvent) {
                if (pActionId == EditorInfo.IME_ACTION_DONE) {

                    userName.clearFocus();
                    passWord.clearFocus();
                    InputMethodManager imm = (InputMethodManager) pView.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(pView.getWindowToken(), 0);

                    final String userLogin = userName.getText().toString();
                    final String userPassword = passWord.getText().toString();

                    if (TextUtils.isEmpty(userName.getText().toString()) || TextUtils.isEmpty(passWord.getText().toString())) {
                        final Toast toast = Toast.makeText(Login.this, "Enter UserName and Password properly.", Toast.LENGTH_SHORT);
                        toast.show();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                toast.cancel();
                            }
                        }, 3000);
                        return false;
                    } else {

                        if("Admin".equals(userName.getText().toString()) && "Admin123".equals(passWord.getText().toString())){
                            completeLogin();
                        }else{
                            final Toast toast = Toast.makeText(Login.this, "UserName or Password wrong. Try again.", Toast.LENGTH_SHORT);
                            toast.show();

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    toast.cancel();
                                }
                            }, 3000);
                        }

                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void loginButtonClick(View v){
        userName.clearFocus();
        passWord.clearFocus();

        final String userLogin = userName.getText().toString();
        final String userPassword = passWord.getText().toString();

        if (TextUtils.isEmpty(userName.getText().toString()) || TextUtils.isEmpty(passWord.getText().toString())) {
            final Toast toast = Toast.makeText(Login.this, "Enter UserName and Password properly.", Toast.LENGTH_SHORT);
            toast.show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, 3000);

        } else {

            if("Admin".equals(userName.getText().toString()) && "Admin123".equals(passWord.getText().toString())){
                completeLogin();
            }else{
                final Toast toast = Toast.makeText(Login.this, "UserName or Password wrong. Try again.", Toast.LENGTH_SHORT);
                toast.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 3000);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void completeLogin() {

        PreferencesHelper.setLoginCheck(true);
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.slide_activity_in_right, R.anim.slide_activity_out_right);
        Login.this.finish();
    }
}
