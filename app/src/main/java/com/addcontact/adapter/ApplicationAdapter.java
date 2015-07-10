package com.addcontact.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.addcontact.R;
import com.addcontact.Util.BitmapHelper;
import com.addcontact.Util.Util;
import com.addcontact.activity.Aboutus;
import com.addcontact.activity.DetailActivity;
import com.addcontact.activity.MainActivity;
import com.addcontact.activity.PersonList;
import com.addcontact.application.cAapplication;
import com.addcontact.models.DataObject;

import java.util.ArrayList;
import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ViewHolder> {

    private ArrayList<DataObject> mDataset;
    private int rowLayout;
    private Context mContext;
    private LayoutInflater linf;
    private static MyClickListener myClickListener;
    private static String LOG_TAG = "ApplicationAdapter";

    public ApplicationAdapter(ArrayList<DataObject> applications, int rowLayout,Context  con) {
        this.mDataset = applications;
        this.rowLayout = rowLayout;
        this.mContext=con;
        this.linf = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.linf = LayoutInflater.from(con);
    }

    public void clearApplications() {
        int size = this.mDataset.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                mDataset.remove(0);
            }
            this.notifyItemRangeRemoved(0, size);
           /// this.mDataset.clear();
            notifyDataSetChanged();
        }
    }

    public void addApplications(List<DataObject> applications) {
        this.mDataset.addAll(applications);
        this.notifyItemRangeInserted(0, applications.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        SpannableStringBuilder Namedetails = new SpannableStringBuilder();
        SpannableStringBuilder Mobiledetails = new SpannableStringBuilder();
        SpannableStringBuilder Emaildetails = new SpannableStringBuilder();
        SpannableStringBuilder Addressdetails = new SpannableStringBuilder();

        final String Name=mDataset.get(position).getFirstName() + " " + mDataset.get(position).getMiddleName() + " " + mDataset.get(position).getLastName();
        SpannableString nameSpan = new SpannableString(Name);
        nameSpan.setSpan(new StyleSpan(Typeface.NORMAL), 0, Name.length(), 0);
        nameSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#008080")), 0, Name.length(), 0);
        Namedetails.append("Name: ").append(nameSpan);
        holder.fullname.setText(Namedetails, TextView.BufferType.SPANNABLE);

        String Slip=mDataset.get(position).getSlipNo();
        SpannableString mobileSpan = new SpannableString(Slip);
        mobileSpan.setSpan(new StyleSpan(Typeface.NORMAL), 0, Slip.length(), 0);
        mobileSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#008080")), 0, Slip.length(), 0);
        Mobiledetails.append("SlipNo: ").append(mobileSpan);
        holder.slipNo.setText(Mobiledetails, TextView.BufferType.SPANNABLE);

        //String Add=TextUtils.isEmpty(mDataset.get(position).getAddress().toString()) ? "N/A" :  mDataset.get(position).getAddress();
        String CusId=mDataset.get(position).getCustomerId();
        SpannableString addressSpan = new SpannableString(CusId);
        addressSpan.setSpan(new StyleSpan(Typeface.NORMAL), 0, CusId.length(), 0);
        addressSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#008080")), 0, CusId.length(), 0);
        Addressdetails.append("CustomerId: ").append(addressSpan);
        holder.customerId.setText(Addressdetails, TextView.BufferType.SPANNABLE);

        final String Add=mDataset.get(position).getAddress()+" "+mDataset.get(position).getVillage()+" "+mDataset.get(position).getTaluko()+" "+mDataset.get(position).getDistrict();

        /*System.out.println("in Adapter PATH is: " + mDataset.get(position).getPhotoPath());

        try{
            if(TextUtils.isEmpty(mDataset.get(position).getPhotoPath())){
                holder.img.setImageResource(R.drawable.placeholder_man_yellow);
            }else{
                Bitmap bitmap = BitmapHelper.scaleAndRotateImage(mDataset.get(position).getPhotoPath(), 300, 300);
                holder.img.setImageBitmap(bitmap);
            }


        }catch(Exception e){
            e.printStackTrace();;
        }*/

        holder.btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mainStr = "CustomerId: " + mDataset.get(position).getCustomerId() +
                        ", SlipNo: " + mDataset.get(position).getSlipNo() +
                        ", Name: " + Name +
                        ", Address: " + Add;

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Balprakash Customer Details");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, mainStr);
                mContext.startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Log.i(LOG_TAG, " edit clicked!! "+position+" AND id is: "+mDataset.get(position).getId());

                View view = linf.inflate(R.layout.update_layout, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setView(view);
                builder.setCancelable(false);

                final EditText txt_firstname=(EditText)view.findViewById(R.id.txt_firstname);
                final EditText txt_midlename=(EditText)view.findViewById(R.id.txt_middlename);
                final EditText txt_lastname=(EditText)view.findViewById(R.id.txt_lastname);
                final EditText txt_mobile=(EditText)view.findViewById(R.id.txt_mobile);
                final EditText txt_address=(EditText)view.findViewById(R.id.txt_address);
                final EditText txt_village=(EditText)view.findViewById(R.id.txt_address2);
                final EditText txt_taluko=(EditText)view.findViewById(R.id.txt_address3);
                final EditText txt_district=(EditText)view.findViewById(R.id.txt_address4);
                final EditText txt_pincode=(EditText)view.findViewById(R.id.txt_pincode);
                List<String> _list=cAapplication.getInstance().getDBAdapter().getPersonDetail(mDataset.get(position).getId());

                txt_firstname.setText(TextUtils.isEmpty(_list.get(0))? "N/A" : _list.get(0));
                txt_midlename.setText(TextUtils.isEmpty(_list.get(1))? "N/A" : _list.get(1));
                txt_lastname.setText(TextUtils.isEmpty(_list.get(2))? "N/A" : _list.get(2));
                txt_mobile.setText(TextUtils.isEmpty(_list.get(3))? "N/A" : _list.get(3));
                txt_address.setText(TextUtils.isEmpty(_list.get(4))? "N/A" : _list.get(4));
                txt_village.setText(TextUtils.isEmpty(_list.get(5))? "N/A" : _list.get(5));
                txt_taluko.setText(TextUtils.isEmpty(_list.get(6))? "N/A" : _list.get(6));
                txt_district.setText(TextUtils.isEmpty(_list.get(7))? "N/A" : _list.get(7));
                txt_pincode.setText(TextUtils.isEmpty(_list.get(8))? "N/A" : _list.get(8));

                txt_mobile.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        // TODO Auto-generated method stub


                       /* if (cAapplication.getInstance().getDBAdapter().is_PersonFound(txt_mobile.getText().toString()) != 0) {

                            final Toast toast = Toast.makeText(mContext, "MobileNo already exist.", Toast.LENGTH_SHORT);
                            toast.show();

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    toast.cancel();
                                }
                            }, 3000);

                        }*/

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

                builder.setTitle("Modify Person Details")
                        .setPositiveButton("Update",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.dismiss();

                                       /* if (cAapplication.getInstance().getDBAdapter().is_PersonFound(txt_mobile.getText().toString())!=0) {


                                            final Toast toast = Toast.makeText(mContext, "MobileNo already exist.", Toast.LENGTH_SHORT);
                                            toast.show();

                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    toast.cancel();
                                                }
                                            }, 3000);


                                        } else {*/

                                            if (cAapplication.getInstance().getDBAdapter().updatePersonDetails(mDataset.get(position).getId(),
                                                    txt_firstname.getText().toString(),
                                                    txt_midlename.getText().toString(),
                                                    txt_lastname.getText().toString(),
                                                    txt_mobile.getText().toString(),
                                                    txt_address.getText().toString(),
                                                    txt_village.getText().toString(),
                                                    txt_taluko.getText().toString(),
                                                    txt_district.getText().toString(),
                                                    txt_pincode.getText().toString())) {

                                                final Toast toast = Toast.makeText(mContext, "Person Details updated", Toast.LENGTH_SHORT);
                                                toast.show();

                                                Handler handler = new Handler();
                                                handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        toast.cancel();
                                                    }
                                                }, 3000);

                                            } else {

                                                final Toast toast = Toast.makeText(mContext, "Something wrong to updating Person details.", Toast.LENGTH_SHORT);
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

                                   // }
                                });
                builder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.setIcon(android.R.drawable.ic_dialog_alert);
                alert.show();

            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, " delete clicked!! "+position+" AND id is: "+mDataset.get(position).getId());

                /*cAapplication.getInstance().getDBAdapter().removeContact(mDataset.get(position).getId());
                removeAt(position);*/

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Delete Person ")
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        dialog.dismiss();
                                        //Util.deleteImgFile(mDataset.get(position).getPhotoPath());
                                        cAapplication.getInstance().getDBAdapter().removeContact(mDataset.get(position).getId());
                                        removeAt(position);

                                    }
                                });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.setMessage("Are you sure want to delete?");
                alert.setIcon(android.R.drawable.ic_dialog_alert);
                alert.show();

                /*holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("Adapter","cardview clicked");
                        Intent i = new Intent(mContext, DetailActivity.class);
                        i.putExtra("UserId",mDataset.get(position).getId());
                        mContext.startActivity(i);
                        //mContext.overridePendingTransition(R.anim.slide_activity_in_right, R.anim.slide_activity_out_right);
                    }
                });*/
            }
        });
    }

    public void removeAt(int position) {
        this.mDataset.remove(position);
        this.notifyItemRemoved(position);
        Log.i(LOG_TAG, " mDataset size: " + mDataset.size());
        notifyItemRangeChanged(0, mDataset.size());
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {return position;}

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener{
       TextView fullname,customerId,slipNo;
        ImageButton btn_edit,btn_delete,btn_share;
        //ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            fullname = (TextView) itemView.findViewById(R.id.fullname);
            customerId = (TextView) itemView.findViewById(R.id.customerId);
            slipNo = (TextView) itemView.findViewById(R.id.slipNo);
            btn_edit=(ImageButton)itemView.findViewById(R.id.edit_btn);
            btn_delete=(ImageButton)itemView.findViewById(R.id.remove_btn);
            btn_share=(ImageButton)itemView.findViewById(R.id.share_btn);

            Log.i(LOG_TAG, "Adding Listener");
           itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
