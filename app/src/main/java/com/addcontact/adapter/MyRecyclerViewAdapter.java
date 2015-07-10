package com.addcontact.adapter;

/**
 * Created by Manish on 10/06/2015.
 */
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.addcontact.R;
import com.addcontact.models.DataObject;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.DataObjectHolder> {

    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<DataObject> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        TextView fullname,Mobile,Address;

        public DataObjectHolder(View itemView) {
            super(itemView);
            fullname = (TextView) itemView.findViewById(R.id.fullname);
            Mobile = (TextView) itemView.findViewById(R.id.mobile);
           Address = (TextView) itemView.findViewById(R.id.address);
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

    public MyRecyclerViewAdapter(ArrayList<DataObject> myDataset) {
        mDataset = myDataset;
    }

    public void clear() {
        int size = this.mDataset.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                mDataset.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    public void add(ArrayList<DataObject> _list) {
        this.mDataset.addAll(_list);
        this.notifyItemRangeInserted(0, _list.size() - 1);
    }


    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        SpannableStringBuilder Namedetails = new SpannableStringBuilder();
        SpannableStringBuilder Mobiledetails = new SpannableStringBuilder();
        SpannableStringBuilder Emaildetails = new SpannableStringBuilder();
        SpannableStringBuilder Addressdetails = new SpannableStringBuilder();

        String Name=mDataset.get(position).getFirstName() + " " + mDataset.get(position).getMiddleName() + " " + mDataset.get(position).getLastName();
        SpannableString nameSpan = new SpannableString(Name);
        nameSpan.setSpan(new StyleSpan(Typeface.NORMAL), 0, Name.length(), 0);
        nameSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#008080")), 0, Name.length(), 0);
        Namedetails.append("Name: ").append(nameSpan);
        holder.fullname.setText(Namedetails, TextView.BufferType.SPANNABLE);

        String Mobile=mDataset.get(position).getMobileNo();
        SpannableString mobileSpan = new SpannableString(Mobile);
        mobileSpan.setSpan(new StyleSpan(Typeface.NORMAL), 0, Mobile.length(), 0);
        mobileSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#008080")), 0, Mobile.length(), 0);
        Mobiledetails.append("Mobile: ").append(mobileSpan);
        holder.Mobile.setText(Mobiledetails, TextView.BufferType.SPANNABLE);

        String Add=TextUtils.isEmpty(mDataset.get(position).getAddress().toString()) ? "N/A" :  mDataset.get(position).getAddress();
        SpannableString addressSpan = new SpannableString(Add);
        addressSpan.setSpan(new StyleSpan(Typeface.NORMAL), 0, Add.length(), 0);
        addressSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#008080")), 0, Add.length(), 0);
        Addressdetails.append("Address: ").append(addressSpan);
        holder.Address.setText(Addressdetails, TextView.BufferType.SPANNABLE);

        //String Email=TextUtils.isEmpty(mDataset.get(position).getEmail().toString()) ? "N/A" :  mDataset.get(position).getEmail();
        //holder.Email.setText("Email: "+Email);

        //String Add=TextUtils.isEmpty(mDataset.get(position).getAddress().toString()) ? "N/A" :  mDataset.get(position).getAddress();
        //holder.Address.setText("Address: "+Add);
    }

    public void addItem(DataObject dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    public void refresh(ArrayList<DataObject> myDataset){
        myDataset=myDataset;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}