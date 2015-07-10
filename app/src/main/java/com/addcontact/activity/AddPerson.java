package com.addcontact.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.addcontact.R;
import com.addcontact.Util.Util;
import com.addcontact.Util.Validation;
import com.addcontact.application.cAapplication;
import com.addcontact.models.DataObject;

import java.util.Date;

/**
 * Created by Edwin on 15/02/2015.
 */
public class AddPerson extends Fragment {

    private EditText txt_firstname,txt_midlename,txt_lastname,txt_mobile,
            txt_address,txt_village,txt_taluko,txt_district,txt_pincode,txt_slipno,txt_customerid,txt_price,txt_kname;
    private Button add_btn;
    private RadioGroup radiobGroup,radioyearGroup;
    private RadioButton radioBButton,radioYButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView =inflater.inflate(R.layout.tab_1,container,false);
        txt_firstname=(EditText)rootView.findViewById(R.id.txt_firstname);
        txt_midlename=(EditText)rootView.findViewById(R.id.txt_middlename);
        txt_lastname=(EditText)rootView.findViewById(R.id.txt_lastname);
        txt_mobile=(EditText)rootView.findViewById(R.id.txt_mobile);
        txt_address=(EditText)rootView.findViewById(R.id.txt_address);
        txt_village=(EditText)rootView.findViewById(R.id.txt_address2);
        txt_taluko=(EditText)rootView.findViewById(R.id.txt_address3);
        txt_district=(EditText)rootView.findViewById(R.id.txt_address4);
        txt_pincode=(EditText)rootView.findViewById(R.id.txt_pincode);
        txt_slipno=(EditText)rootView.findViewById(R.id.txt_slipno);
        txt_customerid=(EditText)rootView.findViewById(R.id.txt_customerid);
        txt_price=(EditText)rootView.findViewById(R.id.txt_price);
        txt_kname=(EditText)rootView.findViewById(R.id.txt_kname);
        radiobGroup = (RadioGroup) rootView.findViewById(R.id.radiob);
        radioyearGroup = (RadioGroup) rootView.findViewById(R.id.radioyear);

        add_btn=(Button)rootView.findViewById(R.id.add_button);

        /*txt_mobile.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                // TODO Auto-generated method stub


                if(Validation.isValidPhoneNumber(s)){
                    /*if (cAapplication.getInstance().getDBAdapter().is_PersonFound(txt_mobile.getText().toString()) !=0) {

                        final Toast toast = Toast.makeText(getActivity(), "MobileNo already exist.", Toast.LENGTH_SHORT);
                        toast.show();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                toast.cancel();
                            }
                        }, 3000);

                    }

                }else{
                    txt_mobile.setError("Phone Number is not valid.");
                }
               /*if(txt_mobile.getText().toString().length()>=10){
                   txt_mobile.clearFocus();
                   if (cAapplication.getInstance().getDBAdapter().is_PersonFound(txt_mobile.getText().toString()) !=0) {

                       final Toast toast = Toast.makeText(getActivity(), "MobileNo already exist.", Toast.LENGTH_SHORT);
                       toast.show();

                       Handler handler = new Handler();
                       handler.postDelayed(new Runnable() {
                           @Override
                           public void run() {
                               toast.cancel();
                           }
                       }, 3000);

                       //txt_mobile.setText("");
                   }
               }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });*/


        /*txt_pincode.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                // TODO Auto-generated method stub

                if (Validation.isValidPinCode(s)) {
                } else {
                    txt_pincode.setError("Pincode is not valid.");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        txt_slipno.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                // TODO Auto-generated method stub

                /*if (Validation.isValidSlipNo(s)) {
                } else {
                    txt_slipno.setError("SlipNo is not valid.");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });*/

       /* txt_customerid.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                // TODO Auto-generated method stub

                if (Validation.isValidCustomerNo(s)) {
                } else {
                    txt_customerid.setError("CustomerId is not valid.");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });*/

        /*txt_price.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                // TODO Auto-generated method stub

                if (Validation.isValidPrice(s)) {
                } else {
                    txt_price.setError("Price is not valid.");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });*/


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(txt_firstname.getText().toString()) || TextUtils.isEmpty(txt_mobile.getText().toString())) {

                    final Toast toast = Toast.makeText(getActivity(), "You must enter all the value properly.", Toast.LENGTH_SHORT);
                    toast.show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 3000);

                    txt_firstname.setText("");
                    txt_lastname.setText("");
                    txt_midlename.setText("");
                    txt_mobile.setText("");
                    txt_address.setText("");
                    txt_village.setText("");
                    txt_taluko.setText("");
                    txt_district.setText("");
                    txt_pincode.setText("");
                    txt_slipno.setText("");
                    txt_customerid.setText("");
                    txt_price.setText("");
                    txt_kname.setText("");

                } else {

                    Date now=new Date();
                    String currntTime= Util.getDateFormatted3(now);

                    int selectedId = radiobGroup.getCheckedRadioButtonId();
                    radioBButton = (RadioButton) rootView.findViewById(selectedId);

                    int selectedId2= radioyearGroup.getCheckedRadioButtonId();
                    radioYButton = (RadioButton) rootView.findViewById(selectedId2);

                        if (cAapplication.getInstance().getDBAdapter().insertContact(txt_firstname.getText().toString(),
                                txt_midlename.getText().toString(),
                                txt_lastname.getText().toString(),
                                txt_mobile.getText().toString(),
                                txt_address.getText().toString(),
                                txt_village.getText().toString(),
                                txt_taluko.getText().toString(),
                                txt_district.getText().toString(),
                                txt_pincode.getText().toString(),
                                txt_slipno.getText().toString(),
                                txt_customerid.getText().toString(),
                                radioBButton.getText().toString(),
                                radioYButton.getText().toString(),
                                txt_price.getText().toString(),
                                txt_kname.getText().toString(),
                                currntTime)) {

                            final Toast toast = Toast.makeText(getActivity(), "Person Added", Toast.LENGTH_SHORT);
                            toast.show();

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    toast.cancel();
                                }
                            }, 3000);

                            txt_firstname.setText("");
                            txt_lastname.setText("");
                            txt_midlename.setText("");
                            txt_mobile.setText("");
                            txt_address.setText("");
                            txt_village.setText("");
                            txt_taluko.setText("");
                            txt_district.setText("");
                            txt_pincode.setText("");
                            txt_slipno.setText("");
                            txt_customerid.setText("");
                            txt_price.setText("");
                            txt_kname.setText("");

                        } else {
                            final Toast toast = Toast.makeText(getActivity(), "Something wrong to adding Person.", Toast.LENGTH_SHORT);
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
        });

        /*take_photo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CameraActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_activity_in_right, R.anim.slide_activity_out_right);
            }
        });*/
        return rootView;
    }
}
